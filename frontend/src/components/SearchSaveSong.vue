<template>
  <div class="body">
    <h5 class="search-title">노래를 검색 후 등록하세요.</h5>
    <p class="search-description">Spotify Search API 를 사용합니다.</p>
    <p class="search-description">원하는 노래가 없거나 결과가 만족스럽지 않다면, 영어로 검색해보세요 🙇‍</p>
    <div style="margin-top: 30px">
      <input v-model="searchKeyword" type="text" class="search-input" placeholder="검색어를 입력하세요.">
      <button @click="selectSong" class="search-button">Search</button>
    </div>
  </div>

  <div class="body" v-if="this.songs.length !== 0">
    <div class="search-attributes">
      <p class="search-description">아티스트</p>
      <p class="search-description">제목</p>
      <p class="search-description">앨범명</p>
      <p class="search-description">앨범 이미지</p>
    </div>
    <div @click="saveSong(song)" class="search-result" v-for="(song, i) in songs" :key="i">
      <p class="search-description">{{ song.artistName }}</p>
      <p class="search-description">{{ song.title }}</p>
      <p class="search-description"> {{ song.albumName }}</p>
      <div class="song-image" :style="{ backgroundImage: `url(${song.imageUrl})` }"></div>
    </div>

    <div class="pagination-body">
      <button class="pagination-button" @click="readPrevPage">
        <font-awesome-icon icon="fa-solid fa-arrow-left"/>
      </button>
      <button class="pagination-button" @click="readNextPage">
        <font-awesome-icon icon="fa-solid fa-arrow-right"/>
      </button>
    </div>
  </div>

</template>

<script>
import axios from 'axios';

export default {
  name: "SearchSaveSong",
  data() {
    return {
      songs: [],
      searchKeyword: '',
      currentPage: 1,
      defaultPageSize: 5,
    }
  },

  methods: {
    selectSong() {
      axios.defaults.withCredentials = true;
      axios.get('http://localhost:8080/api/songs', {
        params: {
          keyword: this.searchKeyword,
          page: this.currentPage,
          size: this.defaultPageSize,
        }
      })
      .then(response => {
        if (this.currentPage < 1) {
          alert('페이지가 존재하지 않습니다.');
          this.currentPage += 1;
        }

        if (response.data.responses.length === 0) {
          alert('페이지가 존재하지 않습니다.');
          this.currentPage -= 1;

        } else {
          this.songs = response.data.responses;
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
      this.selectSong();
    },

    readPrevPage() {
      this.currentPage -= 1;
      this.selectSong();
    },

    saveSong(payload) {
      const saveNoteId = this.$store.state.saveNoteId;

      axios.defaults.withCredentials = true;
      axios.post(`http://localhost:8080/api/songs/${saveNoteId}`, payload)
      .then(response => {
        console.log(response.data);
        alert(`해당 노래를 등록하시겠습니까 ?`);
        this.$router.push(`/note/detail/${saveNoteId}`);
        this.$store.state.saveNoteId = 0;

      }).catch(error => {
        const errorStatus = error.response.status;
        if (errorStatus === 400) {
          const errorMessage = error.response.data.message;
          alert(errorMessage);
          this.$router.push('/');
        }
      })
    }
  },

  watch: {
    searchKeyword(newKeyword, oldKeyword) {
      if (newKeyword !== oldKeyword) {
        this.currentPage = 1;
      }
    }
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

.search-title {
  font-size: 15px;
  color: grey;
  margin-bottom: 20px;
}

.search-description {
  font-size: 14px;
  color: grey;
  text-align: left;
  margin-bottom: 10px;
}

.search-input {
  width: 300px;
  padding: 12px;
  font-size: 16px;
  margin-right: 10px;
  border: 1px solid #ddd;
  border-radius: 20px;
  outline: none;
}

.search-button {
  padding: 12px 24px;
  font-size: 16px;
  background-color: #4CAF50;
  color: #ffffff;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.search-button:hover {
  background-color: #45a049;
}

.search-attributes {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px;
}

.search-result {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px;
}

.search-result:hover {
  background-color: #ec9d9d;
  border-radius: 8px;
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