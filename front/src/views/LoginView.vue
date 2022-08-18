<template>
  <div>
    <v-container class="login">
      <v-row>
        <v-col cols="6">
          <img src="https://images.unsplash.com/photo-1606759163253-094133091882?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1887&q=80" alt="">
          </v-col>
        <v-col cols="6" class="d-flex flex-column justify-content-center text-center align-items-center">
          <h1>DaeRo</h1>
          <h3>내 맘대로, 발 닿는 대로 여행</h3>
          <br>
          <form @submit.prevent="login(credentials)">
            <div>
              <label for="username">관리자 코드 </label><br>
              <input class="inputbox" v-model="credentials.code" type="password" id="code" required />
            </div>
            <br>
            <!-- <account-error-list v-if="authError"></account-error-list> -->
            <div>
              <button class="button">로그인</button>
            </div>
          </form>
        </v-col>
      </v-row>
      
    </v-container>
  </div>
</template>

<script>
// import AccountErrorList from '@/components/AccountErrorList.vue'
import { mapActions } from 'vuex'

export default {
    name: 'LoginView',
  //   components: {
  //   AccountErrorList,
  // },
  data () {
    return {
      credentials: {
        code: '',
      }
    }
  },
  // computed: {
  //   ...mapGetters(['authError'])
  // },
  methods: {
    ...mapActions(['login'])
  },

}
</script>

<style scoped>
img {
  height: 100vh;
  width: 37vw;
}
.login {
  margin: 0;
  padding: 0;
}
.inputbox {
  height: 2rem;
  width: 15rem;
  padding: 1rem;
  border-radius: 10px;
  background: #F3F3F3;
}
.button {
  border: 1px solid #152C70;
  background-color: #152C70;
  color: #ffffff;
  margin: 4px;
  padding: 8px 80px;
  border-radius: 20px;
}
</style>