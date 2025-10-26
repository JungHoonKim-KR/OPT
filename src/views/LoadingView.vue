<template>
  <div class="loading-fullscreen">
    <img src="/src/assets/images/loading.gif" alt="로딩 중" />
  </div>
</template>

<style scoped>
.loading-fullscreen {
  position: fixed;
  inset: 0; /* top, right, bottom, left 전부 0 */
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #fff; /* 필요시 투명하게 rgba(255,255,255,0.9) */
  z-index: 9999;
}

img {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 비율 유지하며 꽉 채우기 */
}
</style>
