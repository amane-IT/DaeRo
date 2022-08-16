<template>
  <div>
    <div v-for="(stamp, idx) in record.trip_stamps" :key="idx">
      <img :src="stamp.image_url" alt="tripstamp">
    </div>
  </div>
</template>

<script>
export default {
  name: "imageCard",
  props: {
    record: Object
  }
}
</script>

<style scoped>
img {
  height: 500px;
}
</style>