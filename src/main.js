// 1. 전역 CSS 파일을 가져옵니다. (앱 전체의 기본 스타일)
import "./assets/main.css";

// 2. Vue와 핵심 라이브러리(Pinia, Router) 기능을 가져옵니다.
import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
import router from "./router";

// 3. Vue 앱 인스턴스(앱의 본체)를 생성합니다. App.vue를 최상위 컴포넌트로 사용합니다.
const app = createApp(App);

// 4. Pinia(상태 관리)와 Router(페이지 이동)를 앱에 연결(등록)합니다.
// .use()는 Vue 앱에 새로운 기능을 추가하는 명령어입니다.
app.use(createPinia());
app.use(router);

// 5. 모든 설정이 끝난 앱을 public/index.html 파일 안에 있는
// <div id="app"></div> 요소에 연결(마운트)합니다.
// 이 줄이 실행되는 순간, 우리 눈에 웹페이지가 보이게 됩니다.
app.mount("#app");
