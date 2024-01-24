<template>

  <div class="note-list-body" v-for="(note, noteId) in notes" :key="noteId" @click="readNote(note.id)">
    <p class="description-content" style="text-align: right; margin-bottom: 0">{{ `작성일 ${note.createAt}` }}</p>
    <p class="description-content" style="text-align: right; margin-bottom: 0">{{ `수정일 ${note.modifiedAt}` }}</p>

    <div class="note-list-body-button-box">
      <button class="royalblue-button" style="width: 12%; margin-right: 10px" @click="clickUpdate($event, note.id)">
        <font-awesome-icon icon="pen-to-square"/> 수정
      </button>
      <button class="royalblue-button" style="width: 12%" @click="clickDelete($event, note.id)">
        <font-awesome-icon icon="trash-can"/> 삭제
      </button>
    </div>

    <p class="description-content">{{ truncateNoteContent(note.content) }}</p>
    <hr />
  </div>

  <div class="pagination-body">
    <button class="pagination-button" @click="readPrevPage">
      <font-awesome-icon icon="fa-solid fa-arrow-left"/> 이전 페이지
    </button>
    <button class="pagination-button" @click="readNextPage"> 다음 페이지
      <font-awesome-icon icon="fa-solid fa-arrow-right"/>
    </button>
  </div>
</template>

<script>
import axios from 'axios';
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";

export default {
  name: "NoteList",
  components: {FontAwesomeIcon},
  data() {
    return {
      notes: [],
      currentPage: 1,
      defaultPageSize: 5,
    }
  },

  created() {
    this.readNotes();
  },

  methods: {
    readNotes() {
      axios.defaults.withCredentials = true;
      const apiServer = process.env.VUE_APP_API_SERVER;

      axios.get(`${apiServer}/api/notes`, {
        params: {
          page: this.currentPage,
          size: this.defaultPageSize,
        }
      })
      .then(response => {
        if (this.currentPage < 1) {
          alert('페이지가 존재하지 않습니다.');
          this.currentPage += 1;
        }

        if (response.data.notes.length === 0) {
          alert('페이지가 존재하지 않습니다.');
          this.currentPage -= 1;

        } else {
          this.notes = response.data.notes;
        }

      }).catch(error => {
        const errorStatus = error.response.status;

        if (errorStatus === 401) {
          localStorage.removeItem('vuex');
          alert('로그인이 필요합니다.');
          window.location.href = '/';
        }
      })
    },

    readNextPage() {
      this.currentPage += 1;
      this.readNotes()
    },

    readPrevPage() {
      this.currentPage -= 1;
      this.readNotes();
    },

    truncateNoteContent(noteContent) {
      if (noteContent.length > 50) {
        return noteContent.substring(0, 50) + '...';
      }

      return noteContent;
    },

    readNote(noteId) {
      this.$router.push(`/note/detail/${noteId}`);
    },

    clickUpdate(event, noteId) {
      event.stopPropagation();
      this.$router.push(`/note/update/${noteId}`);
    },

    clickDelete(event, noteId) {
      event.stopPropagation();
      const result = window.confirm('정말 삭제하시겠습니까 ?');

      if (result) {
        axios.defaults.withCredentials = true;
        const apiServer = process.env.VUE_APP_API_SERVER;

        axios.delete(`${apiServer}/api/notes/${noteId}`)
        .then(() => {
          window.location.href = '/notes';
        }).catch(error => {
          const errorStatus = error.response.status;

          if (errorStatus === 401) {
            localStorage.removeItem('vuex');
            alert('로그인이 필요합니다.');
            window.location.href = '/';

          } else if (errorStatus === 400) {
            const errorMessage = error.response.data.message;
            alert(errorMessage);
            this.$router.push('/');
          }
        })
      }
    },
  },
}
</script>

<style scoped>
@import '../assets/CommonStyle.css';

.note-list-body {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 10px;
  margin-bottom: 20px;
}

.note-list-body-button-box {
  display: flex;
  justify-content: end;
  margin-top: 10px;
  margin-bottom: 20px;
}
</style>