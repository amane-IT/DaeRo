import router from '@/router'
import axios from 'axios'
import adminApi from '@/api/adminApi'

export default ({
  state: {
    totalPage: 1,
    currentPage: 1,
    articleList: [],
    article: {},
    replyList: [],
    reply: {},
    userList: [],
    images: [],
  },
  getters: {
    totalPage: state => state.totalPage,
    currentPage: state => state.currentPage,
    articleList: state => state.articleList,
    article: state => state.article,
    replyList: state => state.replyList,
    reply: state => state.reply,
    images: state => state.images,
  },
  mutations: {
    SET_TOTAL_PAGE: (state, page) => state.totalPage = page,
    SET_CURRENT_PAGE: (state, page) => state.currentPage = page,
    SET_ARTICLE_LIST: (state, articleList) => state.articleList = articleList,
    SET_ARTICLE: (state, article) => state.article = article,
    SET_REPLY_LIST: (state, replyList) => state.replyList = replyList,
    SET_REPLY : (state, reply) => state.reply = reply,
    SET_IMAGES: (state, images) => state.images = images,
  },
  actions: {
    getArticleList({ commit }, page) {
        axios({
            url: adminApi.admin.articleList() + '?page=' + page,
            method: 'get'
        })
        .then(res => {
          commit('SET_ARTICLE_LIST', res.data.results)
          commit('SET_TOTAL_PAGE', res.data.total_page)
          commit('SET_CURRENT_PAGE', res.data.page)
        })
        .catch(err => {
            console.error(err.response)
        })
    },
    getArticleDetail({ commit, getters }, articleSeq) {
      axios({
        url: adminApi.admin.articleDetail(articleSeq),
        method: 'get'
      })
      .then(res => {
        commit('SET_ARTICLE', res.data)
        var imageList = []
        getters.article.records.forEach(record => {
          record.trip_stamps.forEach(stamp => {
            imageList.push(stamp.image_url)
          })
        })
        commit('SET_IMAGES', imageList)
      })
      .catch(err => console.log(err.response.data))
    },
    getReplyList({ commit }, articleSeq) {
      axios({
        url: adminApi.admin.replyList(articleSeq),
        method: 'get'
      })
      .then(res => {
        commit('SET_REPLY_LIST', res.data.results)
      })
      .catch(err => console.log(err.response.data))
    },
    getReplyDetail({ commit }, replySeq) {
      axios({
        url: adminApi.admin.replyDetail(replySeq),
        method: 'get'
      })
      .then(res => {
        commit('SET_REPLY', res.data)
      })
      .catch(err => console.log(err.response.data))
    },
    deleteArticle({ commit }, articleSeq) {
      if (confirm('삭제하시겠습니까?')) {
        axios({
          url: adminApi.admin.deleteArticle(articleSeq),
          method: 'delete'
        })
        .then(() => {
          commit('SET_ARTICLE', {})
          router.push({ name: 'articleList' })
        })
        .catch(err => console.error(err.response))
      }
    },
    deleteReply({ commit }, replySeq) {
      if (confirm('삭제하시겠습니까?')) {
        axios({
          url: adminApi.admin.deleteReply(replySeq),
          method: 'delete'
        })
        .then(() => {
          commit('SET_REPLY', {})
          // router.push({ name: 'articleDetail' })
        })
        .catch(err => console.error(err.response))
      }
    },
    
  }
})
