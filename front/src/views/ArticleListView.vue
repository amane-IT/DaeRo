<template>
  <div>
		<h2>게시글</h2>
		<div>
    <b-table id="articletable" small :fields="fields" :items="articleList" responsive="sm">

      <template #cell(index)="data">
        {{ data.index + 1 }}
      </template>

      <template #cell(title)="data">
        <b><router-link class="link" id="router" :to="{ name: 'articleDetail', params: { articleSeq: data.item.article_seq } }">{{ data.item.title }}</router-link></b>
      </template>

      <template #cell(nickname)="data">
        {{ data.item.nickname }}
      </template>

      <template #cell(createdAt)="data">
        {{ data.item.created_at }}
      </template>
    </b-table>
		</div>
		<!-- <b-pagination
      v-model="page"
      :total-rows="rows"
      :per-page="currentPage"
      aria-controls="articletable"
			@input="handlePage"
    ></b-pagination> -->

		<v-pagination
			v-model="page"
			@input="handlePage"
			:length="totalPage"
		></v-pagination>

  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
	name: 'articleListView',
	data() {
		return {
			page: 1,
			total: 1,
			fields: [
          'index',
          { key: 'title', label: '제목' },
          { key: 'nickname', label: '작성자' },
          { key: 'createdAt', label: '작성일자'}
        ],
			rows: 5,
		}
	},
	computed: {
			...mapGetters(['articleList', 'totalPage', 'currentPage'])
	},
	methods: {
			...mapActions(['getArticleList']),
			handlePage() {
				this.getArticleList(this.page)
    }

	},
	created() {
			this.getArticleList(this.currentPage)
	}

}
</script>

<style>
.d-flex {
    flex-flow: row wrap;
    justify-content: center;
}
</style>