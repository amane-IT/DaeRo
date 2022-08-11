<template>
  <div>
    <h1>DaeRo</h1>
    <h3>내 맘대로, 발 닿는 대로 여행</h3>
    <br>
    <form @submit.prevent="login(credentials)">
      <div>
        <label for="username">관리자 코드 </label><br>
        <input class="inputbox" v-model="credentials.code" type="text" id="code" required />
      </div>
      <br>
      <account-error-list v-if="authError"></account-error-list>
      <div>
        <button class="button">Login</button>
      </div>
    </form>

  </div>
</template>

<script>
import AccountErrorList from '@/components/AccountErrorList.vue'
import { mapActions, mapGetters } from 'vuex'

export default {
    name: 'LoginView',
    components: {
    AccountErrorList,
  },
  data () {
    return {
      credentials: {
        username: '',
        password: '',
      }
    }
  },
  computed: {
    ...mapGetters(['authError'])
  },
  methods: {
    ...mapActions(['login'])
  },

}
</script>

<style>

</style>