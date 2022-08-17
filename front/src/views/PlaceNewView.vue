<template>
  <div>
    <place-form :place="place" :action="action"></place-form>
  </div>
</template>

<script>
import placeForm from '@/components/PlaceNew/PlaceForm.vue'

export default {
  name: 'placeNew',
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
      action: 'CREATE',
    }
  },


}
</script>

<style>

</style>