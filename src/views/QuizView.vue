<template>
  <div class="container">
    <!-- 모든 콘텐츠를 감싸는 비율 유지 래퍼 -->
    <div class="aspect-ratio-wrapper">
      <div class="quiz-scroll-container" ref="scrollContainerRef">
        <!-- 1. 시작 패널 -->
        <section class="panel start-panel" ref="startPanelRef">
          <div class="start-content-wrapper">
            <img
              :src="plusImage"
              alt="decoration"
              class="plus-sign plus-top-left"
            />
            <img
              :src="plusImage"
              alt="decoration"
              class="plus-sign plus-middle-right"
            />
            <img
              :src="plusImage"
              alt="decoration"
              class="plus-sign plus-bottom-left"
            />
            <img :src="titleImage" alt="What's your OPT?" class="title-image" />
          </div>
        </section>

        <!-- 2. 퀴즈 콘텐츠 패널 -->
        <section class="panel quiz-content-panel" ref="quizPanelRef">
          <!-- 헤더를 absolute로 배치하여 뒤에 깔리도록 -->
          <header class="header">
            <div class="inner-wrapper">
              <div class="header-bars-wrapper">
                <img
                  v-for="n in 5"
                  :key="n"
                  :src="backgroundBarImageWhite"
                  alt="background bar"
                  class="header-bar"
                />
              </div>
            </div>
          </header>

          <!-- 퀴즈 진행 중 -->
          <template
            v-if="!isLoading && !isComplete && currentQuestionIndex >= 0"
          >
            <!-- 퀴즈 UI 전체를 감싸는 래퍼 -->
            <div class="quiz-ui-wrapper">
              <header class="question-header">
                <div class="progress-dots">
                  <span
                    v-for="i in quizData.length"
                    :key="i"
                    :class="{ active: i <= currentQuestionIndex + 1 }"
                  ></span>
                </div>
              </header>

              <main class="question-card">
                <div class="card-content">
                  <span class="question-number"
                    >Q{{ String(currentQuestionIndex + 1) }}.</span
                  >

                  <div class="question-text">
                    <transition name="fade" mode="out-in">
                      <p class="korean" :key="currentQuestionIndex + '-ko'">
                        {{ currentQuestion.text_ko }}
                      </p>
                    </transition>
                    <transition name="fade" mode="out-in">
                      <p class="english" :key="currentQuestionIndex + '-en'">
                        {{ currentQuestion.text_en }}
                      </p>
                    </transition>
                  </div>
                  <transition name="fade" mode="out-in">
                    <div class="answer-options" :key="currentQuestionIndex">
                      <button
                        @click="selectOption(0)"
                        :class="{ active: selectedAnswerIndex === 0 }"
                      >
                        <span>
                          {{ currentQuestion.answers[0] }}
                        </span>
                      </button>
                      <button
                        @click="selectOption(1)"
                        :class="{ active: selectedAnswerIndex === 1 }"
                      >
                        <span>
                          {{ currentQuestion.answers[1] }}
                        </span>
                      </button>
                    </div>
                  </transition>
                </div>

                <footer class="card-footer">
                  <button class="back-button" @click="goBack">
                    <img src="../assets/images/back.png" />
                  </button>
                  <button
                    class="next-button"
                    @click="goToNextQuestion"
                    :disabled="selectedAnswerIndex === null"
                  >
                    <img src="../assets/images/go.png" />
                  </button>
                </footer>
              </main>
            </div>
          </template>

          <!-- 로딩 중 -->
          <template v-else-if="isLoading">
            <div class="status-message">
              <h2>전송 중...</h2>
              <p>설문 결과를 서버로 보내고 있습니다.</p>
              <!-- 간단한 로딩 스피너 (CSS) -->
              <div class="loader"></div>
            </div>
          </template>

          <!-- 완료 화면 -->
          <template v-else-if="isComplete">
            <div
              class="status-message completion-screen"
              :class="{ success: completionStatus, error: !completionStatus }"
            >
              <h2>{{ completionStatus ? "제출 완료!" : "오류" }}</h2>
              <p>{{ completionMessage }}</p>
              <!-- 필요하다면 결과 페이지로 이동하는 버튼 등을 추가 -->
            </div>
          </template>

          <!-- [수정됨] 퀴즈 시작 전 빈 상태를 처리할 v-else 블록 추가 -->
          <template v-else>
            <div class="initial-placeholder"></div>
          </template>
          <!-- 헤더를 absolute로 배치하여 뒤에 깔리도록 -->
          <header class="footer">
            <div class="inner-wrapper">
              <div class="footer-bars-wrapper">
                <img
                  v-for="n in 5"
                  :key="n"
                  :src="backgroundBarImageWhite"
                  alt="background bar"
                  class="footer-bar"
                />
              </div>
            </div>
          </header>
        </section>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted, nextTick } from "vue";
import { useRouter } from "vue-router";
// [수정] Pinia 스토어를 가져옵니다.
import { useUserSelectionStore } from "@/stores/userSelection";
// --- 이미지 임포트 ---
import titleImage from "@/assets/images/title_whats_your_opt.png";
import plusImage from "@/assets/images/plus_sign_dotted.png";
import backgroundBarImageWhite from "@/assets/images/backgroundBar_white.png";

const router = useRouter();
// [수정] Pinia 스토어 인스턴스를 생성합니다.
const userSelectionStore = useUserSelectionStore();

// --- 퀴즈 데이터 ---
const quizData = ref([
  {
    id: 1,
    q: "Q1.",
    text_ko: "온라인 속에서 가장 활발한 시간은 언제인가요?",
    text_en: "When are you most active online?",
    answers: ["DAY", "NIGHT"],
  },
  {
    id: 2,
    q: "Q2.",
    text_ko: "온라인 속 당신의 커뮤니케이션 스타일은?",
    text_en: "What is your style of communication when you are online?",
    answers: ["MINIMAL", "EXPRESSIVE"],
  },
  {
    id: 3,
    q: "Q3.",
    text_ko: "당신이 끌리는 콘텐츠는 어떤 유형인가요?",
    text_en: "What type of content interests you?",
    answers: ["FUN", "INFORMATIVE"],
  },
  {
    id: 4,
    q: "Q4.",
    text_ko: "온라인 속 당신의 행동 방식은?",
    text_en: "How would you describe your online behavior?",
    answers: ["ACTIVE", "PASSIVE"],
  },
  // ... (나머지 퀴즈 데이터가 있다면 여기에)
]);

const userAnswers = ref(Array(quizData.value.length).fill(null));
const currentQuestionIndex = ref(-1);
const scrollContainerRef = ref(null);
const startPanelRef = ref(null);
const quizPanelRef = ref(null);
const isLoading = ref(false);
const isComplete = ref(false);
const completionStatus = ref(false); // true: success, false: error
const completionMessage = ref("");
const selectedAnswerIndex = ref(null); // 현재 선택한 답변 인덱스

const currentQuestion = computed(() => {
  if (
    currentQuestionIndex.value >= 0 &&
    currentQuestionIndex.value < quizData.value.length
  ) {
    return quizData.value[currentQuestionIndex.value];
  }
  return { q: "", text_ko: "", text_en: "", answers: ["", ""] };
});

let scrollObserver;

onMounted(() => {
  // [수정] Pinia 스토어 값 확인 (URL 쿼리 대신)
  console.log(
    "QuizView - Info from Store:",
    userSelectionStore.gender,
    userSelectionStore.age
  );

  scrollObserver = new IntersectionObserver(
    (entries) => {
      entries.forEach(async (entry) => {
        // 퀴즈 패널이 화면에 보이기 시작하고, 퀴즈가 아직 시작 전(-1)이면
        if (entry.isIntersecting && currentQuestionIndex.value === -1) {
          console.log("Quiz panel is intersecting, showing first question.");
          currentQuestionIndex.value = 0; // 퀴즈 시작
          if (quizPanelRef.value) {
            scrollObserver.unobserve(quizPanelRef.value);
          }
        }
      });
    },
    { threshold: 0.1 } // 퀴즈 패널이 10% 보였을 때
  );

  // 퀴즈 패널(quizPanelRef)을 감시 대상으로 등록
  if (quizPanelRef.value) {
    scrollObserver.observe(quizPanelRef.value);
  }
});

onUnmounted(() => {
  if (scrollObserver) scrollObserver.disconnect();
});

// 답변 '선택' 함수 (NEXT 버튼용)
function selectOption(index) {
  selectedAnswerIndex.value = index;
}

// 다음 질문으로 '이동' 함수
async function goToNextQuestion() {
  if (selectedAnswerIndex.value === null) return;

  userAnswers.value[currentQuestionIndex.value] = selectedAnswerIndex.value;

  const nextQuestionIndex = currentQuestionIndex.value + 1;

  setTimeout(async () => {
    if (nextQuestionIndex < quizData.value.length) {
      currentQuestionIndex.value = nextQuestionIndex;
      selectedAnswerIndex.value = null; // 선택 초기화
    } else {
      await sendSurveyData();
    }
  }, 300);
}

// [수정됨] 서버로 데이터 전송 함수 (로딩 페이지로 이동 후 결과 페이지로 이동)
async function sendSurveyData() {
  // --- 1. 데이터 가공 ---
  const genderToSend =
    userSelectionStore.gender === "male"
      ? "남"
      : userSelectionStore.gender === "female"
      ? "여"
      : "기타";
  const parsedAge = parseInt(userSelectionStore.age) || 0;

  const surveyPayload = {
    gender: genderToSend,
    age: parsedAge,
    questionList: userAnswers.value,
  };

  console.log("서버로 전송할 최종 데이터:", surveyPayload);

  // --- 2. 로딩 페이지로 이동 ---
  router.push("/loading");

  // --- 3. 최소 3초 대기와 서버 통신을 병렬 처리 ---
  const minLoadingTime = new Promise((resolve) => setTimeout(resolve, 3000));

  const serverRequest = fetch("/question/survey", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(surveyPayload),
  });

  try {
    // 최소 3초와 서버 응답 둘 다 완료될 때까지 대기
    const [_, response] = await Promise.all([minLoadingTime, serverRequest]);

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const data = await response.json();
    console.log("서버 응답:", data);

    // 서버 응답을 Pinia 스토어에 저장
    userSelectionStore.setResult(data);

    // 결과 페이지로 이동
    router.push("/result");
  } catch (error) {
    console.error("데이터 전송 실패:", error);
    // 에러 발생 시에도 최소 3초는 기다린 후 에러 페이지로 이동
    await minLoadingTime;
    // 에러 처리 (에러 페이지로 이동하거나 알림 표시)
    alert("제출 중 오류가 발생했습니다. 다시 시도해주세요.");
    router.push("/"); // 또는 에러 페이지
  }
}

// 완료 화면 표시 함수
function showCompletionScreen(isSuccess, message) {
  completionStatus.value = isSuccess;
  completionMessage.value = message;
  isComplete.value = true;
}

// 뒤로가기 함수
async function goBack() {
  if (currentQuestionIndex.value === 0) {
    startPanelRef.value?.scrollIntoView({ behavior: "smooth" });
    setTimeout(() => {
      currentQuestionIndex.value = -1;
      selectedAnswerIndex.value = null;
      if (quizPanelRef.value) {
        scrollObserver.observe(quizPanelRef.value);
      }
    }, 500);
  } else if (currentQuestionIndex.value > 0) {
    const prevQuestionIndex = currentQuestionIndex.value - 1;
    selectedAnswerIndex.value = userAnswers.value[prevQuestionIndex];
    isComplete.value = false;
    isLoading.value = false;
    currentQuestionIndex.value = prevQuestionIndex;
  }
}
</script>

<style scoped>
.container {
  min-height: 100vh;
  width: 100vw;
  background: #000;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

.header {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 28%;
  box-sizing: border-box;
  z-index: 0;
  pointer-events: none;
}

.inner-wrapper {
  width: 100%;
  margin: 0 auto;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.header .inner-wrapper {
  justify-content: space-between;
}

.header-bars-wrapper {
  background-image: url("../assets/images/homebackground.png");
  background-size: cover;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 100%;
  padding: 85px;
}

.header-bar {
  height: auto;
}

.header-bar:nth-child(2) {
  opacity: 0.7;
}

.header-bar:nth-child(3) {
  opacity: 0.5;
}

.header-bar:nth-child(4) {
  opacity: 0.3;
}

.header-bar:nth-child(5) {
  opacity: 0.2;
}

.footer {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 28%;
  box-sizing: border-box;
  z-index: 0;
  pointer-events: none;
}

.footer .inner-wrapper {
  justify-content: space-between;
}

.footer-bars-wrapper {
  background-image: url("../assets/images/homebackground.png");
  background-size: cover;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 100%;
  padding: 85px 132px;
}

.footer-bar {
  height: auto;
}

.footer-bar:nth-child(4) {
  opacity: 0.7;
}

.footer-bar:nth-child(3) {
  opacity: 0.5;
}

.footer-bar:nth-child(2) {
  opacity: 0.3;
}

.footer-bar:nth-child(1) {
  opacity: 0.2;
}

.aspect-ratio-wrapper {
  position: relative;
  width: calc(100vh * 9 / 16);
  height: 100vh;
  max-width: 100vw;
  max-height: calc(100vw * 16 / 9);
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
}

.quiz-scroll-container {
  height: 100%;
  width: 100%;
  overflow-y: scroll;
  scroll-snap-type: y mandatory;
  background-color: #000000;
}

.panel {
  height: 100%;
  scroll-snap-align: start;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  position: relative;
  padding: 10vh 8vw;
}

/* --- 시작 패널 스타일 --- */
.start-panel {
  justify-content: center;
  align-items: center;
}

.start-content-wrapper {
  position: relative;
  width: 80%;
  max-width: 70vh;
  display: flex;
  justify-content: center;
  align-items: center;
  animation: fadeIn 1s ease-out forwards;
}

.title-image {
  width: 100%;
  height: auto;
}

.plus-sign {
  position: absolute;
  width: 10%;
  height: auto;
}

.plus-top-left {
  top: -50%;
  left: 0;
}

.plus-middle-right {
  top: 50%;
  right: -5%;
  transform: translateY(-50%);
}

.plus-bottom-left {
  bottom: -40%;
  left: 15%;
}

/* --- 퀴즈 콘텐츠 패널 스타일 --- */
.quiz-content-panel {
  position: relative;
  justify-content: center;
  align-items: center;
  background-image: radial-gradient(circle at center, #333 1px, transparent 1px),
    radial-gradient(circle at center, #333 1px, transparent 1px);
  background-size: 5vh 5vh;
  background-position: 0 0, 2.5vh 2.5vh;
}

.quiz-ui-wrapper {
  position: relative;
  z-index: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100%;
  width: 100%;
}

.question-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  max-width: 90%;
  color: white;
  flex-shrink: 0;
  margin-bottom: 3vh;
}

.back-button {
  background: none;
  border: none;
  padding: 0;
  cursor: pointer;
  display: flex;
  align-items: center;
}

.progress-dots {
  display: flex;
  gap: 1vw;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
}

.progress-dots span {
  width: 1.8vh;
  height: 1.8vh;
  border-radius: 50%;
  border: 2px solid white;
  background-color: transparent;
  transition: background-color 0.3s ease;
}

.progress-dots span.active {
  background-color: white;
}

.question-card {
  background-color: #e9f7ff;
  border-radius: 4vh;
  padding: 4vh 5vw;
  width: 100%;
  max-width: 90%;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.card-content {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 5vh;
}

.question-number {
  font-family: Pretendard;
  font-weight: 800;
  font-style: ExtraBold;
  font-size: 70px;
  leading-trim: NONE;
  line-height: 176%;
  letter-spacing: 0%;
  color: #000;
  align-self: flex-start;
  margin-bottom: 2vh;
}

.question-text {
  text-align: left;
  color: #1a1a1a;
}

.question-text p {
  width: 100%;
  margin: 0;
  font-family: Helvetica;
  font-weight: 700;
  font-style: Bold;
  font-size: 100px;
  leading-trim: NONE;
  line-height: 150px;
  letter-spacing: 0%;
}

.korean {
  font-size: clamp(24px, 5vmin, 50px);
  font-weight: bold;
  line-height: 1.3;
  margin-bottom: 1.5vh;
}

.english {
  font-size: clamp(16px, 3vmin, 28px);
  color: #555;
  line-height: 1.3;
}

.answer-options {
  display: flex;
  flex-direction: column;
  gap: 2.5vh;
  align-items: flex-start;
  width: 100%;
}

.answer-options button {
  width: auto;
  padding: 46px 135px;
  background-color: transparent;
  color: black;
  font-weight: bold;
  transition: all 0.2s ease;
  text-align: center;

  angle: 0 deg;
  opacity: 1;
  border-radius: 166.35px;
  border-width: 4.16px;
  border: 4.16px solid #000000;
}
.answer-options span {
  font-family: Pretendard;
  font-weight: 900;
  font-style: Black;
  font-size: 124.76px;
  line-height: 100%;
  letter-spacing: 0%;
}

.answer-options button:hover {
  background-color: rgba(0, 0, 0, 0.1);
}

.answer-options button.active {
  background-color: #1a1a1a;
  color: white;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  margin-top: 4vh;
}

.next-button {
  background: none;
  border: none;
  padding: 0;
  cursor: pointer;
  display: flex;
  align-items: center;
}

/* --- 로딩 및 완료 화면 스타일 --- */
.status-message {
  position: relative;
  z-index: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  color: white;
  height: 100%;
  width: 100%;
}

.status-message h2 {
  font-size: clamp(24px, 5vh, 50px);
  margin-bottom: 2vh;
}

.status-message p {
  font-size: clamp(16px, 3vh, 30px);
}

.completion-screen.success h2 {
  color: #4caf50;
}

.completion-screen.error h2 {
  color: #f44336;
}

.loader {
  border: 4px solid #f3f3f3;
  border-top: 4px solid #ffffff;
  border-radius: 50%;
  width: clamp(30px, 5vmin, 50px);
  height: clamp(30px, 5vmin, 50px);
  animation: spin 1s linear infinite;
  margin-top: 3vh;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

/* 애니메이션 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.initial-placeholder {
  display: block;
  width: 100%;
  height: 100%;
}
</style>

<!-- 스크롤바 숨김 -->
<style>
::-webkit-scrollbar {
  display: none;
}
html {
  -ms-overflow-style: none;
  scrollbar-width: none;
}
</style>
