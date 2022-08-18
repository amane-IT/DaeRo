import axios from 'axios'
import adminApi from '@/api/adminApi'

export default ({
  state: {
    reportList: [],
    inquiryList: [],
    inquiry: {},
    noticeList: [],
    faqList: [],
    reportType: [
      '',
      '스팸 또는 광고',
      '나체 이미지 또는 성적 행위',
      '혐오 발언 또는 암시',
      '폭력 또는 위험한 조직',
      '따돌림이나 괴롭힘',
      '부적절한 언어 사용',
      '불법 또는 규제 상품 판매',
      '지적 재산권 침해',
      '자살 또는 자해',
      '섭식 장애',
      '사기 또는 거짓',
      '거짓 정보'
    ],
  },
  getters: {
    reportList: state => state.reportList,
    inquiryList: state => state.inquiryList,
    inquiry: state => state.inquiry,
    noticeList: state => state.noticeList,
    faqList: state => state.faqList,
    reportType: state => state.reportType,
  },
  mutations: {
    SET_REPORT_LIST: (state, reportList) => state.reportList = reportList,
    SET_INQUIRY_LIST: (state, inquiryList) => state.inquiryList = inquiryList,
    SET_INQUIRY : (state, inquiry) => state.inquiry = inquiry,
    SET_NOTICE_LIST: (state, noticeList) => state.noticeList = noticeList,
    SET_FAQ_LIST: (state, faqList) => state.faqList = faqList,
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
        commit('SET_TOTAL_PAGE', res.data.total_page)
        commit('SET_CURRENT_PAGE', res.data.page)
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
    },
    getFaqList({ commit }, page) {
      axios({
        url: adminApi.admin.faqList() + '?page=' + page,
        method: 'get'
      })
      .then(res => {
        commit('SET_FAQ_LIST', res.data.results)
        commit('SET_TOTAL_PAGE', res.data.total_page)
        commit('SET_CURRENT_PAGE', res.data.page)
      })
      .catch(err => console.log(err.response.data))
    }
  }
})
