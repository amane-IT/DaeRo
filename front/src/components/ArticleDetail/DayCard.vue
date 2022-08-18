<template>
  <div>
    <h5>Day {{ idx+1 }}</h5>
    {{ day.day_comment }}
  </div>
</template>

<script>
export default {
  name: 'dayCard',
  props: {
    day: {
      type: Object
    },
    idx: {
      type: Number
    }
    
  }

}
</script>

<style>

</style>