<template>
  <v-layout
    column
    justify-center
    align-center
  >
    <v-flex
      xs12
      sm8
      md6
    >
      <div class="text-center">
        <logo />
        <vuetify-logo />
      </div>
      <v-card>
        <v-card-title class="headline">
          Welcome to Collection application
        </v-card-title>
        <v-card-text>
          <p>This app is designed to help people manage their book collection.</p>
        </v-card-text>
        <v-card-actions>
          <v-spacer />
          <v-btn
            color="primary"
            nuxt
            to="/inspire"
          >
            Continue
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-flex>
  </v-layout>
</template>

<script>
import Logo from '~/components/Logo.vue'
import VuetifyLogo from '~/components/VuetifyLogo.vue'
import * as THREE from 'three';

export default {
  components: {
    Logo,
    VuetifyLogo
  },
  data: function () {
    return {
      camera: null,
      scene: null,
      renderer: null,
      geometry: null,
      material: null,
      mesh: null
    }
  },
   mounted () {
    this.init();
    this.animate();
  },
  methods: {
      init() {
      this.camera = new THREE.PerspectiveCamera( 70, window.innerWidth / window.innerHeight, 0.01, 10 );
      this.camera.position.z = 1;
  
      this.scene = new THREE.Scene();
  
      this.geometry = new THREE.BoxGeometry( 0.2, 0.2, 0.2 );
      this.material = new THREE.MeshNormalMaterial();
  
      this.mesh = new THREE.Mesh( this.geometry, this.material );
      this.scene.add( this.mesh );
  
      this.renderer = new THREE.WebGLRenderer( { antialias: true } );
      this.renderer.setSize( window.innerWidth, window.innerHeight );
      document.body.appendChild( this.renderer.domElement );
    },

    animate() {
      requestAnimationFrame( this.animate );
      this.mesh.rotation.x += 0.01;
      this.mesh.rotation.y += 0.02;
      this.renderer.render( this.scene, this.camera );
    }
  }
}
</script>
