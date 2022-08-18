<template>
  <v-container>
    <v-row>
      <v-col cols="1">{{ reply.nickname }}</v-col>
      <v-col cols="5">{{ reply.content }}</v-col>
      <v-col cols="2">{{ reply.created_at }}</v-col>
      <v-col cols="1">
        <form @submit.prevent="deleteReply(reply.reply_seq)">
          <v-btn><button>삭제</button></v-btn>
        </form>
      </v-col>
    </v-row>
    
    <div class="ms-3">
      <rereply-list
      v-for="(rereply, idx) in reply.rereply" :key="idx" :rereply="rereply"></rereply-list>
    </div>
  </v-container>
</template>

<script>
import rereplyList from '@/components/ArticleDetail/RereplyList.vue'

import { mapActions } from 'vuex'

export default {
    name: 'replyList',
    components: { rereplyList },
    props : {
        reply: {
            type: Object
        }
    },
    methods: {
      ...mapActions(['deleteReply'])
    }

}
</script>

<style>

</style>