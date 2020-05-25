<template>
	<div>
		<div :id="canvaName" ref="canva"></div>
	</div>
</template>

<script>
import * as THREE from 'three';
import { cloneDeep } from 'lodash';

export default {
	name: 'threebook',
	components: {},

	props: {
		pickedColor: {
			type: String,
			default: '#3F51B5'
		},
		title: {
			type: String,
			required: true
		},
		numberPublished: {
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
			controls: null,
			geometry: null,
			intersected: null,
			material: null,
			mouse: null,
			newRenderReady: true,
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
			const color_r = parseInt(this.pickedColor.substring(1, 3), 16);
			const color_g = parseInt(this.pickedColor.substring(3, 5), 16);
			const color_b = parseInt(this.pickedColor.substring(5, 7), 16);

			//assuming white background
			const background_r = 255;
			const background_g = 255;
			const background_b = 255;

			// getting transparancy [0,255] then translating to [0-1])
			let transparency = parseInt(this.pickedColor.substring(7), 16);
			let alpha = Math.round((transparency * 100) / 256) / 100;

			let newColor_r = Math.round((1 - alpha) * background_r + alpha * color_r);
			let newColor_g = Math.round((1 - alpha) * background_g + alpha * color_g);
			let newColor_b = Math.round((1 - alpha) * background_b + alpha * color_b);

			let hexPartToString = hex => {
				return hex.toString(16).length < 2 ? '0' + hex.toString(16) : hex.toString(16);
			};

			return '#' + hexPartToString(newColor_r) + hexPartToString(newColor_g) + hexPartToString(newColor_b);
		},
		rendererHeight() {
			return (this.rendererWidth * 3) / 4;
		}
	},
	watch: {
		mangaDimensions() {
			this.setBooksScale();
		},
		pickedColor() {
			console.log('pickedColor RGBA:', this.pickedColor);
			console.log('pickedColor RGB :', this.pickedRGBColor);
			let color = parseInt(this.pickedRGBColor.substring(1), 16);
			this.books.forEach(book => {
				book.material.color.set(color);
			});
		}
	},
	mounted() {
		this.init();
		this.rendererWidth = this.$refs.canva.clientWidth;
		this.animate();
	},
	methods: {
		animate() {
			requestAnimationFrame(this.animate);

			if (this.$refs.canva && this.rendererWidth != this.$refs.canva.clientWidth) {
				this.rendererWidth = this.$refs.canva.clientWidth;
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
					console.log('mouse.x', this.mouse.x);
					console.log('mouse.y', this.mouse.y);
					console.log(('intersected', JSON.stringify(this.intersected)));
				}
			} else {
				if (this.intersected) {
					this.intersected.material.emissive.setHex(this.intersected.currentHex);
				}

				this.intersected = null;
			}

			// this.mesh.rotation.x += 0.01;
			// this.mesh.rotation.y += 0.02;
			this.renderer.render(this.scene, this.camera);
		},

		init() {
			this.renderer = new THREE.WebGLRenderer({ antialias: true });
			this.renderer.setPixelRatio(window.devicePixelRatio);

			this.renderer.setSize(this.rendererWidth, this.rendererHeight);
			// this.renderer.shadowMap.enabled = true;
			// this.renderer.shadowMap.type = THREE.PCFShadowMap;

			let container = document.getElementById(this.canvaName);
			container.appendChild(this.renderer.domElement);

			this.camera = new THREE.PerspectiveCamera(60, 4 / 3, 1, 5000);
			this.camera.updateProjectionMatrix();
			this.camera.position.z = this.numberPublished * 11 + 300;
			this.camera.position.y = this.numberPublished + 100 + this.mangaDimensions.height / 4;
			this.camera.lookAt(0, 0, 0);

			this.scene = new THREE.Scene();
			this.scene.background = new THREE.Color(0xf0f0f0);
			this.scene.add(new THREE.AmbientLight(0x505050));

			let light = new THREE.SpotLight(0xffffff, 1.5);
			light.position.set(0, 200, 2000);
			light.angle = Math.PI / 9;

			light.castShadow = true;
			light.shadow.camera.near = 1;
			light.shadow.camera.far = 4000;
			light.shadow.mapSize.width = 1024;
			light.shadow.mapSize.height = 1024;
			this.scene.add(light);

			let bookWidth = this.mangaDimensions.width;
			let emptySpace = 5;
			this.geometry = new THREE.BoxGeometry(1, 1, 1);

			for (var i = 0; i < this.numberPublished; i++) {
				// let color =  Math.random() * 0xffffff;

				// remove # at the start and 2 digits used for transparency at the end
				let color = parseInt(this.pickedRGBColor.substring(1), 16);

				var object = new THREE.Mesh(this.geometry, new THREE.MeshLambertMaterial({ color: color }));

				let bookTotalSpace = bookWidth + emptySpace;
				object.position.x = i * bookTotalSpace - (this.numberPublished / 2) * bookTotalSpace + bookWidth / 2;
				// object.position.y = Math.random() * 600 - 300;
				// object.position.z = Math.random() * 800 - 400;

				object.castShadow = true;
				object.receiveShadow = true;

				this.scene.add(object);
				this.books.push(object);
			}

			this.setBooksScale();

			this.raycaster = new THREE.Raycaster();
			this.mouse = new THREE.Vector2();
			container.addEventListener('mousemove', this.onMouseMove, false);
		},
		onMouseMove(event) {
			event.preventDefault();
			// calculate mouse position in normalized device coordinates
			// (-1 to +1) for both components
			var rect = this.renderer.domElement.getBoundingClientRect();
			this.mouse.x = ((event.clientX - rect.left) / (rect.right - rect.left)) * 2 - 1;
			this.mouse.y = -((event.clientY - rect.top) / (rect.bottom - rect.top)) * 2 + 1;
		},
		rerender() {
			const myNode = document.getElementById(this.canvaName);
			myNode.innerHTML = '';
			this.init();
			this.animate();
		},
		setBooksScale() {
			this.books.forEach(book => {
				book.scale.x = this.mangaDimensions.width;
				book.scale.y = this.mangaDimensions.height;
				book.scale.z = this.mangaDimensions.depth;
			});
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
