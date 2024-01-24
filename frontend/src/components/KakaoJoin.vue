<template>
  <div></div>
</template>

<script>
import axios from 'axios'

export default {
  name: "KakaoJoin",
  data() {
    return {
      code: '',
    }
  },

  created() {
    this.code = this.$route.query.code;
    this.sendCode();
  },

  methods: {
    // code 를 backend 로 넘긴다.
    sendCode() {
      const apiServer = process.env.VUE_APP_API_SERVER;
      axios.defaults.withCredentials = true;
      axios.get(`${apiServer}/api/login/` + this.code)
      .then(response => {
        const userinfo = response.data;
        this.$store.commit('saveSignInUserInfo', userinfo);
        this.$router.push('/');

      }).catch((e) => {
        console.log(`exception message : ${e}`)
        console.log(`로그인 과정 중 오류`);
      })
    }
  }
}
</script>

<style scoped>

</style>