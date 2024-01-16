<template>
    <h3 class="login-name">Social Login</h3>
    <hr class="login-divider">
    <div class="kakao-login">
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
.login-name {
  margin-bottom: 10px;
  color: #333;
  text-align: center;
}

.login-divider {
  border: 1px solid #ccc;
  margin-bottom: 20px; /* 연한 줄 위아래 여백 조절 가능 */
}

.kakao-login {
  text-align: center;
}

</style>

