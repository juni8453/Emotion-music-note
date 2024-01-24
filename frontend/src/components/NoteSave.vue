<template>
  <!-- form-control, mb-1, mb-4 는 bootstrap -->
  <div class="save-note-body">
    <input class="form-control mb-1" v-model="emotion" type="text" placeholder="오늘의 기분을 적어주세요.">
    <label class="save-note-label">예) 행복, 슬픔 등 자유롭게 감정을 적어주세요.</label>
    <textarea class="form-control mb-4" v-model="content" rows="20" placeholder="일기 내용을 적어주세요."></textarea>
    <button class="royalblue-button" @click="searchSong({emotion, content})">
      <font-awesome-icon icon="pen-to-square" /> 노래 등록
    </button>
  </div>
</template>

<script>
import axios from 'axios'
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";

export default {
  name: "SaveNote",
  components: {FontAwesomeIcon},
  data() {
    return {
      emotion: '',
      content: '',
    }
  },

  methods: {
    searchSong(payload) {
      axios.defaults.withCredentials = true;
      const apiServer = process.env.VUE_APP_API_SERVER;

      axios.post(`${apiServer}/api/notes`, payload)
      .then(response => {
        const noteId = response.data;
        this.$store.commit('saveNoteId', noteId);
        this.$router.push(`/song/save/search`);

      }).catch((error) => {
        const errorStatus = error.response.status;

        if (errorStatus === 401) {
          localStorage.removeItem('vuex');
          alert('로그인이 필요합니다.');
          window.location.href = '/';

        } else if (errorStatus === 400) {
          const errorMessages = [];
          const errors = error.response.data.validation;

          for (const errorKey in errors) {
            if (errorKey !== undefined) {
              errorMessages.push(errors[`${errorKey}`]);
            }
          }

          for (let i = 0; i < errorMessages.length; i++) {
            alert(errorMessages[i])
          }
        }
      })
    },

  },
}
</script>

<style scoped>
@import '../assets/CommonStyle.css';

.save-note-body {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: end;
}

.save-note-label {
  font-family: 'Jeju Gothic', serif;
  font-size: 14px;
  margin-bottom: 20px;
}
</style>