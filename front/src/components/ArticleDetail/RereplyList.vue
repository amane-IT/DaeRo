<template>
  <v-container>
    <v-row>
      <v-col cols="2">{{ rereply.nickname }}</v-col>
      <v-col cols="6">{{ rereply.content }}</v-col>
      <v-col cols="3">{{ rereply.created_at }}</v-col>
      <v-col cols="1">
        <form @submit.prevent="deleteReply(rereply.reply_seq)">
          <v-btn><button>삭제</button></v-btn>
        </form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'rereplyList',
  props: {
    rereply: Object
  },
  methods: {
    ...mapActions(['deleteReply'])
  }

}
</script>

<style>

</style>