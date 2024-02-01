<template>
  <!-- form-control, mb-1, mb-4 는 bootstrap -->
  <div class="note-update-body">
    <input class="form-control mb-1" v-model="emotion" type="text" placeholder="수정할 기분을 적어주세요.">
    <label class="note-update-label">예) 행복, 슬픔 등 자유롭게 감정을 적어주세요.</label>
    <textarea class="form-control mb-4" v-model="content" rows="20" placeholder="수정할 내용을 적어주세요."></textarea>
    <button class="royalblue-button" @click="updateNote({emotion, content})">
      <font-awesome-icon icon="pen-to-square" /> 수정 완료
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
      const apiServer = process.env.VUE_APP_API_SERVER;

      axios.put(`${apiServer}/api/notes/${this.noteId}`, payload)
      .then(() => {
        alert('일기 수정 완료!');
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
/* Desktop UI */
.note-update-body {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: end;
}

.note-update-label {
  font-family: 'Jeju Gothic', serif;
  font-size: 14px;
  margin-bottom: 20px;
}

/* Mobile UI */
@media (max-width: 576px) {
  .note-update-label {
    font-size: 12px;
    margin-bottom: 18px;
  }
}

</style>