<script setup>
// vue-router에서 RouterView를 가져옵니다.
// RouterView는 현재 URL 경로에 맞는 컴포넌트를 렌더링하는 역할을 합니다.
import { RouterView } from "vue-router";
</script>

<template>
  <!-- 
    이곳이 바로 페이지가 교체되어 보이는 마법의 공간입니다.
    / 주소로 접속하면 HomeView가, /quiz 주소로 접속하면 QuizView가 이곳에 렌더링됩니다.
  -->
  <RouterView />
</template>


<style scoped>
/* scoped 키워드는 이 스타일이 App.vue 컴포넌트 내부에만 적용되도록 합니다.
  하지만 App.vue는 모든 페이지를 감싸고 있으므로,
  여기에 작성하는 스타일은 사실상 전역 스타일처럼 작동하게 할 수 있습니다.
  프로젝트 전반에 걸친 기본 배경색이나 레이아웃을 설정하기 좋은 곳입니다.
*/
#app {
  /* 프로젝트의 최대 너비를 제한하고 중앙에 배치하여
    큰 화면에서도 콘텐츠가 너무 넓게 퍼지지 않도록 합니다.
  */
  /* max-width: 1280px; */
  margin: 0 auto;
  font-weight: normal;
}

/* 모든 페이지 전환에 부드러운 애니메이션 효과를 주기 위한 설정입니다.
*/
.v-enter-active,
.v-leave-active {
  transition: opacity 0.3s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}
</style>
