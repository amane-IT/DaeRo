import Vue from 'vue'
import Vuex from 'vuex'

import article from './modules/article'
import user from './modules/user'
import place from './modules/place'
import service from './modules/service'


Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    article,
    user,
    place,
    service,
  },
})
