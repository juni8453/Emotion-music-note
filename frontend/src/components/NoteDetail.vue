<template>
  <div class="note-detail-body">
    <p class="description-content" style="text-align: right">{{ `작성 일자 [${note.createAt}]` }}</p>
    <p class="description-content" style="text-align: right">{{ `수정 일자 [${note.modifiedAt}]` }}</p>
    <p class="description-content" style="text-align: right">{{ `이날의 감정 [${note.emotion}]` }}</p>
    <p class="description-content">일기 내용</p>
    <p class="note-detail-content-font"><strong>{{ note.content }}</strong></p>
  </div>

  <p class="description-content" style="margin-bottom: 20px">오늘의 노래</p>

  <div class="note-detail-song-body" v-for="(song, songId) in songs" :key="songId" @click="clickUpdateSong(note.id)">
    <div class="song-image" :style="{ backgroundImage: `url(${song.imageUrl})` }"></div>
    <div class="note-detail-song-attributes">
      <p class="description-content">가수</p>
      <p class="description-content">{{ song.artistName }}</p>
    </div>
    <div class="note-detail-song-attributes">
      <p class="description-content">제목</p>
      <p class="description-content">{{ song.title }}</p>
    </div>
    <div class="note-detail-song-attributes">
      <p class="description-content">앨범</p>
      <p class="description-content">{{ song.albumName }}</p>
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
      songs: [],
    }
  },

  created() {
    const noteId = this.$route.params.noteId;
    this.readNote(noteId);
  },

  methods: {
    readNote(noteId) {
      axios.defaults.withCredentials = true;
      const apiServer = process.env.VUE_APP_API_SERVER;

      axios.get(`${apiServer}/api/notes/${noteId}`)
      .then(response => {
        this.note = response.data;
        this.songs = response.data.songSavedInNoteResponses;

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
      const result = window.confirm('노래를 수정하시겠습니까 ?')
      if (result) {
        this.$store.commit('updateNoteId', noteId);
        this.$router.push('/song/update/search');
      }
    }
  }
}
</script>

<style>
@import '../assets/CommonStyle.css';

.note-detail-body {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin-bottom: 20px;
}

.note-detail-content-font {
  font-family: "Shree Devanagari 714", serif;
  font-size: 14px;
}

.note-detail-song-body {
  display: flex;
  justify-content: space-around;
  align-items: center;
  border-radius: 6px;
  box-shadow: 0 0 1px rgba(0, 0, 0, 0.15), 0 15px 12px rgba(0, 0, 0, 0.15);
  cursor: pointer;
}

.note-detail-song-attributes {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}
</style>