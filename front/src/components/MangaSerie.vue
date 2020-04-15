<template>
<div>  
  <p>{{item.title}} - {{item.length}}</p>
  <threebook
    :title="item.title"
    :length="item.length"
  />
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
    return {}
  },
  mounted () {

  },
  methods: {
  }
}
</script>
<style scoped>

</style>