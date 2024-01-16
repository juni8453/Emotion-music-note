<template>
  <div></div>
</template>

<script>
export default {
  name: "KakaoOut",
  created() {
    localStorage.removeItem('vuex');
    window.location.href = '/';
  }
}
</script>

<style scoped>

</style>