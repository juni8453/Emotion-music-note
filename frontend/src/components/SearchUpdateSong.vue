<template>
  <div class="search-song-body">
    <p class="description-content">수정할 노래를 검색 후 등록하세요.</p>
    <p class="description-content">Spotify Search API 를 사용합니다.</p>
    <p class="description-content">원하는 노래가 없거나 결과가 만족스럽지 않다면, 영어로 검색해보세요 🙇‍</p>
    <div class="search-song-input-body">
      <input class="search-song-input-modal" v-model="searchKeyword" type="text" placeholder="검색어를 입력하세요.">
      <button class="search-song-button" @click="selectSong">Search</button>
    </div>
  </div>

  <div class="search-song-result-attributes" v-if="this.songs.length !== 0">
    <p class="description-content">아티스트</p>
    <p class="description-content">제목</p>
    <p class="description-content">앨범명</p>
    <p class="description-content">앨범 이미지</p>
  </div>

  <div class="search-song-result-body" style="margin-bottom: 10px" v-for="(song, i) in songs" :key="i" @click="updateSong(song)">
    <p class="search-song-result-artisName description-content">{{ song.artistName }}</p>
    <p class="search-song-result-content description-content">{{ song.title }}</p>
    <p class="search-song-result-albumName description-content">{{ song.albumName }}</p>
    <div class="song-image" :style="{ backgroundImage: `url(${song.imageUrl})` }"></div>
  </div>

  <div class="pagination-body" v-if="this.songs.length !== 0">
    <button class="pagination-button" @click="readPrevPage">
      <font-awesome-icon icon="fa-solid fa-arrow-left"/> 이전 페이지
    </button>
    <button class="pagination-button" @click="readNextPage">
      <font-awesome-icon icon="fa-solid fa-arrow-right"/> 다음 페이지
    </button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "SearchUpdateSong",
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
      const apiServer = process.env.VUE_APP_API_SERVER;

      axios.get(`${apiServer}/api/songs`, {
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

    updateSong(payload) {
      const result = window.confirm('해당 노래로 수정하시겠습니까 ?')

      if (result) {
        const updateNoteId = this.$store.state.updateNoteId;
        const apiServer = process.env.VUE_APP_API_SERVER;

        axios.defaults.withCredentials = true;
        axios.put(`${apiServer}/api/songs/${updateNoteId}`, payload)
        .then(() => {
          this.$router.push(`/note/detail/${updateNoteId}`);
          this.$store.state.updateNoteId = 0;
          alert('노래 수정 완료!');
        }).catch(error => {
          const errorStatus = error.response.status;
          if (errorStatus === 400) {
            const errorMessage = error.response.data.message;
            alert(errorMessage);
            this.$router.push('/');
          }
        })
      }
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
@import '../assets/CommonStyle.css';

/* Desktop UI */
.search-song-body {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.search-song-input-modal {
  border: 1px solid #ddd;
  border-radius: 20px;
  font-size: 16px;
  width: 300px;
  padding: 12px;
  margin-right: 10px;
  outline: none;
}

.search-song-button {
  color: #ffffff;
  background-color: #4CAF50;
  border: none;
  border-radius: 20px;
  font-size: 16px;
  padding: 12px 24px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.search-song-button:hover {
  background-color: #45a049;
}

.search-song-result-attributes {
  display: grid;
  justify-items: center;
  grid-template-columns: repeat(4, 1fr); /* 4개의 열, 각 열의 너비는 동일 */
  gap: 20px; /* 열 간의 간격 */
  padding: 5px;
}

.search-song-result-body {
  display: grid;
  justify-items: flex-start;
  align-items: center;
  grid-template-columns: repeat(4, 1fr); /* 4개의 열, 각 열의 너비는 동일 */
  gap: 20px; /* 열 간의 간격 */
  padding: 5px;
}

.search-song-result-body:hover {
  background-color: #ec9d9d;
  border-radius: 8px;
}

/* Mobile UI */
@media (max-width: 576px) {
  .search-song-input-body {
    display: flex;
  }

  .search-song-input-modal {
    font-size: 14px;
    width: 170px;
  }

  .search-song-button {
    font-size: 14px;
  }

  .search-song-result-attributes {
    display: none;
  }

  .search-song-result-body {
    grid-template-columns: repeat(3, 1fr);
    gap: 10px;
    padding: 5px;
  }

  .search-song-result-albumName {
    display: none;
  }

  .song-image {
    width: 90px;
    height: 90px;
  }
}

</style>