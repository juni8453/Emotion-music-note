import {createStore} from 'vuex'
import createPersistedState from 'vuex-persistedstate';

const store = createStore({
  state() {
    return {
      sessionId: null,
      nickname: null,
      profileImageUrl: null,
      saveNoteId: 0,
      updateNoteId: 0,
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
    },

    updateNoteId(state, updateNoteId) {
      state.updateNoteId = updateNoteId;
    }

  },

  actions: {

  },

  plugins: [
    createPersistedState()
  ]
})

export default store;