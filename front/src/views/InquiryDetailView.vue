<template>
  <div class="body">
    <h2>1:1 문의</h2>
    <v-container>
      <v-row>
        <v-col cols="2">
          제목
        </v-col>
        <v-col cols="8">
          {{ inquiry.title }}
        </v-col>
      </v-row>
      <hr>
      <div class="content py-3">
        <h6>내용</h6>
        <div>{{ inquiry.content }}</div>
      </div>
      <div class="answer my-5" v-if="inquiry.answer">
        <h6>답변</h6>
        <hr>
        <div>{{ inquiry.answer }}</div>
      </div>
    </v-container>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'inquiryDetailView',
  data () {
    return {
      inquirySeq: this.$route.params.inquirySeq,
    }
  },
  computed: {
    ...mapGetters(['inquiry'])
  },
  methods: {
    ...mapActions(['getInquiryDetail'])
  },
  created() {
    this.getInquiryDetail(this.inquirySeq)
  }
}
</script>

<style>

</style>