<template>
  <div class="body">
    <h2>댓글</h2>
    <v-container>
      <v-row>
        <v-col cols="2">작성자</v-col>
        <v-col cols="2">{{ reply.nickname }}</v-col>
        <v-col cols="2">작성일자</v-col>
        <v-col cols="3">{{ reply.created_at}}</v-col>
        <v-col cols="3">
          <form @submit.prevent="deleteReply(reply.reply_seq)">
            <v-btn><button>삭제</button></v-btn>
          </form>
        </v-col>
      </v-row>
      <hr>
      <h6>내용</h6>
      {{ reply.content }}
    </v-container>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'replyDetailView',
  data() {
    return {
      replySeq: this.$route.params.replySeq,
    }
  },
  computed: {
		...mapGetters(['reply'])
	},
	methods: {
		...mapActions(['getReplyDetail'])
	},
	created () {
		this.getReplyDetail(this.replySeq)
	}
}
</script>

<style>

</style>