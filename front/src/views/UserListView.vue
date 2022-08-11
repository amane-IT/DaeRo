<template>
  <div>
		<h2>유저</h2>
    <div>
    <b-table id="usertable" small :fields="fields" :items="userList" responsive="sm">

      <template #cell(index)="data">
        {{ data.item.user_seq }}
      </template>

      <template #cell(email)="data">
        {{ data.item.id }}
      </template>

      <template #cell(nickname)="data">
        {{ data.item.nickname }}
      </template>

      <template #cell(report)="data">
        {{ data.item.report_count }}
      </template>

      <template #cell(regDate)="data">
        {{ data.item.reg_date }}
      </template>

      <template #cell()="data">
        <form @submit.prevent="suspendUser(data.item.user_seq)">
          <v-btn><button>이용 정지</button></v-btn>
        </form>
      </template>
    </b-table>
    </div>

    <!-- <b-pagination
      v-model="page"
      :total-rows="rows"
      :per-page="currentPage"
      aria-controls="usertable"
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
  name: 'userListView',
  data() {
    return {
      page: 1,
      rows: 5,
      fields: [
          { key: 'index', label: '사용자 번호'},
          { key: 'email', label: '이메일' },
          { key: 'nickname', label: '닉네임' },
          { key: 'report', label: '신고당한 횟수'},
          { key: 'regDate', label: '가입일자'}
        ],
    }
	},
	computed: {
    ...mapGetters(['userList', 'totalPage', 'currentPage'])
	},
	methods: {
    ...mapActions(['getUserList', 'suspendUser']),
    handlePage() {
      this.getUserList(this.page)
    }
	},
	created() {
    this.getUserList(this.page)
    }
	
}
</script>

<style>

</style>