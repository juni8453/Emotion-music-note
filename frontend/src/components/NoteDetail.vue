<template>
  <div class="body">
    <p class="note-description">{{ `작성 일자 [${note.createAt}]` }}</p>
    <p class="note-description">{{ `수정 일자 [${note.modifiedAt}]` }}</p>
    <p class="note-description">{{ `이날의 감정 [${note.emotion}]` }}</p>
    <p class="note-content">일기 내용</p>
    <p class="note-content-font"><strong>{{ note.content }}</strong></p>

    <div class="body">
        <div class="note-content-box">
        <p class="note-content">오늘의 노래</p>
        <button class="note-content-button" style="margin: 5px" @click="clickUpdateSong(note.id)">
          <font-awesome-icon icon="pen-to-square" /> 수정
        </button>
      </div>
      <div class="song">
        <div class="song-image" :style="{ backgroundImage: `url(${note.songSavedInNoteResponse?.imageUrl})` }"></div>
        <div class="song-description">
          <p>가수</p>
          <p>{{ note.songSavedInNoteResponse?.artistName }}</p>
        </div>
        <div class="song-description">
          <p>제목</p>
          <p>{{ note.songSavedInNoteResponse?.title }}</p>
        </div>
        <div class="song-description">
          <p>앨범</p>
          <p>{{ note.songSavedInNoteResponse?.albumName }}</p>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import axios from 'axios';

export default {
  name: "NoteDetail",
  data() {
    return {
      note: {},
    }
  },

  created() {
    const noteId = this.$route.params.noteId;
    this.readNote(noteId);
  },

  methods: {
    readNote(noteId) {
      axios.defaults.withCredentials = true;
      axios.get(`http://localhost:8080/api/notes/${noteId}`)
      .then(response => {
        console.log(response.data);
        this.note = response.data;
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

    clickUpdateSong(noteId) {
      this.$store.commit('updateNoteId', noteId);
      this.$router.push('/song/update/search');
    }
  }
}
</script>

<style>
.body {
  height: auto;
  max-width: 100%;
  background-position: center;
  background-size: cover;
  margin: 20px;
}

.note-description {
  font-size: 14px;
  color: grey;
  text-align: right;
  margin-bottom: 10px;
}

.note-content-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: auto;
  margin-top: 50px;
  margin-bottom: 10px;
}

.note-content {
  font-size: 14px;
  color: grey;
}

.note-content-button {
  background-color: royalblue;
  color: white;
  border: black;
  border-radius: 4px;
  cursor: pointer;
  height: 25px;
  width: 10%;
  text-align: center;
}

.note-content-font {
  font-family: "Shree Devanagari 714", serif;
  font-size: 14px;
}

.song {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px;
  box-shadow: 0 0 1px rgba(0, 0, 0, 0.15), 0 15px 12px rgba(0, 0, 0, 0.15);
}

.song-image {
  width: 100px;
  height: 100px;
  margin-bottom: 0;
  background-size: 100%;
  border-radius: 10%;
  float: left;
}

.song-description {
  font-size: 14px;
  color: grey;
  text-align: center;
  margin-bottom: 0;
}

</style>