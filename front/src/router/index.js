import Vue from 'vue'
import VueRouter from 'vue-router'

import LoginView from '@/views/LoginView'
import ArticleListView from '@/views/ArticleListView'
import ArticleDetailView from '@/views/ArticleDetailView'
import ReplyList from '@/components/ArticleDetail/ReplyList'
import ReplyDetailView from '@/views/ReplyDetailView'
import UserListView from '@/views/UserListView'
import PlaceListView from '@/views/PlaceListView'
import PlaceDetailView from '@/views/PlaceDetailView'
import ReportListView from '@/views/ReportListView'
import InquiryListView from '@/views/InquiryListView'
import InquiryDetailView from '@/views/InquiryDetailView'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: LoginView
  },
  {
    path: '/articles',
    name: 'articleList',
    component: ArticleListView
  },
  {
    path: '/article/:articleSeq',
    name: 'articleDetail',
    component: ArticleDetailView
  },
  {
    path: '/article/:articleSeq/reply',
    name: 'replyList',
    component: ReplyList
  },
  {
    path: '/reply/:replySeq',
    name: 'replyDetail',
    component: ReplyDetailView
  },
  {
    path: '/users',
    name: 'userList',
    component: UserListView
  },
  {
    path: '/places',
    name: 'placeList',
    component: PlaceListView
  },
  {
    path: '/place/:placeSeq',
    name: 'placeDetail',
    component: PlaceDetailView
  },
  {
    path: '/reports',
    name: 'reportList',
    component: ReportListView
  },
  {
    path: '/inquiries',
    name: 'inquiryList',
    component: InquiryListView
  },
  {
    path: '/inquiry/:inquirySeq',
    name: 'inquiryDetail',
    component: InquiryDetailView
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
