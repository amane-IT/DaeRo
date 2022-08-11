<template>
  <div>
    {{ place }}
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'placeDetailView',
  data() {
    return {
      placeSeq: this.$route.params.placeSeq,
    }
  },
	computed: {
    ...mapGetters(['place'])
	},
	methods: {
    ...mapActions(['getPlaceDetail'])
	},
	created() {
    this.getPlaceDetail(this.placeSeq)
	}
}
</script>

<style>

</style>