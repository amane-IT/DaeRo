<template>
  <div>

  </div>
</template>

<script>
export default {
  name: 'placeUpdate',
  components : { placeForm },
  data() {
    return {
      place: {
        pk: null,
        name: '',
        address: '',
        latitude: null,
        longitude: null,
        imageUrl: '',
        description: '',
      },
      action: 'UPDATE',
    }
  }
}
</script>

<style>

</style>