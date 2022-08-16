<template>
  <div class="body">
		<v-container>
			<v-row>
				<v-col cols="3">
					게시글 제목
				</v-col>
				<v-col cols="3">{{ article.title }}</v-col>
			</v-row>
		</v-container>		
		<v-container>
			<v-row>
				<v-col cols="3">
				작성자 
			</v-col>
			<v-col cols="3">
				{{ article.nickname }}
			</v-col>
			<v-col cols="2">
				작성일자
			</v-col>
			<v-col cols="3">
				{{ article.created_at }}
			</v-col>
			<form @submit.prevent="deleteArticle(article.article_seq)" class="col-1">
				<v-btn><button>삭제</button></v-btn>
			</form>
			</v-row>
		</v-container>
		<v-container>
			<day-card
			v-for="(day, idx) in article.records" :key="idx" :day="day" :idx="idx">
			</day-card>
		</v-container>
		<v-container>
			<h5>여행 경비</h5>
			<v-container>
				<expenses-card
				v-for="(expenses, idx) in article.trip_expenses" :key="idx" :expenses="expenses">
				</expenses-card>
			</v-container>
		</v-container>
		<v-container>
			<h5>본문</h5>
			<p>{{ article.trip_comment }}</p>
		</v-container>
		<v-container>
			<h5>태그</h5>
			
		</v-container>
		<v-container>
			<v-row>
				<h5 class="col-2">평점 {{ article.rating }}</h5>
				<h5 class="col-2">댓글 {{ article.comments }}</h5>
				<h5 class="col-2">좋아요  {{ article.likes }}</h5>
			</v-row>
		</v-container>
		<v-container>
			<v-row>
				<v-col cols="2">댓글 작성자</v-col>
				<v-col cols="6">내용</v-col>
				<v-col cols="3">작성일자</v-col>
				<v-col cols="1"></v-col>
			</v-row>
			<reply-list
			v-for="(reply, reply_idx) in replyList" :key="reply_idx" :reply="reply">
			</reply-list>
		</v-container>

  </div>
</template>

<script>
import dayCard from '@/components/ArticleDetail/DayCard.vue'
import expensesCard from '@/components/ArticleDetail/ExpensesCard.vue'
import replyList from '@/components/ArticleDetail/ReplyList.vue'

import { mapGetters, mapActions } from 'vuex'

export default {
	name: 'articleDetailView',
	components: { dayCard, expensesCard, replyList },
	data() {
    return {
      articleSeq: this.$route.params.articleSeq,
    }
  },
	computed: {
		...mapGetters(['article', 'replyList'])
	},
	methods: {
		...mapActions(['getArticleDetail', 'getReplyList', 'deleteArticle'])
	},
	created () {
		this.getArticleDetail(this.articleSeq)
		this.getReplyList(this.articleSeq)
	}

}
</script>

<style>

</style>