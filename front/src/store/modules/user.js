import axios from 'axios'
import adminApi from '@/api/adminApi'
import router from '@/router'

export default ({
  state: {
    session: localStorage.getItem('session') || '',
    authError: null,
    userList: [],
  },
  getters: {
    session: state => state.authError,
    userList: state => state.userList,
  },
  mutations: {
    SET_SESSION: (state, session) => state.session = session,
    SET_USER_LIST: (state, userList) => state.userList = userList,
  },
  actions: {
    login({ commit }, credentials) {
      axios({
          url: adminApi.admin.login(),
          method: 'post',
          data: credentials
      })
          .then(res =>{
              this.$session.set('user_no', res.data.user)
              // commit('SET_SESSION', this.$session.get('user_no'))
           })
          .catch(err => {
              console.error(err.response.data)
              commit('SET_AUTH_ERROR', err.response.data)
          })
    },
    getUserList({ commit }, page) {
      axios({
        url: adminApi.admin.userInfo() + '?page=' + page,
        method: 'get'
      })
      .then(res => {
        commit('SET_USER_LIST', res.data.results)
        commit('SET_TOTAL_PAGE', res.data.total_page)
        commit('SET_CURRENT_PAGE', res.data.page)
      })
      .catch(err => console.log(err.response.data))
    },
    suspendUser({ userSeq, day }) {
      axios({
        url: adminApi.admin.suspend(userSeq),
        method: 'put',
        data: day
      })
      .then(() => {
        router.push({ name: 'userList' })
      })
      .catch(err => err.response.data)
    }
  }
})
