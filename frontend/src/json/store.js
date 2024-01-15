import {createStore} from 'vuex'
import createPersistedState from 'vuex-persistedstate';

const store = createStore({
  state() {
    return {
      sessionId: null,
      nickname: null,
      profileImageUrl: null,
      saveNoteId: 0,
    }
  },

  mutations: {
    saveSignInUserInfo(state, userinfo) {
      state.sessionId = userinfo.sessionId;
      state.nickname = userinfo.nickname;
      state.profileImageUrl = userinfo.profileImageUrl;
    },

    saveNoteId(state, saveNoteId) {
      state.saveNoteId = saveNoteId;
    }

  },

  actions: {

  },

  plugins: [
    createPersistedState()
  ]
})

export default store;