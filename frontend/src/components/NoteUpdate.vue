<template>
  <div class="note-update-box">
    <div class="note-input">
      <input v-model="emotion" type="text" class="form-control" placeholder="수정할 기분을 적어주세요.">
      <label class="label-font mt-1 mb-3">예) 행복, 슬픔 ...</label>
    </div>
    <textarea v-model="content" class="form-control mb-4" rows="20"
              placeholder="수정할 내용을 적어주세요."></textarea>
  </div>
  <div class="note-update-button-box">
    <button @click="updateNote({emotion, content})"
            class="note-update-button">
      <font-awesome-icon icon="pen-to-square"/>
      일기 수정하기
    </button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "NoteUpdate",
  data() {
    return {
      noteId: 0,
      emotion: '',
      content: '',
    }
  },

  created() {
    this.noteId = this.$route.params.noteId;
  },

  methods: {
    updateNote(payload) {
      axios.defaults.withCredentials = true;
      axios.put(`http://localhost:8080/api/notes/${this.noteId}`, payload)
      .then(() => {
        this.$router.push(`/note/detail/${this.noteId}`);
      }).catch(error => {
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
    }
  }
}
</script>

<style scoped>
.note-update-box {
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

.note-update-button-box {
  text-align: right;
}

.note-update-button {
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

.note-update-button:hover {
  background-color: #ef4a4a;
}
</style>