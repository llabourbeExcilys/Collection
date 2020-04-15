<template>
  <div>
    <div :id="canvaName"></div>
  </div>
</template>

<script>

import * as THREE from 'three';
import DragControls from 'drag-controls';
import  {cloneDeep} from 'lodash';

export default {
  name: 'threebook',  
  components: {},
  
  props:{
    title :{
      type: String,
      required: true
    },
    length:{
      type: Number,
      required : true
    }
  },
  data: function () {
    return {
      books : [],
      camera: null,
      controls: null,
      scene: null,
      renderer: null,
      geometry: null,
      material: null
    }
  },
  computed: {
    canvaName (){
      return this.title+"-canva"
    }
  },
  mounted () {
    this.init();
    this.animate();
  },
  methods: {
    init() {
      DragControls.install({THREE: THREE})

      this.renderer = new THREE.WebGLRenderer( { antialias: true } );
      this.renderer.setPixelRatio( window.devicePixelRatio );
      this.renderer.setSize( window.innerWidth/2, window.innerHeight /4);
      this.renderer.shadowMap.enabled = true;
			this.renderer.shadowMap.type = THREE.PCFShadowMap;
      let container = document.getElementById( this.canvaName );
      container.appendChild( this.renderer.domElement );

      this.camera = new THREE.PerspectiveCamera( 80, window.innerWidth / (window.innerHeight/2), 1, 5000 );
      this.camera.position.z = 500;
      this.camera.position.y = 150;
      this.camera.lookAt(0,0,0)
  
      this.scene = new THREE.Scene();
      this.scene.background = new THREE.Color( 0xf0f0f0 );
			this.scene.add( new THREE.AmbientLight( 0x505050 ) );


      let light = new THREE.SpotLight( 0xffffff, 1.5 );
      light.position.set( 0, 200, 2000 );
      light.angle = Math.PI / 9;

      light.castShadow = true;
      light.shadow.camera.near = 1000;
      light.shadow.camera.far = 4000;
      light.shadow.mapSize.width = 1024;
      light.shadow.mapSize.height = 1024;
      this.scene.add( light );

      let bookWidth = 17
      let bookHeight = 184
      let bookDepth = 130
      let emptySpace = 5

      this.geometry = new THREE.BoxGeometry( bookWidth, bookHeight, bookDepth );
      //this.geometry = new THREE.BoxGeometry( 40, 40, 40 );

      let color = { color: Math.random() * 0xffffff }

      
      for ( var i = 0; i < this.length; i ++ ) {

        var object = new THREE.Mesh( this.geometry, new THREE.MeshLambertMaterial( { color: Math.random() * 0xffffff } ) );

        let bookTotalSpace = bookWidth + emptySpace
        object.position.x = (i*bookTotalSpace) - (this.length/2)*bookTotalSpace + bookWidth/2
        // object.position.y = Math.random() * 600 - 300;
        // object.position.z = Math.random() * 800 - 400;


        object.castShadow = true;
        object.receiveShadow = true;

        this.scene.add( object );
        this.books.push( object );

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

      this.controls = new DragControls([ ... this.books ], this.camera, this.renderer.domElement)
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
      requestAnimationFrame( this.animate );
      // this.mesh.rotation.x += 0.01;
      // this.mesh.rotation.y += 0.02;
      this.renderer.render( this.scene, this.camera );
    }
  }
}
</script>
<style scoped>

#canvas {
    border: 1px solid black;
    display: inline-block
    /* position: static; 
    top: 100px;
    left: 100px; */
}
</style>