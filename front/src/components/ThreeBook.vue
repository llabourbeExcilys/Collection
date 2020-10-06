<template>
	<div>
		<div :id="canvaName" ref="canva"></div>
	</div>
</template>

<script type="module">
import * as THREE from 'three';
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';
import { cloneDeep } from 'lodash';

export default {
	name: 'threebook',
	components: {},

	props: {
		editMode: {
			type: Boolean,
			default: false
		},
		title: {
			type: String,
			required: true
		},
		numberPublished: {
			type: Number,
			required: true
		},
		numberPossessed: {
			type: Number,
			required: true
		},
		mangaDimensions: {
			type: Object,
			required: true
		}
	},
	data: function() {
		return {
			books: [],
			camera: null,
			color: '#FF0000FF',
			controls: null,
			emptySpace: 3,
			geometry: null,
			intersected: null,
			loader: null,
			material: null,
			mouse: null,
			newRenderReady: true,
			previousMangaDimensions: null,
			raycaster: null,
			renderer: null,
			rendererWidth: 613,
			scene: null,
			timeBeforeEachRender: 50
		};
	},
	computed: {
		canvaName() {
			return this.title + '-canva';
		},
		// convert RGBA to RGB assuming color background
		pickedRGBColor() {
			const transparency = parseInt(this.color.substring(7), 16);
			return this.computedColor(transparency);
		},
		rendererHeight() {
			if (this.editMode) {
				return this.rendererWidth * (3 / 4);
			} else {
				return this.rendererWidth * (9 / 16);
			}
		},
		lightenColor() {
			const transparency = Math.round(255 * 0.7);
			return this.computedColor(transparency);
		},
		ratio() {
			return this.editMode ? 4 / 3 : 16 / 9;
		}
	},
	watch: {
		mangaDimensions() {
			this.setBooksScale();
		},
		color() {
			let colorOwned = parseInt(this.pickedRGBColor.substring(1), 16);
			let colorNotOwned = parseInt(this.lightenColor.substring(1), 16);
			for (var i = 0; i < this.numberPublished; i++) {
				this.books[i].material.color.set(i < this.numberPossessed ? colorOwned : colorNotOwned);
			}
		},
		numberPossessed() {
			this.rerender();
		},
		numberPublished() {
			this.rerender();
		}
	},
	mounted() {
		this.previousMangaDimensions = cloneDeep(this.mangaDimensions);
		this.init();
		this.animate();
	},
	updated() {},
	methods: {
		animate() {
			requestAnimationFrame(this.animate);

			if (this.$refs.canva && this.rendererWidth != this.$refs.canva.clientWidth) {
				this.rendererWidth = this.$refs.canva.clientWidth;

				this.camera.aspect = this.ratio;
				this.camera.updateProjectionMatrix();
				this.renderer.setSize(this.rendererWidth, this.rendererHeight);
			}

			// update the picking ray with the camera and mouse position
			this.raycaster.setFromCamera(this.mouse, this.camera);
			// calculate objects intersecting the picking ray
			var intersects = this.raycaster.intersectObjects(this.scene.children);
			if (intersects.length > 0) {
				if (this.intersected != intersects[0].object) {
					if (this.intersected) {
						this.intersected.material.emissive.setHex(this.intersected.currentHex);
					}
					this.intersected = intersects[0].object;
					this.intersected.currentHex = this.intersected.material.emissive.getHex();
					this.intersected.material.emissive.setHex(0xa6b7bf);
				}
			} else {
				if (this.intersected) {
					this.intersected.material.emissive.setHex(this.intersected.currentHex);
				}
				this.intersected = null;
			}

			// this.mesh.rotation.y += 0.02;
			this.renderer.render(this.scene, this.camera);
		},

		init() {
			this.renderer = new THREE.WebGLRenderer({ antialias: true });
			this.renderer.setPixelRatio(window.devicePixelRatio);

			this.rendererWidth = this.$refs.canva.clientWidth;
			this.renderer.setSize(this.rendererWidth, this.rendererHeight);

			// this.renderer.shadowMap.enabled = true;
			// this.renderer.shadowMap.type = THREE.PCFShadowMap;

			let container = document.getElementById(this.canvaName);
			container.appendChild(this.renderer.domElement);

			this.camera = new THREE.PerspectiveCamera(60, this.ratio, 1, 5000);
			this.camera.position.z = this.numberPublished * 12 + 500;
			// this.camera.position.y = this.numberPublished + 100 + this.mangaDimensions.height / 4;
			this.camera.position.y = this.numberPublished + 100 + this.mangaDimensions.height / 4;
			this.camera.lookAt(0, 0, this.mangaDimensions.height / 2);

			this.scene = new THREE.Scene();
			this.scene.background = new THREE.Color(0xf7f9f9);

			const loader = new GLTFLoader();
			loader.load(
				'/models/scene.gltf',
				gltf => {
					var obj3D = gltf.scene;
					// console.log('obj3D', obj3D);

					var objectToRemove = obj3D.children[0].children[0].children[0].children[0];
					var parent = objectToRemove.parent;
					parent.remove(objectToRemove);

					objectToRemove = obj3D.children[0].children[0].children[0].children[0];
					parent = objectToRemove.parent;
					parent.remove(objectToRemove);

					var object = obj3D.children[0].children[0].children[0].children[0];

					object.position.x = 0;
					object.position.y = 0;
					object.position.z = -(this.mangaDimensions.height / 2 + 20);
					object.rotateZ(1.5708);

					object.scale.x = 100;
					object.scale.y = 100;
					object.scale.z = 100;
					this.scene.add(gltf.scene);
				},
				xhr => console.log((xhr.loaded / xhr.total) * 100 + '% loaded'),
				error => console.error(error)
			);

			this.scene.add(new THREE.AmbientLight(0xffffff));

			let light = new THREE.SpotLight(0xffffff);
			light.position.set(0, 1000, 500);
			light.target = this.scene.children[0];

			light.shadow.camera.near = 1;
			light.shadow.camera.far = 4000;
			light.shadow.mapSize.width = 1024;
			light.shadow.mapSize.height = 1024;
			this.scene.add(light);

			this.addBooks();
			this.setBooksScale();

			this.raycaster = new THREE.Raycaster();
			this.mouse = new THREE.Vector2();
			container.addEventListener('mousemove', this.onMouseMove, false);

			// var axesHelper = new THREE.AxesHelper(500);
			// this.scene.add(axesHelper);
		},
		onMouseMove(event) {
			event.preventDefault();
			// calculate mouse position in normalized device coordinates : (-1 to +1) for both components
			var rect = this.renderer.domElement.getBoundingClientRect();
			this.mouse.x = ((event.clientX - rect.left) / (rect.right - rect.left)) * 2 - 1;
			this.mouse.y = -((event.clientY - rect.top) / (rect.bottom - rect.top)) * 2 + 1;
		},
		rerender() {
			this.books.forEach(item => this.scene.remove(item));
			this.books = [];
			this.addBooks();
			this.setBooksScale();
			this.animate();
		},
		addBooks() {
			let bookWidth = this.mangaDimensions.width;
			let bookTotalSpace = bookWidth + this.emptySpace;
			let geometry = new THREE.BoxGeometry(1, 1, 1);
			for (var i = 0; i < this.numberPublished; i++) {
				let color;
				if (i < this.numberPossessed) {
					// remove # at the start and 2 digits used for transparency at the end
					color = parseInt(this.pickedRGBColor.substring(1), 16);
				} else {
					color = parseInt(this.lightenColor.substring(1), 16);
				}

				var manga = new THREE.Mesh(geometry, new THREE.MeshPhongMaterial({ color: color }));
				manga.position.x = i * bookTotalSpace - (this.numberPublished / 2) * bookTotalSpace + bookWidth / 2;
				manga.castShadow = true;
				manga.receiveShadow = true;
				this.scene.add(manga);
				this.books.push(manga);
			}
		},
		setBooksScale() {
			this.books.forEach((book, index) => {
				book.scale.x = this.mangaDimensions.width;
				book.scale.y = this.mangaDimensions.height;
				book.scale.z = this.mangaDimensions.depth;

				let diffWidth = this.mangaDimensions.width - this.previousMangaDimensions.width;
				if (diffWidth != 0) {
					let middle = (this.books.length - 1) / 2;
					book.translateX(diffWidth * (index - middle));

					this.camera.position.z = this.camera.position.z + diffWidth / 3;
					this.camera.position.y = this.camera.position.y + diffWidth / 5;
					this.camera.lookAt(0, 0, this.mangaDimensions.height / 2);
				}

				let diffHeight = this.mangaDimensions.height - this.previousMangaDimensions.height;
				if (diffHeight != 0) {
					book.translateY(diffHeight / 2);
				}
			});
			this.previousMangaDimensions = cloneDeep(this.mangaDimensions);
		},
		computedColor(transparency) {
			const color_r = parseInt(this.color.substring(1, 3), 16);
			const color_g = parseInt(this.color.substring(3, 5), 16);
			const color_b = parseInt(this.color.substring(5, 7), 16);

			//assuming white background
			const background_r = 255;
			const background_g = 255;
			const background_b = 255;

			// getting transparancy [0,255] then translating to [0-1])
			const alpha = Math.round((transparency * 100) / 256) / 100;

			const newColor_r = Math.round((1 - alpha) * background_r + alpha * color_r);
			const newColor_g = Math.round((1 - alpha) * background_g + alpha * color_g);
			const newColor_b = Math.round((1 - alpha) * background_b + alpha * color_b);

			const hexPartToString = hex => {
				return hex.toString(16).length < 2 ? '0' + hex.toString(16) : hex.toString(16);
			};

			return '#' + hexPartToString(newColor_r) + hexPartToString(newColor_g) + hexPartToString(newColor_b);
		}
	}
};
</script>
<style scoped>
#canvas {
	border: 1px solid black;
	display: inline-block;
	/* position: static; 
    top: 100px;
    left: 100px; */
}
</style>
