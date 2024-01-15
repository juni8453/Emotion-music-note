<template>
  <div class="body" v-for="(note, noteId) in notes" :key="noteId">
    <div class="card">
      <div class="card-body" @click="readNote(note.id)">
        <h5 class="card-header-description">{{ `작성일 ${note.createAt}` }}</h5>
        <h5 class="card-header-description">{{ `수정일 ${note.modifiedAt}` }}</h5>
        <div class="card-button-box">
          <button class="card-button" style="margin: 5px" @click="clickUpdate($event, note.id)">
            <!-- 이벤트 버블링 방지 -->
            <font-awesome-icon icon="pen-to-square"/>
            수정
          </button>
          <button class="card-button" style="margin: 5px" @click="clickDelete($event, note.id)">
            <font-awesome-icon icon="trash-can"/>
            삭제
          </button>
        </div>
        <p class="card-text">{{ truncateNoteContent(note.content) }}</p>
      </div>
    </div>
  </div>
  <div class="pagination-body">
    <button class="pagination-button" @click="readPrevPage">
      <font-awesome-icon icon="fa-solid fa-arrow-left"/>
      이전 페이지
    </button>
    <button class="pagination-button" @click="readNextPage">
      다음 페이지
      <font-awesome-icon icon="fa-solid fa-arrow-right"/>
    </button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "NoteList",
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
      axios.get('http://localhost:8080/api/notes', {
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

      axios.defaults.withCredentials = true;
      axios.delete(`http://localhost:8080/api/notes/${noteId}`)
      .then(() => {
        alert('정말 삭제하시겠습니까?');
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
    },
  },
}
</script>

<style scoped>
.body {
  height: auto;
  max-width: 100%;
  background-position: center;
  background-size: cover;
  margin: 20px;
}

.card-header-description {
  font-size: 14px;
  color: grey;
  text-align: right;
  margin-bottom: 10px;
}

.card-button-box {
  display: flex;
  justify-content: flex-end;
}

.card-button {
  background-color: royalblue;
  color: white;
  border: black;
  border-radius: 4px;
  cursor: pointer;
  height: 25px;
  width: 10%;
  text-align: center;
}

.card-button:hover {
  background-color: #ef4a4a;
}

.pagination-body {
  display: flex;
  justify-content: center;
}

.pagination-button {
  background-color: royalblue;
  color: white;
  border: none;
  margin: 5px;
  border-radius: 4px;
}

.pagination-button:hover {
  background-color: #ef4a4a;
}
</style>