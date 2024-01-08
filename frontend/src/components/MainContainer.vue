<template>
  <header>
    <div class="home-name">
      <router-link to="/" class="header-router-link">
        <font-awesome-icon :icon="['fas', 'quote-left']" /> Emotion Note <font-awesome-icon :icon="['fas', 'quote-right']" />
      </router-link>
    </div>

    <div>
      <div v-if="$store.state.sessionId === null">
        <button class="nav-button">
          <router-link to="/login" class="header-router-link">
            <font-awesome-icon :icon="['fas', 'user-tie']" /> Login
          </router-link>
        </button>
      </div>
      <div v-else-if="$store.state.sessionId !== null">
        <button class="nav-button">
          <img class="profile-img" :src="$store.state.profileImageUrl" alt="profile Url">
          {{ $store.state.nickname }} 님 반갑습니다 !
        </button>
      </div>
    </div>
  </header>

  <main>
    <div class="sidebar">
      <h4 class="sidebar-name">메뉴</h4>
      <button class="sidebar-button">
        <font-awesome-icon icon="clipboard" /> - 일기 목록
      </button>
      <button class="sidebar-button">
        <router-link to="/note/new" class="sidebar-router-link">
          <font-awesome-icon icon="pen-to-square" /> - 일기 작성
        </router-link>
      </button>
    </div>

    <div class="section">
      <router-view></router-view>
    </div>
  </main>
</template>

<script>
export default {
  name: "MainContainer",
}
</script>

<style scoped>
header {
  background-color: royalblue;
  padding: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-radius: 8px;
  margin-bottom: 10px;
}

.header-router-link {
  text-decoration: none;
  color: white;
}

.home-name {
  color: white;
}

.nav-button {
  color: white;
  border-radius: 6px;
  border: none;
  background-color: royalblue;
}

.nav-button:hover {
  color: white;
  border-radius: 6px;
  background-color: #ef4a4a;
}

.profile-img {
  width: 50px;
  height: 50px;
  border-radius: 60%;
  object-fit: cover;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);
  margin-right: 7px;
}

main {
  display: flex;
}

.sidebar {
  background-color: #ffffff;
  padding: 10px;
  border-radius: 8px;
  width: 170px;
  display: flex;
  flex-direction: column;
}

.sidebar-name {
  text-align: center;
  margin-bottom: 20px;
  font-weight: bolder;
}

.sidebar-button {
  background-color: white;
  color: black;
  margin-bottom: 10px;
  border: none;
  border-radius: 4px;
  font-weight: bolder;
  cursor: pointer;
  height: 45px;
  width: 100%;
  text-align: center;
}

.sidebar-button:hover {
  background-color: #898989;
}

.sidebar-router-link {
  text-decoration: none;
  color: black;
}

.section {
  padding: 20px;
  margin-left: 20px;
  flex: 1;
  box-shadow: 0 2px 1px -1px rgba(0,0,0,.2), 0 1px 1px 0 rgba(0,0,0,.14), 0 1px 3px 0 rgba(0,0,0,.12);
  border-radius: 8px;
}

</style>