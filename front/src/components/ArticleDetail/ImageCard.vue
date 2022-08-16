<template>
  <div id="card">
    <img :src="image" alt="tripstamp">
  </div>
</template>

<script>
export default {
  name: 'imageCard',
  props: {
    image: String
  },

}
</script>

<style scoped>
img {
  height: 500px;
}
#card {
  margin: 1rem;
  display: inline-block;
  padding: 5px 5px;
  margin-right: 5px;
}
</style>