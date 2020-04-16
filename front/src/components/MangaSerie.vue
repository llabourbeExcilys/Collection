<template>
<div>   
  <v-card
      class="mx-auto"
      max-width="1000"
    >
      <v-card-title>
        {{item.title}}
      </v-card-title>
      <v-card-subtitle>
        {{item.edition}} ({{item.editeur}})
      </v-card-subtitle>
      <v-container fluid>
        <v-row 
          align='center'
          justify='center'
          no-gutters
        >
          <v-col
            :cols="6">           
              {{item.owned}} / {{item.published}}
          </v-col>
          <v-col :cols="3">
            <v-card
              class="ma-3 pa-6"
              outlined
              tile
            >
              {{item.type}}
            </v-card>          
          </v-col>
          <v-col :cols="3">
            <v-card
              class="ma-3 pa-6"
              outlined
              tile
            >
              {{item.genre}}
            </v-card> 
          </v-col>
        </v-row>
      </v-container>
      <v-card-actions>
        <v-spacer/>
        <v-btn
          icon
          @click="show = !show"
        >
          <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
        </v-btn>
      </v-card-actions>
      <v-expand-transition>
        <div v-show="show">
          <threebook
            :title="item.title"
            :length="item.published"
          />
        </div>
      </v-expand-transition>
    </v-card>


</div>
</template>

<script>

import threebook from '@/components/ThreeBook';

export default {
  name: 'manga-serie',  
  components: {
    threebook
  },
  props:{
    item :{
      type: Object,
      required: true
    }
  },
  data () {
    return {
      show: false
    }
  },
  mounted () {

  },
  methods: {
  }
}
</script>
<style scoped>

</style>