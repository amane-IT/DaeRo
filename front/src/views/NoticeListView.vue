<template>
  <div class="body">
    <h2>공지사항</h2>
    <br>
		<div>
    <b-table id="noticetable" stacked small :fields="fields" :items="noticeList" responsive="sm">

      <template #cell(index)="data">
        {{ data.index + 1 }}
      </template>

      <template #cell(title)="data">
        <details>
          <summary>{{ data.item.title }}</summary>
          <p>{{ data.item.content }}</p>
        </details>
      </template>
      <template #cell(admin_seq)="data">
        {{ data.item.admin_seq }}
      </template>
      <template #cell(created_at)="data">
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
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'noticeListView',
  data() {
		return {
			page: 1,
			total: 1,
			fields: [
          'index',
          { key: 'title', label: '제목' },
          { key: 'admin_seq', label: '작성자' },
          { key: 'created_at', label: '작성일자'},
        ],
			rows: 5,
		}
	},
	computed: {
			...mapGetters(['noticeList', 'totalPage', 'currentPage'])
	},
	methods: {
    ...mapActions(['getNoticeList']),
    handlePage() {
      this.getNoticeList(this.page)
    },
	},
	created() {
			this.getNoticeList(this.page)
	}
}
</script>

<style>
summary {
  list-style: none;
}
summary::-webkit-details-marker {
  display: none;
}

</style>