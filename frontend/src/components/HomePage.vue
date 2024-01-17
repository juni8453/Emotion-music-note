<template>
  <div class="home-body">
    <p class="description-title">
      <strong>노래를 담아내는 갬성 일기장</strong>
    </p>
    <p class="description-content">
      음악이 감정을 표현하는 가장 아름다운 수단 중 하나입니다.
      음악 일기는 하루의 감동과 감정을 일기로 기록하고, 그 때 들었던 노래로 감성을 더욱 풍성하게 표현할 수 있는 특별한 일기장 앱입니다.
    </p>
    <p class="description-content">
      <strong>어떤 순간이든, 언제든지 소리로 기억을 남겨보세요. "음악 일기"와 함께라면 당신의 감성이 더욱 특별한 순간으로 기억될 것입니다.</strong>
    </p>
    <button class="royalblue-button" v-if="$store.state.sessionId === null">
      <router-link class="royalblue-button-router-link" to="/login">
        <font-awesome-icon :icon="['fas', 'user-tie']" /> Login
      </router-link>
    </button>
  </div>
</template>

<script>
export default {
  name: "HomePage"
}
</script>

<style scoped>
@import '../assets/CommonStyle.css';

.home-body {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}

</style>