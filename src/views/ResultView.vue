<template>
  <div class="result-container">
    <div class="result-scroll-container" ref="scrollContainerRef">
      <!-- 1. 타입 소개 패널 -->
      <section class="panel type-intro-panel">
        <div class="type-image"></div>
        <div class="type-intro-content">
          <p class="intro-text">{{ resultData.typeName }}</p>
          <img
            :src="getTypeImage(resultData.typeCode)"
            :alt="resultData.typeCode"
          />
          <!-- 타입 캐릭터 이미지 -->
          <div class="character-image">
            <img
              :src="getCharacterGif(resultData.typeCode)"
              :alt="resultData.typeCode"
            />
          </div>

          <!-- 해시태그들 -->
          <div class="hashtags">
            <div
              v-for="(tag, index) in resultData.hashTags"
              :key="index"
              class="hashtag"
            >
              {{ tag }}
            </div>
          </div>

          <div class="type-description">{{ resultData.description }}</div>
        </div>
      </section>

      <!-- 2. Best/Worst Match 패널 -->
      <section class="panel match-panel">
        <div class="match-content">
          <h3 class="panel-title">BEST / WORST<br />MATCH</h3>

          <div class="match-card-container">
            <div class="match-card best-match">
              <div class="match-info">
                <div>
                  <div class="match-character">
                    <div class="match-label">BEST</div>

                    <img
                      :src="
                        getCharacterMatchImage(resultData.bestMatch?.typeCode)
                      "
                      alt="Best Match"
                    />
                  </div>
                </div>

                <div class="match-details">
                  <img
                    :src="
                      getCharacterMatchTypeImage(resultData.bestMatch?.typeCode)
                    "
                    alt="Worst Match"
                  />
                  <p class="match-description">
                    {{
                      resultData.bestMatch?.description ||
                      "최고의 궁합을 가진 타입입니다."
                    }}
                  </p>
                </div>
              </div>
            </div>

            <!-- WORST Match -->
            <div class="match-card worst-match">
              <div class="match-info">
                <div>
                  <div class="match-character">
                    <div class="match-label">WORST</div>

                    <img
                      :src="
                        getCharacterMatchImage(resultData.worstMatch?.typeCode)
                      "
                      alt="Worst Match"
                    />
                  </div>
                </div>

                <div class="match-details">
                  <img
                    :src="
                      getCharacterMatchTypeImage(
                        resultData.worstMatch?.typeCode
                      )
                    "
                    alt="Worst Match"
                  />
                  <p class="match-description">
                    {{
                      resultData.worstMatch?.description ||
                      "다른 성향을 가진 타입입니다."
                    }}
                  </p>
                </div>
              </div>
            </div>
          </div>
          <div class="routine-text">ROUTINE SUGGESTION</div>

          <!-- 추천 루틴 -->
          <div class="routine-suggestion">
            <ul class="routine-list">
              <li v-for="(routine, index) in resultData.routines" :key="index">
                {{ routine }}
              </li>
            </ul>
          </div>
        </div>
      </section>

      <!-- 3. 통계 패널 -->
      <section class="panel statistics-panel">
        <div class="statistics-content">
          <div class="statistics-title">
            설문에 참여한<br />xx명 중<br /><br />나와 같은 OPT 유형을 가진
            사람들은...
            <!-- <span class="highlight">xx명 중</span> -->
          </div>
          <div class="statistics-detail">전체 유형의 <strong>___%</strong></div>

          <!-- 도넛 차트 -->
          <div class="donut-chart">
            <svg viewBox="0 0 200 200" width="300" height="300">
              <circle
                cx="100"
                cy="100"
                r="80"
                fill="none"
                stroke="#e0e0e0"
                stroke-width="40"
              />
              <circle
                cx="100"
                cy="100"
                r="80"
                fill="none"
                stroke="#000"
                stroke-width="40"
                :stroke-dasharray="`${genderPercentage * 5.03} 502.4`"
                stroke-dashoffset="125.6"
                transform="rotate(-90 100 100)"
                class="donut-segment"
              />
              <text x="70" y="95" font-size="14" fill="#000">
                {{ genderPercentage }}%
              </text>
              <text x="70" y="115" font-size="12" fill="#666">남성</text>
              <text x="130" y="95" font-size="14" fill="#000">
                {{ 100 - genderPercentage }}%
              </text>
              <text x="130" y="115" font-size="12" fill="#666">여성</text>
            </svg>
          </div>

          <!-- 연령대별 막대 그래프 -->
          <div class="age-statistics">
            <div v-for="(data, index) in ageData" :key="index" class="age-row">
              <span class="age-label">{{ data.label }}</span>
              <div class="age-bar-container">
                <div class="age-bar" :style="{ width: data.percentage + '%' }">
                  <span class="age-dots">
                    <span
                      v-for="i in data.dots"
                      :key="i"
                      class="dot filled"
                    ></span>
                    <span
                      v-for="i in 10 - data.dots"
                      :key="'empty-' + i"
                      class="dot empty"
                    ></span>
                  </span>
                </div>
              </div>
              <span class="age-percentage">{{ data.percentage }}%</span>
            </div>
          </div>
        </div>
      </section>

      <!-- 4. QR/프린트 패널 -->
      <section class="panel qr-panel">
        <div class="qr-content">
          <h1 class="qr-title">
            SCAN<br />
            OR<br />
            PRINT<br />
            YOUR<br />
            OPT!
          </h1>

          <!-- 클립보드 프린트 섹션 -->
          <div class="print-section" @click="handlePrint">
            <div class="clipboard-icon" :class="{ printing: isPrinting }">
              <img :src="clipboardGif" alt="Print" />
            </div>
            <p class="print-label">클립보드 프린트</p>
            <p v-if="isPrinting" class="print-status">인쇄 중...</p>
            <p v-if="printError" class="print-error">{{ printError }}</p>
          </div>

          <!-- 서울숲 저장 섹션 -->
          <div class="save-section">
            <div class="cursor-icon">
              <img :src="cursorGif" alt="Save" />
            </div>
            <p class="save-label">서울숲으로</p>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useUserSelectionStore } from "@/stores/userSelection";

const userSelectionStore = useUserSelectionStore();
const scrollContainerRef = ref(null);

// 서버에서 받은 결과 데이터 (디폴트 값 포함)
const resultData = computed(() => userSelectionStore.getResult);

// 성별 비율 계산 (예시로 60% 설정, 실제로는 서버 데이터 사용)
const genderPercentage = ref(60);

// 연령대별 데이터 가공
const ageData = computed(() => {
  const ageLabels = ["10대", "20대", "30대", "40대", "50대", "기타"];
  const surveyList = resultData.value.surveyListByAge || [5, 55, 25, 20, 0, 0];
  const total = resultData.value.totalCount || 100;

  return ageLabels.map((label, index) => {
    const count = surveyList[index] || 0;
    const percentage = Math.round((count / total) * 100);
    const dots = Math.round(percentage / 10);

    return {
      label,
      percentage,
      dots: Math.min(dots, 10),
    };
  });
});

function getCharacterMatchImage(typeCode) {
  if (!typeCode) return "";
  try {
    return new URL(
      `../assets/images/matchCharacters/${typeCode}.png`,
      import.meta.url
    ).href;
  } catch {
    return "";
  }
}
function getCharacterMatchTypeImage(typeCode) {
  if (!typeCode) return "";
  try {
    return new URL(
      `../assets/images/matchCharactersType/${typeCode}.png`,
      import.meta.url
    ).href;
  } catch {
    return "";
  }
}

// 이미지 경로 함수 - 16개 타입 모두 지원
function getCharacterImage(typeCode) {
  if (!typeCode) return "";
  try {
    return new URL(`../assets/images/${typeCode}.png`, import.meta.url).href;
  } catch {
    return "";
  }
}
//gif 경로함수 - 16개 타입 모두 지원
function getCharacterGif(typeCode) {
  if (!typeCode) return "";
  try {
    return new URL(
      `../assets/images/characters/${typeCode}.gif`,
      import.meta.url
    ).href;
  } catch {
    return "";
  }
}
function getTypeImage(typeCode) {
  if (!typeCode) return "";
  try {
    return new URL(
      `../assets/images/typeNames/${typeCode}.png`,
      import.meta.url
    ).href;
  } catch {
    return "";
  }
}

const clipboardGif = new URL("..\assets\images\print.png", import.meta.url)
  .href;
const cursorGif = new URL("../assets/images/cursor.gif", import.meta.url).href;

onMounted(() => {
  // 스크롤 애니메이션 관찰
  const observerOptions = {
    threshold: 0.5,
    rootMargin: "0px",
  };

  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        entry.target.classList.add("visible");
      }
    });
  }, observerOptions);

  const panels = scrollContainerRef.value?.querySelectorAll(".panel");
  panels?.forEach((panel) => observer.observe(panel));
});
</script>

<style scoped>
.result-container {
  width: 100vw;
  height: 100vh;
  background: #000;
  overflow: hidden;
}

.result-scroll-container {
  width: 100%;
  height: 100%;
  overflow-y: scroll;
  scroll-snap-type: y mandatory;
  scroll-behavior: smooth;
}

.panel {
  min-height: 100vh;
  scroll-snap-align: start;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 5vh 5vw;
  box-sizing: border-box;
  opacity: 0;
  transform: translateY(30px);
  transition: opacity 0.8s ease, transform 0.8s ease;
}

.panel.visible {
  opacity: 1;
  transform: translateY(0);
}

/* --- 1. 타입 소개 패널 --- */
.type-intro-panel {
  background: #101010;
  color: #fff;
}

.type-intro-content {
  text-align: center;
  max-width: 600px;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.intro-text {
  font-family: Pretendard;
  font-weight: 700;
  font-style: Bold;
  font-size: 70px;
  color: #aac7dd;
}

.type-code {
  font-size: clamp(60px, 12vh, 120px);
  font-weight: bold;
  letter-spacing: 0.1em;
  margin: 2vh 0;
  background: linear-gradient(45deg, #fff, #aaa);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.type-description {
  border-radius: 72.51px;
  padding: 20px 70px;
  background: transparent;
  font-family: Pretendard;
  font-weight: 600;
  font-style: SemiBold;
  font-size: 50px;
  line-height: 155%;
  letter-spacing: 0.25px;
  text-align: center;

  color: #aac7dd;
  border: 2px solid #fff;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-bottom: 4vh;
  margin-top: 4vh;
}

.character-image {
}

.hashtags {
  display: flex;
  gap: 2vw;
  justify-content: center;
  margin-top: 4vh;
}

.hashtag {
  border-radius: 72.51px;
  white-space: nowrap;
  /* padding: 1.5vh 4vw; */
  padding: 20px 70px;
  background: transparent;
  font-family: Pretendard;
  font-weight: 700;
  font-style: Bold;
  font-size: 60px;
  line-height: 140%;
  letter-spacing: 0.25px;
  color: #aac7dd;
  border: 2px solid #fff;
  /* font-size: clamp(12px, 2vh, 16px); */
  cursor: pointer;
  transition: all 0.3s ease;
}

.tashtag:hover {
  background: #fff;
  color: #000;
}

/* --- 2. Match 패널 --- */
.match-panel {
  background: #aac7dd;
}

.match-content {
  width: 100%;
}

.panel-title {
  font-family: Pretendard;
  font-weight: 900;
  color: #ffffff;
  font-size: 70px;
  line-height: 120%;
  letter-spacing: 0.25px;
  text-align: center;
}
.match-card-container {
  display: flex;
  flex-direction: column;
  gap: 124px;
}

.match-card {
  border-radius: 72.51px;
  background: transparent;
  border: 2px solid #fff;
  cursor: pointer;
  padding: 80px;
  font-family: Pretendard;
  font-weight: 500;
  font-size: 33.45px;
  line-height: 150%;
  letter-spacing: 0%;
  color: #fff;
}

.match-label {
  font-family: Pretendard;
  font-weight: 700;
  font-size: 50px;
  line-height: 100%;
  letter-spacing: 0%;
  text-align: start;
  color: #ffffff;
}

.match-info {
  display: flex;
  gap: 3vw;
  align-items: center;
}

.match-character {
  width: 802px;
  height: 811px;
  border-radius: 72.51px;
  background-color: rgba(255, 255, 255, 0.1); /* 배경만 반투명 */
  display: flex;
  flex-direction: column;
  justify-content: center;
  box-sizing: border-box;
  padding: 80px;
}

.match-details {
  flex: 1;
}

.match-type {
  font-size: clamp(20px, 3.5vh, 32px);
  font-weight: bold;
  margin-bottom: 1vh;
}

.match-description {
}
.routine-text {
  font-family: Pretendard;
  font-weight: 900;
  font-size: 70px;
  line-height: 140%;
  letter-spacing: 0.25px;
  color: #fff;
  display: flex;
  justify-content: center;
  margin-top: 176px;
  margin-bottom: 56px;
}

.routine-suggestion {
  padding: 3vh 4vw;
  text-align: center;
  opacity: 1;
  border-radius: 85px;

  border: 5px solid #ffffff;
}

.routine-icon {
  font-size: clamp(32px, 5vh, 48px);
  margin-bottom: 2vh;
}

.routine-suggestion h5 {
  font-size: clamp(16px, 2.5vh, 20px);
  font-weight: bold;
  margin-bottom: 2vh;
}

.routine-list {
  list-style: none;
  padding: 0;
  text-align: left;
}

.routine-list li {
  font-family: Pretendard;
  font-weight: 600;
  font-size: 50px;
  line-height: 187%;
  letter-spacing: 0.25px;
  color: #ffffff;
}

/* --- 3. 통계 패널 --- */
.statistics-panel {
  background: #fff;
}

.statistics-content {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.statistics-title {
  white-space: nowrap;
  font-family: Pretendard;
  font-weight: 700;
  font-size: 70px;
  line-height: 100%;
  text-align: center;
  margin-bottom: 154px;
}

.highlight {
  color: #4a90e2;
}

.statistics-subtitle {
  font-size: clamp(16px, 2.5vh, 22px);
  color: #333;
  margin-bottom: 1vh;
}

.statistics-detail {
  font-family: Pretendard;
  font-weight: 700;
  font-size: 70px;
  line-height: 100%;
  text-align: center;
}

.donut-chart {
  width: clamp(250px, 40vh, 350px);
  height: clamp(250px, 40vh, 350px);
  margin: 234px;
}

.donut-segment {
  transition: stroke-dasharray 1s ease;
}

.age-statistics {
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
}

.age-row {
  display: flex;
  align-items: center;
  margin-bottom: 2vh;
  gap: 2vw;
}

.age-label {
  font-size: clamp(14px, 2vh, 18px);
  font-weight: bold;
  width: 60px;
  text-align: left;
}

.age-bar-container {
  flex: 1;
  height: 3vh;
  background: #f0f0f0;
  border-radius: 1.5vh;
  overflow: hidden;
}

.age-bar {
  height: 100%;
  background: #000;
  display: flex;
  align-items: center;
  padding: 0 1vw;
  transition: width 1s ease;
}

.age-dots {
  display: flex;
  gap: 0.5vw;
}

.dot {
  width: 1vh;
  height: 1vh;
  border-radius: 50%;
}

.dot.filled {
  background: #fff;
}

.dot.empty {
  background: rgba(255, 255, 255, 0.3);
}

.age-percentage {
  font-size: clamp(12px, 2vh, 16px);
  font-weight: bold;
  width: 50px;
  text-align: right;
}

/* --- 4. QR 패널 --- */
.qr-panel {
  background: #000;
  color: #fff;
}

.qr-content {
  text-align: center;
  max-width: 600px;
  width: 100%;
}

.qr-title {
  font-size: clamp(48px, 10vh, 80px);
  font-weight: bold;
  letter-spacing: 0.05em;
  line-height: 1.2;
  margin-bottom: 6vh;
}

.print-section,
.save-section {
  margin: 5vh 0;
}

.clipboard-icon,
.cursor-icon {
  width: clamp(120px, 20vh, 180px);
  height: clamp(120px, 20vh, 180px);
  margin: 0 auto 2vh;
  border: 3px solid #fff;
  border-radius: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(255, 255, 255, 0.05);
}

.clipboard-icon img,
.cursor-icon img {
  width: 70%;
  height: 70%;
  object-fit: contain;
}

.print-label,
.save-label {
  font-size: clamp(18px, 3vh, 28px);
  font-weight: bold;
  color: #ff4444;
}

/* 스크롤바 숨김 */
::-webkit-scrollbar {
  display: none;
}

.result-scroll-container {
  -ms-overflow-style: none;
  scrollbar-width: none;
}
</style>
