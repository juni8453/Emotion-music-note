<template>
  <div>로그인 중 입니다.</div>
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
      axios.get('http://localhost:8080/login/' + this.code)
        .then(response => {
          console.log(response.data);
          this.$store.commit('saveSignInUserInfo', response.data);
          this.$router.push('/');

        }).catch((e) => {
          console.log(`exception message : ${e}`)
      })
    }
  }
}
</script>

<style scoped>

</style>