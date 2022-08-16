<template>
  <div class="body">
    <v-container>
      <v-row>
        <v-col cols="2"><img :src="user.profile_url" alt="profile_img"></v-col>
        <v-col cols="5"><h3>{{ user.nickname }}</h3></v-col>
        <v-col cols="2"><h6>배지 갯수 {{ user.badge_count }}</h6></v-col>
        <v-col cols="3">
          <h6>신고 횟수 5</h6>
          <form @submit.prevent="suspendUser(user.user_seq)">
            <v-btn><button>이용 정지</button></v-btn>
          </form>
        </v-col>
      </v-row>

    </v-container>
    <hr>
    <h4>작성한 게시글</h4>
    <b-table small :fields="articleFields" :items="user.articles" responsive="sm">
      <template #cell(index)="data">
        {{ data.index + 1 }}
      </template>

      <template #cell(title)="data">
        <b><router-link class="link" id="router" :to="{ name: 'articleDetail', params: { articleSeq: data.item.article_seq } }">{{ data.item.title }}</router-link></b>
      </template>

      <template #cell(created_at)="data">
        {{ data.item.created_at }}
      </template>
    </b-table>
    <hr>
    <h4>작성한 댓글</h4>
    <b-table small :fields="replyFields" :items="user.replies" responsive="sm">
      <template #cell(index)="data">
        {{ data.index + 1 }}
      </template>

      <template #cell(content)="data">
        <b><router-link class="link" id="router" :to="{ name: 'replyDetail', params: { replySeq: data.item.reply_seq } }">{{ data.item.content }}</router-link></b>
      </template>

      <template #cell(article_seq)="data">
        <b><router-link class="link" id="router" :to="{ name: 'articleDetail', params: { articleSeq: data.item.article_seq } }">게시글 보기</router-link></b>
      </template>
    </b-table>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'userDetailView',
  data () {
    return {
      userSeq: this.$route.params.userSeq,
      articleFields: [
        'index',
        { key: 'title', label: '제목' },
        { key: 'createdAt', label: '작성일자'}
      ],
      replyFields: [
        'index',
        { key: 'content', label: '댓글 내용' },
        { key: 'article_seq', label: ''}
      ]
    }
  },
  computed: {
    ...mapGetters(['user'])
  },
  methods: {
    ...mapActions(['getUserDetail'])
  },
  created () {
    this.getUserDetail(this.userSeq)
  }

}
</script>

<style scoped>
img {
  width: 100px;
  height: 100px;
}
</style>