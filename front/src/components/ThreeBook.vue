<template>
	<div>
		<div :id="canvaName" ref="canva"></div>
	</div>
</template>

<script>
import * as THREE from 'three';
import DragControls from 'drag-controls';
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
			scene: null,
			renderer: null,
			geometry: null,
			material: null,
			timeBeforeEachRender: 200,
			newRenderReady: true,
			rendererWidth: 613
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
		}
	},
	watch: {
		mangaDimensions() {
			if (this.newRenderReady) {
				this.rerender();
				this.newRenderReady = false;
				const readyToRender = () => {
					this.newRenderReady = true;
				};
				setTimeout(readyToRender, this.timeBeforeEachRender);
			}
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
		rerender() {
			const myNode = document.getElementById(this.canvaName);
			myNode.innerHTML = '';
			this.init();
			this.animate();
		},
		init() {
			DragControls.install({ THREE: THREE });

			this.renderer = new THREE.WebGLRenderer({ antialias: true });
			this.renderer.setPixelRatio(window.devicePixelRatio);
			this.renderer.setSize(this.rendererWidth, (this.rendererWidth * 3) / 4);
			this.renderer.shadowMap.enabled = true;
			this.renderer.shadowMap.type = THREE.PCFShadowMap;
			let container = document.getElementById(this.canvaName);
			container.appendChild(this.renderer.domElement);

			this.camera = new THREE.PerspectiveCamera(60, 4, 3, 5000);
			this.camera.aspect = 4 / 3;
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
			light.shadow.camera.near = 1000;
			light.shadow.camera.far = 4000;
			light.shadow.mapSize.width = 1024;
			light.shadow.mapSize.height = 1024;
			this.scene.add(light);

			let bookWidth = this.mangaDimensions.width;
			let bookHeight = this.mangaDimensions.height;
			let bookDepth = this.mangaDimensions.depth;
			let emptySpace = 5;

			this.geometry = new THREE.BoxGeometry(this.mangaDimensions.width, bookHeight, bookDepth);

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

			// this.material = new THREE.MeshLambertMaterial(color);
			// let mesh1 = new THREE.Mesh( this.geometry, this.material );
			// let mesh2 = new THREE.Mesh( this.geometry, this.material );
			// mesh1.castShadow = true;
			// mesh1.receiveShadow = true;
			// mesh2.castShadow = true;
			// mesh2.receiveShadow = true;

			// this.scene.add(mesh1)
			// this.scene.add(mesh2)

			// this.books.push(mesh1);
			// this.books.push(mesh2);

			this.controls = new DragControls([...this.books], this.camera, this.renderer.domElement);
			// add event listener to highlight dragged objects
			// this.controls.addEventListener( 'dragstart',  event => {
			//   console.log('event:', event)
			//   event.object.material.emissive.set( 0x0b9663 )
			// }
			// );
			// this.controls.addEventListener( 'dragend', function ( event ) {
			//   console.log('event:', event)
			//   event.object.material.emissive.set( color );
			// } );
		},
		animate() {
			//console.log('canvaWidth:', this.$refs.canva.clientWidth);
			if (this.rendererWidth != this.$refs.canva.clientWidth) {
				this.rendererWidth = this.$refs.canva.clientWidth;
				this.camera.updateProjectionMatrix();
				this.renderer.setSize(this.rendererWidth, (this.rendererWidth * 3) / 4);
			}

			// this.mesh.rotation.x += 0.01;
			// this.mesh.rotation.y += 0.02;
			this.renderer.render(this.scene, this.camera);
			requestAnimationFrame(this.animate);
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
