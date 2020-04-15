<template>
<div>
  <p>Manage</p>
  <v-data-iterator
    :items="mangaSeries"
    :items-per-page=5
    content-tag="v-layout"
    row
    wrap
  >
    <template v-slot:item="item">
        <MangaSerie 
            v-bind=item
        />
    </template>
  </v-data-iterator>
</div>
</template>

<script>

import MangaSerie from '@/components/MangaSerie';

export default {
  name: 'manage',  
  components: {
      MangaSerie
  },
  data: function () {
    return {
      books : [],
      mangaSeries: [
          {
            title: "Berserk",
            length: 40
          },
          {
            title: "Jojo",
            length: 80

          },
          {
            title: "Vinland",
            length: 22
          }
        ]
      }
  },
  methods: {}
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