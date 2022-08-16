<template>
  <div>
    <v-container>
      <v-row>
        <v-col cols="3">장소명</v-col>
        <v-col cols="9">{{ place.place_name }}</v-col>
      </v-row>
      <v-row>
        <v-col cols="3">주소</v-col>
        <v-col cols="9">{{ place.address }}</v-col>
      </v-row>
      <v-row>
        <v-col cols="3">위도</v-col>
        <v-col cols="9">{{ place.latitude }}</v-col>
      </v-row>
      <v-row>
        <v-col cols="3">경도</v-col>
        <v-col cols="9">{{ place.longitude }}</v-col>
      </v-row>
      <v-row>
        <v-col cols="3">태그</v-col>
        <v-col cols="9">
          <span v-for="(tag, idx) in place.tags" :key="idx">
            <span v-if="idx !== 0">,</span>
            {{ tags[tag] }} 
          </span>
          </v-col>
      </v-row>
      <v-row>
        <v-col cols="3">이미지 경로</v-col>
        <v-col cols="6">{{ place.image_url }}</v-col>
        <!-- <v-col cols="9"><img :src="place.image_url" alt="place"></v-col> -->
      </v-row>
      <v-row>
        <v-col cols="3">설명</v-col>
        <v-col cols="6">{{ place.description }}</v-col>
      </v-row>
    </v-container>
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
    ...mapGetters(['place', 'tags'])
	},
	methods: {
    ...mapActions(['getPlaceDetail'])
	},
	created() {
    this.getPlaceDetail(this.placeSeq)
	}
}
</script>

<style scoped>
img {
  width: 300px;
}
</style>