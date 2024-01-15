<template>
  <div class="note-save-box">
    <div class="note-input">
      <input v-model="emotion" type="text" class="form-control" placeholder="오늘의 기분을 적어주세요.">
      <label class="label-font mt-1 mb-3">예) 행복, 슬픔 ...</label>
    </div>
    <textarea v-model="content" class="form-control mb-4" rows="20"
              placeholder="일기 내용을 적어주세요."></textarea>
  </div>
  <div class="note-save-button-box">
    <button @click="searchSong({emotion, content})"
            class="note-save-button">
      <font-awesome-icon icon="pen-to-square"/> 노래 등록하기
    </button>
  </div>

</template>

<script>
import axios from 'axios'

export default {
  name: "SaveNote",
  data() {
    return {
      emotion: '',
      content: '',
    }
  },

  methods: {
    searchSong(payload) {
      axios.defaults.withCredentials = true;
      axios.post('http://localhost:8080/api/notes', payload)
      .then(response => {
        const noteId = response.data;
        this.$store.commit('saveNoteId', noteId);
        this.$router.push(`/song/search`);

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
.note-save-box {
  display: flex;
  flex-direction: column;
}

.note-input {
  text-align: right;
}

.label-font {
  font-family: 'Jeju Gothic', serif;
  font-size: 14px;
}

.note-save-button-box {
  text-align: right;
}

.note-save-button {
  background-color: royalblue;
  color: white;
  border: none;
  border-radius: 4px;
  font-weight: bolder;
  cursor: pointer;
  height: 45px;
  width: 20%;
  margin: 10px;
  text-align: center;
}

.note-save-button:hover {
  background-color: #ef4a4a;
}

</style>