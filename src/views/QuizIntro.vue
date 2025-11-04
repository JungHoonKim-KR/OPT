<template>
  <div class="container">
    <!-- 모든 콘텐츠를 감싸는 비율 유지 래퍼 -->
    <div class="aspect-ratio-wrapper">
      <div class="intro-container">
        <!-- 첫 번째 화면 패널 -->
        <section class="panel first-panel">
          <header class="header">
            <div class="inner-wrapper">
              <div class="header-bars-wrapper">
                <img
                  v-for="n in 4"
                  :key="n"
                  :src="backgroundBarImageWhite"
                  alt="background bar"
                  class="header-bar"
                />
              </div>
            </div>
          </header>
          <main class="content-wrapper">
            <img
              :src="titleImage"
              alt="Online Persona Type"
              class="title-image"
            />
            <div class="description">
              <!-- 화면 높이에 따라 텍스트 조건부 렌더링 -->
              <template v-if="isSmallScreen">
                <p>
                  온라인 속 나의 다양한 페르소나를 탐색하고,<br />
                  디지털 자아의 정체성을 기록하는 프로젝트입니다.
                </p>
              </template>
              <template v-else>
                <p>
                  우리는 온라인 속에서 무수히 많은 '나'로 살아갑니다.<br />
                  이 프로젝트는 언어 습관, 앱 사용 패턴, 콘텐츠 취향, 검색 행동
                  등<br />
                  온라인 속에서 나타나는 행동 데이터를 기반으로 16가지의<br />온라인
                  페르소나 유형을 정의합니다.
                </p>
                <p>
                  OPT (Online Persona Type) 테스트를 통해 관람자는<br />자신의
                  디지털 성향을 진단받고, 그 결과에 따라 그래픽 캐릭터와<br />
                  상징 오브제로 구현된 디지털 자아와 마주하게 됩니다. <br />이
                  프로젝트는 온라인 세계 속의 '나'를 관찰하고 이해하는 실험이자,
                  <br />지금 이 시대의 자아를 기록하는 아카이브입니다. OPT는
                  온라인 <br />자아의 정체성을 탐색하고, 스스로를 들여다볼 수
                  있는 도구가 됩니다.
                </p>
              </template>
            </div>
          </main>
          <div class="footer-bars-wrapper">
            <img
              :src="backgroundBarImageBlack"
              alt="Online Persona Type"
              class="footer-image"
            />
          </div>
        </section>

        <!-- 두 번째 화면 패널 (패턴 & 대화 애니메이션 화면) -->
        <section class="panel pattern-panel" ref="patternPanelRef">
          <img src="/src/assets/images/intro-background.gif" alt="로딩 중" />

          <!-- <div class="dialogue-wrapper">
            <div
              v-for="(message, index) in dialogueMessages"
              :key="index"
              class="dialogue-item"
            >
              <div class="avatar-dot"></div>
              <div class="message-bubble">
                <p>{{ message.text }}</p>
              </div>
            </div>
            <div class="loading-dots">
              <span></span>
              <span></span>
              <span></span>
            </div>
          </div> -->
        </section>

        <!-- 세 번째 화면 패널 (선택 화면) -->
        <section class="panel selection-panel">
          <div class="selection-content">
            <div class="selection-header">
              <p class="main-text">성별, 연령대를 선택해주세요!</p>
              <p class="sub-text">
                귀하의 정보는 통계 목적으로만 활용되며,<br></br>익명으로 처리됩니다.
              </p>
            </div>

            <div class="option-group sex-group">
              <button
                @click="userSelectionStore.setGender('male')"
                :class="{ active: userSelectionStore.gender === 'male' }"
                class="option-button"
              >
                남자
              </button>
              <button
                @click="userSelectionStore.setGender('female')"
                :class="{ active: userSelectionStore.gender === 'female' }"
                class="option-button"
              >
                여자
              </button>
            </div>

            <div class="option-group age-group">
              <button
                @click="userSelectionStore.setAge('10s')"
                :class="{ active: userSelectionStore.age === '10s' }"
                class="option-button option-button-age"
              >
                10대
              </button>
              <button
                @click="userSelectionStore.setAge('20s')"
                :class="{ active: userSelectionStore.age === '20s' }"
                class="option-button option-button-age"
              >
                20대
              </button>
              <button
                @click="userSelectionStore.setAge('30s')"
                :class="{ active: userSelectionStore.age === '30s' }"
                class="option-button option-button-age"
              >
                30대
              </button>
              <button
                @click="userSelectionStore.setAge('40s')"
                :class="{ active: userSelectionStore.age === '40s' }"
                class="option-button option-button-age"
              >
                40대
              </button>
              <button
                @click="userSelectionStore.setAge('50s')"
                :class="{ active: userSelectionStore.age === '50s' }"
                class="option-button option-button-age"
              >
                50대
              </button>
              <button
                @click="userSelectionStore.setAge('etc')"
                :class="{ active: userSelectionStore.age === 'etc' }"
                class="option-button option-button-age"
              >
                기타
              </button>
            </div>

            
          </div>
          <button
              @click="goToQuiz"
              class="start-button"
              :disabled="!userSelectionStore.gender || !userSelectionStore.age"
            >
              <img
              :src="startButtonImage"
              alt="설문조사 시작"
              class="start-button-img"
            />
            </button>
        </section>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, computed } from "vue";
import { useRouter } from "vue-router";
// Pinia 스토어 임포트
import { useUserSelectionStore } from "@/stores/userSelection";
// --- 이미지 임포트 ---
import backgroundBarImageWhite from "@/assets/images/backgroundBar_white.png";
import titleImage from "@/assets/images/title_online_persona_type.png";
import backgroundBarImageBlack from "@/assets/images/footer_plus_black.png";
import startButtonImage from "@/assets/images/start-button.png";

const router = useRouter();
// Pinia 스토어 인스턴스 생성
const userSelectionStore = useUserSelectionStore();

// 로컬 selectedGender, selectedAge ref 제거

// 화면 크기 반응형을 위한 ref
const screenHeight = ref(window.innerHeight);
const screenWidth = ref(window.innerWidth);

// 작은 화면 여부 (텍스트 조건부 렌더링 기준)
const isSmallScreen = computed(() => {
  return (
    screenHeight.value < 700 || screenWidth.value / screenHeight.value > 1.2
  );
});

// 두 번째 패널 대화 데이터
const dialogueMessages = ref([
  { id: 1, text: "당신은 하루에 몇 시간을 온라인 공간에서 보내나요?" },
  { id: 2, text: "온라인 공간에서의 당신은 어떤 사람인가요?" },
  {
    id: 3,
    text: "OPT는 온라인 속에서의 나를 파악하고, 새로운 시선으로 나를 바라보게하는 실험입니다.",
  },
]);

// 퀴즈 페이지로 이동하는 함수 (쿼리 파라미터 제거)
function goToQuiz() {
  if (userSelectionStore.gender && userSelectionStore.age) {
    router.push("/quiz");
  } else {
    console.warn("성별과 연령대를 모두 선택해주세요.");
  }
}

// 스크롤 애니메이션 로직 단순화
const patternPanelRef = ref(null);
let panelObserver;

const updateScreenSize = () => {
  screenHeight.value = window.innerHeight;
  screenWidth.value = window.innerWidth;
};

onMounted(() => {
  panelObserver = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          entry.target.classList.add("is-active");
          // 한 번 애니메이션 후 unobserve (필요에 따라 주석 처리 가능)
          // panelObserver.unobserve(entry.target);
        } else {
          // 화면 밖으로 나가면 다시 비활성화 (선택 사항)
          // entry.target.classList.remove("is-active");
        }
      });
    },
    { threshold: 0.5 } // 50% 이상 보일 때
  );

  if (patternPanelRef.value) {
    panelObserver.observe(patternPanelRef.value);
  }

  // 화면 크기 변경 감지
  window.addEventListener("resize", updateScreenSize);
  updateScreenSize(); // 초기 값 설정
});

onUnmounted(() => {
  if (panelObserver) panelObserver.disconnect();
  window.removeEventListener("resize", updateScreenSize);
});
</script>

<style scoped>
/* 레터박스 배경 */
.container {
  min-height: 100vh;
  width: 100vw;
  background: #000;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

/* 비율 유지 래퍼 */
.aspect-ratio-wrapper {
  position: relative;
  width: calc(100vh * 9 / 16);
  height: 100vh;
  max-width: 100vw;
  max-height: calc(100vw * 16 / 9);
  display: flex; /* 내부 intro-container를 채우기 위함 */
  flex-direction: column;
  box-sizing: border-box;
}

/* 스크롤 스냅 컨테이너 */
.intro-container {
  flex-grow: 1; /* 부모 공간을 꽉 채움 */
  width: 100%;
  overflow-y: scroll;
  scroll-snap-type: y mandatory;
  background-color: #e9f7ff;
}

/* 패널 공통 */
.panel {
  flex-shrink: 0; /* 패널 크기 고정 */
  height: 100%; /* 부모(intro-container) 높이 100% */
  width: 100%; /* 부모 너비 100% */
  scroll-snap-align: start;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  position: relative;
  /* vmin과 vh를 혼합하여 반응형 패딩 설정 */
  padding: clamp(1rem, 3vmin, 3rem) clamp(1rem, 5vmin, 5rem);
}

/* --- 첫 번째 패널 --- */
.first-panel {
  background-color: transparent;
  padding: 0; /* 첫 패널은 헤더와 컨텐츠가 자체 패딩을 가짐 */
}
.header {
  height: 28%;
  box-sizing: border-box;
  flex-shrink: 0;
  width: 100%;
}
.inner-wrapper {
  width: 100%;
  margin: 0 auto;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-image: url("../assets/images/homebackground.png");
  background-size: cover;
}
.header .inner-wrapper {
  justify-content: space-between;
}
.header-bars-wrapper {
  padding: 150px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 100%;
}
.header-bar {
  height: auto;
  width: 80%;
}
.header-bar:nth-child(1) {
  opacity: 0.7;
}
.header-bar:nth-child(2) {
  opacity: 0.5;
}
.header-bar:nth-child(3) {
  opacity: 0.3;
}
.header-bar:nth-child(4) {
  opacity: 0.2;
}

.content-wrapper {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  gap: clamp(1.5rem, 4vmin, 4rem); /* vmin 사용 */
  width: 100%;
  /* padding: clamp(1rem, 3vmin, 3rem) clamp(1rem, 5vmin, 5rem); */
  padding: 10rem;
  box-sizing: border-box;
  overflow: hidden; /* 내부 스크롤 방지 */
}
.title-image {
  height: 35%;
}
.start-button-img{

}
.description {
  font-family: Pretendard;
  font-weight: 500;
  font-style: Medium;
  font-size: 45px;
  line-height: 180%;
  letter-spacing: 0%;

  display: flex;
  flex-direction: column;
  gap: clamp(0.8rem, 2vmin, 2rem); /* vmin 사용 */
  line-height: 1.6;
  text-align: left;
  width: 100%;
  /* font-size: clamp(10px, 1.8vmin, 18px);  */
}
.footer-bars-wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding: clamp(1rem, 3vmin, 3rem) clamp(1rem, 5vmin, 5rem);
  box-sizing: border-box;
  flex-shrink: 0;
}
.footer-image {
  width: 80%;
  /* max-width: 1000px;  */
  height: auto;
}

/* --- 두 번째 패널 --- */
.pattern-panel {
  background-color: #e9f7ff;
  background-image: radial-gradient(circle at center, #aaa 1px, transparent 1px),
    radial-gradient(circle at center, #aaa 1px, transparent 1px);
  background-size: clamp(25px, 4vmin, 40px) clamp(25px, 4vmin, 40px); /* vmin 사용 */
  background-position: 0 0,
    calc(clamp(25px, 4vmin, 40px) / 2) calc(clamp(25px, 4vmin, 40px) / 2); /* vmin 사용 */
  justify-content: center;
  align-items: center;
}
.dialogue-wrapper {
  width: 100%;
  max-width: 85%;
  display: flex;
  flex-direction: column;
  gap: clamp(1.5rem, 4vmin, 4rem); /* vmin 사용 */
  align-items: flex-start;
}
.dialogue-item {
  display: flex;
  align-items: flex-start;
  gap: clamp(0.5rem, 1.5vw, 1rem);
}
.avatar-dot {
  width: clamp(14px, 2.5vmin, 20px); /* vmin 사용 */
  height: clamp(14px, 2.5vmin, 20px); /* vmin 사용 */
  background-color: #000000;
  border-radius: 50%;
  flex-shrink: 0;
  margin-top: clamp(0.4rem, 1vmin, 1rem); /* vmin 사용 */
  opacity: 0;
  transform: scale(0);
  transition: all 0.5s cubic-bezier(0.34, 1.56, 0.64, 1);
}
.message-bubble {
  font-family: Pretendard, sans-serif;
  font-weight: 600;
  /* 기본 (가로 화면) 폰트 크기 */
  font-size: clamp(12px, 1.5vmin, 20px); /* 기본값을 작게 설정 */
  line-height: 1.3;
  background: #000000;
  color: #e9f7ff;
  padding: clamp(0.6rem, 1.8vmin, 1.8rem) clamp(1rem, 2.5vmin, 2.5rem); /* 패딩 조정 */
  border-radius: clamp(1rem, 2.2vmin, 2.2rem); /* 크기 조정 */
  width: fit-content;
  opacity: 0;
  transform: translateX(-20px); /* 이동 거리 줄임 */
  transition: all 0.6s cubic-bezier(0.25, 1, 0.5, 1);
}

/* [미디어 쿼리 추가] 세로 화면일 때 폰트 크기 증가 */
@media (orientation: portrait) {
  .message-bubble {
    font-size: clamp(16px, 4vmin, 40px); /* 세로가 길 때는 더 큰 폰트 적용 */
  }
}

.pattern-panel.is-active .dialogue-item:nth-child(1) .avatar-dot {
  transition-delay: 0.3s;
}
.pattern-panel.is-active .dialogue-item:nth-child(1) .message-bubble {
  transition-delay: 0.5s;
}
.pattern-panel.is-active .dialogue-item:nth-child(2) .avatar-dot {
  transition-delay: 1.3s;
}
.pattern-panel.is-active .dialogue-item:nth-child(2) .message-bubble {
  transition-delay: 1.5s;
}
.pattern-panel.is-active .dialogue-item:nth-child(3) .avatar-dot {
  transition-delay: 2.3s;
}
.pattern-panel.is-active .dialogue-item:nth-child(3) .message-bubble {
  transition-delay: 2.5s;
}
.pattern-panel.is-active .avatar-dot {
  opacity: 1;
  transform: scale(1);
}
.pattern-panel.is-active .message-bubble {
  opacity: 1;
  transform: translateX(0);
}

.loading-dots {
  display: flex;
  flex-direction: column;
  gap: clamp(0.4rem, 1.5vmin, 1rem); /* vmin 사용 */
  padding-left: clamp(0.1rem, 0.3vmin, 0.3rem); /* vmin 사용 */
}
.loading-dots span {
  width: clamp(10px, 1.8vmin, 16px); /* vmin 사용 */
  height: clamp(10px, 1.8vmin, 16px); /* vmin 사용 */
  background-color: #000;
  border-radius: 50%;
  opacity: 0;
}
.pattern-panel.is-active .loading-dots span:nth-child(1) {
  animation: dot-appear 0.4s forwards 3.5s, dot-disappear 0.4s forwards 5s;
}
.pattern-panel.is-active .loading-dots span:nth-child(2) {
  animation: dot-appear 0.4s forwards 3.8s, dot-disappear 0.4s forwards 5.3s;
}
.pattern-panel.is-active .loading-dots span:nth-child(3) {
  animation: dot-appear 0.4s forwards 4.1s, dot-disappear 0.4s forwards 5.6s;
}

/* --- 세 번째 패널 --- */
.selection-panel {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding-bottom: 274px;
  padding-top: 736px;
  background-color: #e9f7ff;
  align-items: center;
}
.selection-content {
  display: flex;
  flex-direction: column;
  gap: clamp(1.5rem, 3.5vmin, 3.5rem); /* vmin 사용 */
  width: 100%;
  max-width: 90%;
}
.selection-header {
  text-align: center;
}
.main-text {
  /* font-size: clamp(18px, 3.5vmin, 35px);
  font-weight: bold;
  color: #1a1a1a;
  margin-bottom: clamp(0.6rem, 1.2vmin, 1.2rem);  */
  font-family: Pretendard;
  font-weight: 600;
  font-style: SemiBold;
  font-size: 68.29px;
  leading-trim: NONE;
  line-height: 155%;
  letter-spacing: 0%;
}
.sub-text {
  /* font-size: clamp(10px, 1.6vmin, 16px);  */
  color: #929191;
  font-family: Pretendard;
  font-weight: 600;
  font-style: SemiBold;
  font-size: 40px;
  line-height: 100%;
  letter-spacing: 0%;
  text-align: center;
}
.option-group {
  display: flex;
  justify-content: center;
  gap: 67px
}
.sex-group{
  margin-top: 294px;
  margin-bottom: 383px;
}
.age-group {
  display: grid; /* Grid 사용 */
  grid-template-columns: repeat(3, 1fr); /* 3개의 열 */
  gap: 133px 68px; /* 세로, 가로 간격 */
  justify-items: center; /* 아이템 가로 중앙 정렬 */
}
.option-button {
  font-family: Pretendard;
  font-weight: 700;
  font-style: Bold;
  font-size: 80px;
  line-height: 100%;
  letter-spacing: 0%;
  padding: 10px 20px;
  width: 489px;
  height: 244px;
  opacity: 1;
  top: 1254px;
  left: 557px;
  border-width: 5px;
  border-radius: 200px;
  background-color: transparent;
  background-color: none;
  border: 5px solid #000000;
  padding: 3rem 5rem;
  /* padding: clamp(0.5rem, 1.2vmin, 1rem) clamp(0.8rem, 2.5vmin, 2rem); 패딩 조정 */
  /* font-size: clamp(12px, 2.2vmin, 20px); 크기 조정 */
  cursor: pointer;
  transition: all 0.2s ease;
  text-align: center;
}
.option-button-age{
  height: 205px;
}
.option-button:hover {
  background: #f0f0f0;
  border-color: #aaa;
}
.option-button.active {
  background: #1a1a1a;
  color: white;
  border-color: #1a1a1a;
}
.start-button {
  padding: 70px;
  border-radius: 200px;
  background: #1a1a1a;
  border: none;
 
  cursor: pointer;
  transition: transform 0.2s ease;
  width: 80%; /* 너비 100% */
  align-self: center; /* 중앙 정렬 */
}
.start-button:disabled {
  /* 비활성화 시 스타일 */
  opacity: 0.5;
  cursor: not-allowed;
  transform: none; /* 호버 효과 제거 */
}
.start-button:hover:not(:disabled) {
  transform: scale(1.05);
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
@keyframes dot-appear {
  from {
    opacity: 0;
    transform: scale(0.5);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}
@keyframes dot-disappear {
  from {
    opacity: 1;
    transform: scale(1);
  }
  to {
    opacity: 0;
    transform: scale(0.5);
  }
}
</style>

<style>
/* Chrome, Safari, Edge 등 Webkit 기반 브라우저용 */
::-webkit-scrollbar {
  display: none;
}
/* Firefox 및 최신 브라우저용 */
html {
  -ms-overflow-style: none;
  scrollbar-width: none;
}
</style>
