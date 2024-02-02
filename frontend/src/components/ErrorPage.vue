<template>
  <div class="error-body">
    <p class="description-title">서버 통신 오류</p>
    <p class="description-content">서버와의 연결이 끊어졌습니다.</p>
    <p class="description-content">지속 시 tany.byeongjun.jeon@gmail.com 로 문의해주세요 🙇‍</p>
  </div>
</template>

<script>
export default {
  name: "ErrorPage"
}
</script>

<style scoped>
.error-body {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>