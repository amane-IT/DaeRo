<template>
  <div>
    <h2>FAQ</h2>
    <br>
		<div>
    <b-table id="faqtable" stacked small :fields="fields" :items="faqList" responsive="sm">

      <template #cell(faq_seq)="data">
        {{ data.item.faq_seq }}
      </template>

      <template #cell(title)="data">
        <details>
          <summary>{{ data.item.title }}</summary>
          <p>{{ data.item.content }}</p>
        </details>
      </template>
      <template #cell(admin_name)="data">
        {{ data.item.admin_name }}
      </template>
      <template #cell(created_at)="data">
        {{ data.item.created_at }}
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
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'faqListView',
  data () {
    return {
      page: 1,
      fields: [
        { key: 'faq_seq', label: 'Index'},
        { key: 'title', label: '제목'},
        { key: 'created_at', label: '작성일자'},
        { key: 'admin_name', label: '작성자'},
      ]
    }
  },
  computed: {
    ...mapGetters(['faqList', 'totalPage', 'currentPage'])
  },
  methods: {
    ...mapActions(['getFaqList']),
        handlePage() {
      this.getFaqList(this.page)
    },
  },
  created() {
    this.getFaqList(this.page)
  }
}
</script>

<style scoped>
summary {
  list-style: none;
}
summary::-webkit-details-marker {
  display: none;
}
</style>