<template>
  <div>
    {{ reply }}
    <form @submit.prevent="deleteReply(reply.reply_seq)">
      <v-btn><button>삭제</button></v-btn>
    </form>
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