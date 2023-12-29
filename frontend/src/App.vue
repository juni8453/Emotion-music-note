<template>
  <div class="container-fluid">
    <MainContainer />
  </div>
</template>

<script>
import MainContainer from "@/components/MainContainer";
export default {
  name: 'App',
  components: {
    MainContainer
  }
}
</script>

<style>
html, body {
  height: 100%;
  margin: 10px;
}
</style>
