<template>
  <div class="login-body">
    <p class="description-title">Social Login</p>
    <p class="description-content">로그인 후 일기를 작성해보세요.</p>
    <p class="description-content">현재는 카카오 로그인만을 지원합니다.</p>
    <p class="description-content">곧 다른 플랫폼 로그인을 업데이트 할 예정입니다 🙇‍</p>
    <img :src="require('../assets/kakao_login_medium_narrow.png' )" @click="kakaoLogin" alt="kakao login"/>
  </div>
</template>

<script>
export default {
  name: "UserLogin",
  data() {
    return {
      code: '',
    }
  },

  methods: {
    kakaoLogin() {
      const clientId = this.$store.state.clientId;
      const redirectUri = this.$store.state.redirectUri;

      window.location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${clientId}&redirect_uri=${redirectUri}&response_type=code`;
    }
  },

}
</script>

<style scoped>
@import '../assets/CommonStyle.css';

.login-body {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}

</style>

