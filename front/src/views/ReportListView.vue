<template>
  <div>
		<h2>신고</h2>
    <div>
    <b-table id="reporttable" small :fields="fields" :items="reportList" responsive="sm">
      <template #cell(detail)="data">
        <div v-if="data.item.content_type === 'article'">
          <b><router-link class="link" id="router" :to="{ name: 'articleDetail', params: { articleSeq: data.item.content_seq } }">상세보기</router-link></b>
        </div>
        <div v-else-if="data.item.content_type === 'reply'">
          <b><router-link class="link" id="router" :to="{ name: 'replyDetail', params: { replySeq: data.item.content_seq } }">상세보기</router-link></b>
        </div>
        <div v-else>
          유저 디테일 뷰?
          <!-- <b><router-link class="link" id="router" :to="{ name: 'placeDetail', params: { articleSeq: data.item.content_seq } }">상세보기</router-link></b> -->
        </div>
      </template> 

    </b-table>
    </div>

    <!-- <b-pagination
      v-model="page"
      :total-rows="rows"
      aria-controls="placetable"
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
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'reportListView',
  data() {
    return {
      page: 1,
      fields: [
        { key: 'content_type', label: '카테고리'},
        { key: 'report_categories_seq', label: '신고유형' },
        { key: 'reporter_user_seq', label: '신고인'},
        { key: 'reported_user_seq', label: '피신고인'},
        { key: 'handled_yn', label: '처리여부'},
        { key: 'reported_at', label: '신고일자' },
        { key: 'detail', label: '상세' }
        ],
        types: [] // 신고 유형 저장
      }
	},
	computed: {
    ...mapGetters(['reportList', 'totalPage', 'currentPage'])
	},
	methods: {
    ...mapActions(['getReportList']),
        handlePage() {
      this.getReportList(this.page)
    }
	},
	created() {
    this.getReportList(this.page)
	}

}
</script>

<style>

</style>