<template>
  <div class="loading-container">
    <div class="spinner"></div>
    <h2>결과를 분석 중입니다...</h2>
    <p>잠시만 기다려주세요</p>
  </div>
</template>

<style scoped>
.loading-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: #000;
  color: white;
}
.spinner {
  width: 50px;
  height: 50px;
  border: 4px solid #f3f3f3;
  border-top: 4px solid #fff;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}
@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}
</style>

<script setup>
// 필요한 로직이 여기에 들어갈 예정입니다.
</script>
