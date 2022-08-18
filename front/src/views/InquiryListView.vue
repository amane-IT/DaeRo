<template>
	<div>
		<h2>1:1 문의</h2>
		<br>
		<div>
    <b-table id="inquirytable" small :fields="fields" :items="inquiryList" responsive="sm lg">

      <template #cell(title)="data">
        <b><router-link class="link" id="router" :to="{ name: 'inquiryDetail', params: { inquirySeq: data.item.inquiry_seq } }">{{ data.item.title }}</router-link></b>
      </template>

    </b-table>
    </div>

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
	name: 'inquiryListView',
	data () {
		return {
			page: 1,
			fields: [
				{ key: 'answer_yn', label: '답변여부'},
				{ key: 'title', label: '제목' },
				{ key: 'user_name', label: '작성자' },
				{ key: 'created_at', label: '문의일자' },
				{ key: 'answer_at', label: '답변일자' },
			],
		}
	},
	computed: {
		...mapGetters(['inquiryList', 'totalPage', 'currentPage'])
	},
	methods: {
		...mapActions(['getInquiryList']),
		handlePage() {
      this.getInquiryList(this.page)
    }
	},
	created() {
		this.getInquiryList(this.page)
	}
}
</script>

<style>

</style>