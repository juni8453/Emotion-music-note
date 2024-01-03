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
    saveSignInUserInfo(state, data) {
      state.sessionId = data.jsessionId;
      state.nickname = data.nickname;
      state.profileImageUrl = data.profileImageUrl;
    }
  },

  actions: {

  },

  plugins: [
    createPersistedState()
  ]
})

export default store;