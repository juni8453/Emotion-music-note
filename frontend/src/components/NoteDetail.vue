<template>
  <div class="body">
    <p class="note-description">{{ `작성 일자 [${note.createAt}]` }}</p>
    <p class="note-description">{{ `수정 일자 [${note.modifiedAt}]` }}</p>
    <p class="note-description">{{ `이날의 감정 [${note.emotion}]` }}</p>
    <p class="note-content">일기 내용</p>
    <p class="note-content-font"><strong>{{ note.content }}</strong></p>

    <div class="body">
      <p class="note-content">들었던 노래</p>
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
      });
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

.note-content {
  font-size: 14px;
  color: grey;
  margin-top: 50px;
  margin-bottom: 10px;
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
  box-shadow: 0 0 1px rgba(0,0,0,0.15), 0 15px 12px rgba(0,0,0,0.15);
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