import Vue from 'vue';
import Vuex from 'vuex';
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		woodPlankScene: null,
		manageSelectedTab: 'Serie'
	},
	mutations: {
		setWoodPlankScene(state, plank){
			state.woodPlankScene = plank
		},
		setManageSelectedTab(state, tab){
			state.manageSelectedTab = tab
		}
	},
	actions: {
		loadWoodPlankScene({commit}){
			return new Promise( (resolve) => {
				const loader = new GLTFLoader();
				loader.load(
					'/models/scene.gltf',
					gltf => {
						const gltfScene = gltf.scene;
						var parent = gltfScene.children[0].children[0].children[0];
						// parent.remove(parent.children[0]);
						// parent.remove(parent.children[0]);
						var object = parent.children[0];
						// console.log('object)', object);
						object.position.set(0, 0, 0);
						object.scale.set(100, 100, 100);
						object.rotateZ(1.5708);
	
						commit('setWoodPlankScene', gltfScene)
						resolve()
					},
					xhr => console.log((xhr.loaded / xhr.total) * 100 + '% loaded'),
					error => console.error(error)
				);
			})
		}
	},
	modules: {}
});
