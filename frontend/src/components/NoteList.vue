<template>
  <button class="royalblue-button" style="margin-bottom: 10px;" @click="filterSwitch">
    <font-awesome-icon icon="calendar-days"/>
  </button>
  <div v-if="dateFilterSwitch">
    <label class="description-content">Start Date</label>
    <datepicker class="note-list-date-filter-input"
                style="border-radius: 8px; border: 1px solid #ccc; margin-bottom: 10px;"
                v-model="startDate">
    </datepicker>
    <label class="description-content">End Date</label>
    <datepicker class="note-list-date-filter-input"
                style="border-radius: 8px; border: 1px solid #ccc; margin-bottom: 10px;"
                v-model="endDate">
    </datepicker>
  </div>
  <button class="royalblue-button" v-if="dateFilterSwitch" @click="dateFilterSelect">
    <font-awesome-icon icon="calendar-check"/>
  </button>

  <div class="note-list-body" v-for="(note, noteId) in notes" :key="noteId"
       @click="readNote(note.id)">
    <p class="description-content" style="text-align: right; margin-bottom: 0">
      {{ `작성일 ${note.createAt}` }}</p>
    <p class="description-content" style="text-align: right; margin-bottom: 0">
      {{ `수정일 ${note.modifiedAt}` }}</p>

    <div class="note-list-body-button-box">
      <button class="royalblue-button" style="width: 12%; margin-right: 10px"
              @click="clickUpdate($event, note.id)">
        <font-awesome-icon icon="pen-to-square"/>
      </button>
      <button class="royalblue-button" style="width: 12%" @click="clickDelete($event, note.id)">
        <font-awesome-icon icon="trash-can"/>
      </button>
    </div>

    <p class="description-content">{{ truncateNoteContent(note.content) }}</p>
    <hr/>
  </div>

  <div class="pagination-body">
    <button class="pagination-button" @click="readPrevPage">
      <font-awesome-icon icon="fa-solid fa-arrow-left"/>
      이전 페이지
    </button>
    <button class="pagination-button" @click="readNextPage"> 다음 페이지
      <font-awesome-icon icon="fa-solid fa-arrow-right"/>
    </button>
  </div>
</template>

<script>
import axios from 'axios';
import FontAwesomeIcon from "@fortawesome/vue-fontawesome";
import Datepicker from 'vue3-datepicker';

export default {
  name: "NoteList",
  components: {
    FontAwesomeIcon,
    Datepicker,
  },

  data() {
    return {
      notes: [],
      currentPage: 1,
      defaultPageSize: 5,
      dateFilterSwitch: false,
      dataFilterSelecting: false,
      startDate: null,
      endDate: null,
    }
  },

  created() {
    this.readData();
  },

  methods: {
    readData() {
      const params = this.loadParams();
      const uri = this.loadUri();

      this.fetchData(params, uri)
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
      })
      .catch(error => {
        const errorStatus = error.response.data.code;

        if (errorStatus === 401) {
          localStorage.removeItem('vuex');
          alert(error.response.data.message);
          window.location.href = '/';
        }
      })
    },

    loadParams() {
      return {
        page: this.currentPage,
        size: this.defaultPageSize,
        startDate: this.startDate ? this.startDate.toISOString().split('T')[0] : null,
        endDate: this.endDate ? this.endDate.toISOString().split('T')[0] : null,
      };
    },

    loadUri() {
      // 날짜 필터링을 적용하고 조회했을때 /api/notes/date 엔드 포인트 사용
      return this.dataFilterSelecting ? '/api/notes/date' : '/api/notes';
    },

    fetchData(params, uri) {
      axios.defaults.withCredentials = true;
      const apiServer = process.env.VUE_APP_API_SERVER;

      return axios.get(`${apiServer}${uri}`, {params});
    },

    filterSwitch() {
      this.dateFilterSwitch = !this.dateFilterSwitch;
    },

    dateFilterSelect() {
      this.dataFilterSelecting = true;
      this.readData();
    },

    readNextPage() {
      this.currentPage += 1;
      this.readData();
    },

    readPrevPage() {
      this.currentPage -= 1;
      this.readData();
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
          const errorStatus = error.response.data.code;

          // Interceptor preHandler()
          if (errorStatus === 401) {
            localStorage.removeItem('vuex');
            alert(error.response.data.message);
            window.location.href = '/';

            // NotFoundNoteException
          } else if (errorStatus === 400) {
            const errorMessage = error.response.data.message;
            alert(errorMessage);
            this.$router.push('/');
          }
        })
      }
    },

    readNote(noteId) {
      this.$router.push(`/note/detail/${noteId}`);
    },

    truncateNoteContent(noteContent) {
      if (noteContent.length > 50) {
        return noteContent.substring(0, 50) + '...';
      }

      return noteContent;
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
  cursor: pointer;
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