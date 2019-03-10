import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
import { topicList, topicInfo, login, reply } from '@/api/publicApi'
import { GET_TOPIC_LIST, UPDATE_TOPIC_LIST, GET_TOPIC_INFO, LOGIN, LOGIN_OUT, REPLY, TOOGLE_LOAD, TOOGLE_LIST_LOAD } from '@/constants/mutationTypes'

const store = new Vuex.Store({
  state: {
    topics: [],
    topicInfo: {},
    userInfo: {},
    showLoad: false, // 页面等待效果
    showListLoad: false // list划到底后的等待效果
  },

  mutations: {
    [GET_TOPIC_LIST](state, data) {
      state.topics = data
    },

    [UPDATE_TOPIC_LIST](state, data) {
      state.topics = [...state.topics, ...data]
    },

    [GET_TOPIC_INFO](state, data) {
      state.topicInfo = data
    },

    [LOGIN](state, data) {
      state.userInfo = data
    },

    [TOOGLE_LOAD](state, data) {
      if (data) {
        state.showLoad = data
      } else {
        state.showLoad = !state.showLoad
      }
    },

    [TOOGLE_LIST_LOAD](state, data) {
      if (data) {
        state.showListLoad = data
      } else {
        state.showListLoad = !state.showListLoad
      }
    },

    [LOGIN_OUT](state) {
      state.userInfo = {}
      localStorage.removeItem('userInfo')
    }
  },

  actions: {
    [GET_TOPIC_LIST]({ commit }, data) {
      commit(TOOGLE_LOAD, true)
      return topicList(data).then((res) => {
        if (res.success) {
          commit(TOOGLE_LOAD, false)
          commit(GET_TOPIC_LIST, res.data)
        }
      })
    },

    [UPDATE_TOPIC_LIST]({ commit }, data) {
      commit(TOOGLE_LIST_LOAD, true)

      return topicList(data).then((res) => {
        if (res.success) {
          commit(TOOGLE_LIST_LOAD, false)
          commit(UPDATE_TOPIC_LIST, res.data)
        }
      })
    },

    [GET_TOPIC_INFO]({ commit }, data) {
      commit(TOOGLE_LOAD, true)
      topicInfo(data).then((res) => {
        if (res.success) {
          commit(TOOGLE_LOAD, false)
          commit(GET_TOPIC_INFO, res.data)
        }
      })
    },

    [LOGIN]({ commit }, data) {
      return login(data).then((res) => {
        if (res.success) {
          const user = {
            loginname: res.loginname,
            id: res.id,
            avatar_url: res.avatar_url,
            accesstoken: data.accesstoken
          }
          localStorage.setItem('userInfo', JSON.stringify(user))
          commit(LOGIN, user)
        }
      })
    },

    [REPLY]({ commit, dispatch }, data) {
      const topicId = data.topicId
      delete data.topicId
      reply(data, topicId).then((res) => {
        if (res.success) {
          dispatch(GET_TOPIC_INFO, topicId)
        }
      })
    }
  }
})

export default store
