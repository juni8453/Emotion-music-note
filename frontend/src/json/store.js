import {createStore} from 'vuex'

const store = createStore({
  state() {
    return {
      sessionId: '',
    }
  },

  mutations: {
    saveSessionId(state, data) {
      state.sessionId = data;
    }
  },

  actions: {

  }

})

export default store;