<template>
    <!-- HEADER -->
    <div class="header-body">
      <router-link class="royalblue-button-router-link" to="/">
        <font-awesome-icon :icon="['fas', 'quote-left']" /> Emotion Note <font-awesome-icon :icon="['fas', 'quote-right']" />
      </router-link>

      <div v-if="$store.state.sessionId === null">
        <button class="royalblue-button">
          <router-link class="royalblue-button-router-link" to="/login">
            <font-awesome-icon :icon="['fas', 'user-tie']" /> Login
          </router-link>
        </button>
      </div>

      <div v-else-if="$store.state.sessionId !== null">
        <button class="royalblue-button">
          <img class="header-login-image" :src="$store.state.profileImageUrl" alt="profile Url">{{ $store.state.nickname }} 님 반갑습니다 !
        </button>
        <button class="royalblue-button" @click="logout">로그아웃</button>
      </div>
    </div>

    <div class="main-body">
      <!-- SIDEBAR -->
      <div class="sidebar-body">
        <h4>메뉴</h4>
        <button class="white-button">
          <router-link class="white-button-router-link" to="/notes">
            <font-awesome-icon icon="clipboard" /> - 일기 목록
          </router-link>
        </button>
        <button class="white-button">
          <router-link class="white-button-router-link" to="/note/new">
            <font-awesome-icon icon="pen-to-square" /> - 일기 작성
          </router-link>
        </button>
      </div>

      <!-- MAIN MENU -->
      <div class="component-body">
        <router-view></router-view>
      </div>
    </div>

</template>

<script>
export default {
  name: "MainContainer",

  methods: {
    logout() {
      const clientId = this.$store.state.clientId;
      const logoutRedirectUri = this.$store.state.logoutRedirectUri;

      alert('다른 사용자의 접근을 막기 위해 공공장소에서는 카카오계정과 함께 로그아웃하여 개인정보를 보호하세요.');
      window.location.href = `https://kauth.kakao.com/oauth/logout?client_id=${clientId}&logout_redirect_uri=${logoutRedirectUri}`;
    }
  }
}
</script>

<style scoped>
@import '../assets/CommonStyle.css';

.header-body {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: royalblue;
  border-radius: 8px;
  padding: 10px;
  margin-bottom: 15px;
}

.header-login-image {
  width: 50px;
  height: 50px;
  border-radius: 60%;
  object-fit: cover;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);
  margin-right: 7px;
}

.sidebar-body {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  width: 170px;
  height: 200px;
  background-color: #ffffff;
  border-radius: 8px;
  border-right: 3px solid royalblue;
  padding: 10px;
}

.main-body {
  display: flex;
}

.component-body {
  flex: 1;
  border-radius: 8px;
  box-shadow: 0 2px 1px -1px rgba(0,0,0,.2), 0 1px 1px 0 rgba(0,0,0,.14), 0 1px 3px 0 rgba(0,0,0,.12);
  padding: 20px;
  margin-left: 20px;
}

</style>