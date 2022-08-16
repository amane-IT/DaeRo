<template>
  <div class="body">
		<h2>신고</h2>
    <br>
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
          <b><router-link class="link" id="router" :to="{ name: 'userDetail', params: { userSeq: data.item.reported_user_seq } }">상세보기</router-link></b>
        </div>
      </template>
      <template #cell(handled_yn)="data">
        <div v-if="data.item.handled_yn === 'y'">처리완료</div>
        <div v-else>
          <form @submit.prevent="handledReport(data.item.report_seq)">
            <v-btn><button>처리</button></v-btn>
          </form>
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
        { key: 'reporter_user_nickname', label: '신고인'},
        { key: 'reported_user_nickname', label: '피신고인'},
        { key: 'reported_at', label: '신고일자' },
        { key: 'detail', label: '상세' },
        { key: 'handled_yn', label: '처리여부'},
        ],
        types: [] // 신고 유형 저장
      }
	},
	computed: {
    ...mapGetters(['reportList', 'totalPage', 'currentPage'])
	},
	methods: {
    ...mapActions(['getReportList', 'handledReport']),
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