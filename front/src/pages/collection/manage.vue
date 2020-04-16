<template>
<div>
  <p>Manage</p>
  <v-data-iterator
    :items="mangaSeries"
    :items-per-page=5
    row
    wrap
  >
    <template v-slot:default="props">
      <v-row>
        <v-col
          v-for="item in props.items"
          :key="item.title"
          cols="12"
        >
          <MangaSerie 
              :item="item"
          />
        </v-col>
      </v-row>
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
            edition: "RÉÉDITION FRANÇAISE",
            editeur: "GLENAT MANGA",
            owned: 40,
            published: 40,
            type: "Manga",
            genre:"Fantasy"
          },
          {
            title: "Jojo",
            edition: "Parties 1 -5",
            editeur: "TONKAM",
            owned: 68,
            published: 68,
            type: "Manga",
            genre:"Aventure"
          },
          {
            title: "Vinland Saga",
            edition: "Simple",
            editeur: "KUROKAWA",
            owned: 16,
            published: 23,
            type: "Manga",
            genre: "Action"          
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