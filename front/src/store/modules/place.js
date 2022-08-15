// import router from '@/router'
import axios from 'axios'
import adminApi from '@/api/adminApi'

export default ({
  state: {
    placeList: [],
    place: {},
    tags: [
      '',
      '바다',
      '산',
      '강',
      '수목원',
      '역사',
      '공원',
      '전망대',
      '체험',
      '먹거리',
      '거리',
      '박물관',
      '불교',
      '성당',
      '교회'
    ]
  },
  getters: {
    placeList: state => state.placeList,
    place: state => state.place,
    tags: state => state.tags,
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
