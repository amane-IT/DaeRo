// import router from '@/router'
import axios from 'axios'
import adminApi from '@/api/adminApi'

export default ({
  state: {
    placeList: [],
    place: {},
  },
  getters: {
    placeList: state => state.placeList,
    place: state => state.place,
  },
  mutations: {
    SET_PLACE_LIST: (state, placeList) => state.placeList = placeList,
    SET_PLACE: (state, place) => state.place = place,
  },
  actions: {
    getPlaceList({ commit }, page) {
      axios({
        url: adminApi.admin.placeList() + '?page=' + page,
        method: 'get'
      })
      .then(res => {
        commit('SET_PLACE_LIST', res.data.results)
        commit('SET_TOTAL_PAGE', res.data.total_page)
        commit('SET_CURRENT_PAGE', res.data.page)
      })
      .catch(err => console.log(err.response.data))
    },
    getPlaceDetail({ commit }, placeSeq) {
        axios({
            url: adminApi.admin.placeDetail(placeSeq),
            method: 'get'
        })
        .then(res => {
            commit('SET_PLACE', res.data)
        })
        .catch(err => console.log(err.response.data))
    }
  }
})
