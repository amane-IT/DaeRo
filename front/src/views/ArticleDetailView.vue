<template>
  <div>
		<v-container>
			<v-row>
				<v-col cols="2">
					게시글 제목
				</v-col>
				<v-col cols="2">{{ article.title }}</v-col>
			</v-row>
		</v-container>		
		<v-container>
			<v-row>
				<v-col cols="2">
				작성자 
			</v-col>
			<v-col cols="2">
				{{ article.nickname }}
			</v-col>
			<v-col cols="1">
				작성일자
			</v-col>
			<v-col cols="2">
				{{ article.created_at }}
			</v-col>
			<form @submit.prevent="deleteArticle(article.article_seq)" class="col-1">
				<v-btn><button>삭제</button></v-btn>
			</form>
			</v-row>
		</v-container>
		<v-container>
			<div id="imageSwiper">
				<swiper ref="filterSwiper" :options="swiperOption" role="tablist">
					<swiper-slide role="tab">
						<image-card v-for="(image, idx) in images" :key="idx" :image="image">
						</image-card>
					</swiper-slide>
				</swiper>
			</div>
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
				<h5 class="col-1">평점 {{ article.rating }}</h5>
				<h5 class="col-1">댓글 {{ article.comments }}</h5>
				<h5 class="col-1">좋아요  {{ article.likes }}</h5>
			</v-row>
		</v-container>
		<v-container>
			<v-row>
				<v-col cols="1">댓글 작성자</v-col>
				<v-col cols="5">내용</v-col>
				<v-col cols="2">작성일자</v-col>
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
import imageCard from '@/components/ArticleDetail/ImageCard.vue'
import { swiper, swiperSlide } from 'vue-awesome-swiper'
import 'swiper/dist/css/swiper.css'

import { mapGetters, mapActions } from 'vuex'

export default {
	name: 'articleDetailView',
	components: { dayCard, expensesCard, replyList, imageCard, swiper, swiperSlide },
	data() {
    return {
      articleSeq: this.$route.params.articleSeq,
			swiperOption: {
				slidesPerView: 'auto',
				spaceBetween: 10, // swiper-slide 사이의 간격 지정
				slidesOffsetBefore: 10, // slidesOffsetBefore는 첫번째 슬라이드의 시작점에 대한 변경할 때 사용
				slidesOffsetAfter: 100, // slidesOffsetAfter는 마지막 슬라이드 시작점 + 마지막 슬라이드 너비에 해당하는 위치의 변경이 필요할 때 사용
				freeMode: true, // freeMode를 사용시 스크롤하는 느낌으로 구현 가능
				centerInsufficientSlides: true, // 컨텐츠의 수량에 따라 중앙정렬 여부를 결정함
      },
    }
  },
	computed: {
		...mapGetters(['article', 'replyList', 'images'])
	},
	methods: {
		...mapActions(['getArticleDetail', 'getReplyList', 'deleteArticle']),
	},
	created () {
		this.getArticleDetail(this.articleSeq)
		this.getReplyList(this.articleSeq)
	}

}
</script>

<style lang="scss" scoped>
#imageSwiper {
  white-space:nowrap; 
  overflow-x: hidden; 
  text-align:center;
  padding: 3vh 2vw 0 0;
	margin: 0 2vw 0 0;
}

.swiper-container {
  .swiper-wrapper {
    .swiper-slide {
      width: auto;
      min-width: 56px; 
      padding: 0px 14px;
      font-size: 14px;
      line-height: 36px;
      text-align: center;
      color: #84868c;
      border: 0;
      border-radius: 18px;
      background: #dbcfb000;
      appearance: none;
      cursor: pointer;
    }
  }
}
.swiper-container{
	margin: 0 50px 0 0;
}
</style>