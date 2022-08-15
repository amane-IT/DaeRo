import axios from 'axios'
import adminApi from '@/api/adminApi'

export default ({
  state: {
    reportList: [],
    inquiryList: [],
    inquiry: {},
    noticeList: [],
  },
  getters: {
    reportList: state => state.reportList,
    inquiryList: state => state.inquiryList,
    inquiry: state => state.inquiry,
    noticeList: state => state.noticeList,
  },
  mutations: {
    SET_REPORT_LIST: (state, reportList) => state.reportList = reportList,
    SET_INQUIRY_LIST: (state, inquiryList) => state.inquiryList = inquiryList,
    SET_INQUIRY : (state, inquiry) => state.inquiry = inquiry,
    SET_NOTICE_LIST: (state, noticeList) => state.noticeList = noticeList,
  },
  actions: {
    getReportList({ commit }, page) {
        axios({
          url: adminApi.admin.reportList() + '?page=' + page,
          method: 'get'
        })
        .then(res => {
          commit('SET_REPORT_LIST', res.data.results)
          commit('SET_TOTAL_PAGE', res.data.total_page)
          commit('SET_CURRENT_PAGE', res.data.page)
        })
        .catch(err => console.log(err.response.data))
    },
    // 신고 여부 전환
    handledReport(reportSeq) {
        axios({
            url: adminApi.admin.handledReport(reportSeq),
            method: 'put'
        })
        .then(res => {
            console.log(res)
        })
    },
    getInquiryList({ commit }, page) {
        axios({
            url: adminApi.admin.inquiryList() + '?page=' + page,
            method: 'get'
        })
        .then(res => {
            commit('SET_INQUIRY_LIST', res.data.results)
        })
        .catch(err => console.log(err.response.data))
    },
    getInquiryDetail({ commit }, inquirySeq) {
      axios({
        url: adminApi.admin.inquiryDetail(inquirySeq),
        method: 'get'
      })
      .then(res => {
        commit('SET_INQUIRY', res.data)
      })
    },
    getNoticeList({ commit }, page) {
      axios({
        url: adminApi.admin.noticeList() + '?page=' + page,
        method: 'get'
      })
      .then(res => {
        commit('SET_NOTICE_LIST', res.data.results)
        commit('SET_TOTAL_PAGE', res.data.total_page)
        commit('SET_CURRENT_PAGE', res.data.page)
      })
    }
  }
})
