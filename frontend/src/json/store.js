import {createStore} from 'vuex'
import createPersistedState from 'vuex-persistedstate';

const store = createStore({
  state() {
    return {
      sessionId: null,
      nickname: null,
      profileImageUrl: null,
    }
  },

  mutations: {
    saveSignInUserInfo(state, userinfo) {
      state.sessionId = userinfo.sessionId;
      state.nickname = userinfo.nickname;
      state.profileImageUrl = userinfo.profileImageUrl;
    },
  },

  actions: {

  },

  plugins: [
    createPersistedState()
  ]
})

export default store;