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
              :src="getCharacterImage(resultData.typeCode)"
              :alt="resultData.typeCode"
            />
          </div>

          <!-- 해시태그들 -->
          <div class="hashtags">
            <span
              v-for="(tag, index) in resultData.hashTags"
              :key="index"
              class="hashtag"
            >
              {{ tag }}
            </span>
          </div>

          <!-- 하단 버튼들 -->
          <div class="action-buttons">
            <button class="btn-outline">#테스트하기</button>
            <button class="btn-outline">#공유하기</button>
            <button class="btn-outline">#결과보기</button>
          </div>
          <h2 class="type-name">{{ resultData.description }}</h2>
        </div>
      </section>

      <!-- 2. Best/Worst Match 패널 -->
      <section class="panel match-panel">
        <div class="match-content">
          <h3 class="panel-title">BEST / WORST<br />MATCH</h3>

          <!-- BEST Match -->
          <div class="match-card best-match">
            <div class="match-label">BEST</div>
            <div class="match-info">
              <div class="match-character">
                <img
                  :src="getCharacterImage(resultData.bestMatch?.typeCode)"
                  alt="Best Match"
                />
              </div>
              <div class="match-details">
                <h4 class="match-type">
                  {{ resultData.bestMatch?.typeCode || "DEIP" }}
                </h4>
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
            <div class="match-label">WORST</div>
            <div class="match-info">
              <div class="match-character">
                <img
                  :src="getCharacterImage(resultData.worstMatch?.typeCode)"
                  alt="Worst Match"
                />
              </div>
              <div class="match-details">
                <h4 class="match-type">
                  {{ resultData.worstMatch?.typeCode || "NEIA" }}
                </h4>
                <p class="match-description">
                  {{
                    resultData.worstMatch?.description ||
                    "다른 성향을 가진 타입입니다."
                  }}
                </p>
              </div>
            </div>
          </div>

          <!-- 추천 루틴 -->
          <div class="routine-suggestion">
            <div class="routine-icon">💡</div>
            <h5>ROUTINE SUGGESTION</h5>
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
          <h3 class="statistics-title">
            성별에 따라면<br />
            <span class="highlight">결과 ○</span>
          </h3>

          <p class="statistics-subtitle">나와 같은 OPT 유형인 사람들은...</p>

          <p class="statistics-detail">전체 유형의 <strong>___%</strong></p>

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

// 이미지 경로 함수 - 16개 타입 모두 지원
function getCharacterImage(typeCode) {
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
}

.intro-text {
  font-family: Pretendard;
  font-weight: 700;
  font-style: Bold;
  font-size: 70px;
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

.type-name {
  font-size: clamp(24px, 4vh, 40px);
  font-weight: 500;
  margin-bottom: 4vh;
}

.character-image {
  /* width: clamp(200px, 30vh, 300px);
  height: clamp(200px, 30vh, 300px); */
  margin: 4vh auto;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.character-image img {
  width: 80%;
  height: 80%;
  object-fit: contain;
}

.hashtags {
  display: flex;
  flex-wrap: wrap;
  gap: 1vh 2vw;
  justify-content: center;
  margin: 4vh 0;
}

.hashtag {
  font-size: clamp(14px, 2vh, 18px);
  color: #aaa;
  padding: 1vh 2vw;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 20px;
}

.action-buttons {
  display: flex;
  gap: 2vw;
  justify-content: center;
  margin-top: 4vh;
}

.btn-outline {
  padding: 1.5vh 4vw;
  background: transparent;
  color: #fff;
  border: 2px solid #fff;
  border-radius: 25px;
  font-size: clamp(12px, 2vh, 16px);
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn-outline:hover {
  background: #fff;
  color: #000;
}

/* --- 2. Match 패널 --- */
.match-panel {
  background: #aac7dd;
}

.match-content {
  max-width: 800px;
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

.match-card {
  background: rgba(255, 255, 255, 0.9);
  border-radius: 3vh;
  padding: 3vh 4vw;
  margin-bottom: 3vh;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

.match-label {
  font-size: clamp(14px, 2vh, 18px);
  font-weight: bold;
  color: #666;
  margin-bottom: 2vh;
}

.match-info {
  display: flex;
  gap: 3vw;
  align-items: center;
}

.match-character {
  width: clamp(80px, 15vh, 120px);
  height: clamp(80px, 15vh, 120px);
  border-radius: 50%;
  background: #f0f0f0;
  flex-shrink: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.match-character img {
  width: 70%;
  height: 70%;
  object-fit: contain;
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
  font-size: clamp(12px, 2vh, 16px);
  line-height: 1.5;
  color: #333;
}

.routine-suggestion {
  padding: 3vh 4vw;
  margin-top: 3vh;
  text-align: center;
  opacity: 1;
  top: 3017px;
  left: 177px;
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
  font-size: clamp(12px, 2vh, 16px);
  padding: 1vh 0;
  color: #333;
}

.routine-list li::before {
  content: "○ ";
  color: #666;
  margin-right: 1vw;
}

/* --- 3. 통계 패널 --- */
.statistics-panel {
  background: #fff;
}

.statistics-content {
  max-width: 700px;
  width: 100%;
  text-align: center;
}

.statistics-title {
  font-size: clamp(28px, 5vh, 42px);
  font-weight: bold;
  color: #000;
  margin-bottom: 2vh;
  line-height: 1.3;
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
  font-size: clamp(14px, 2vh, 18px);
  color: #666;
  margin-bottom: 4vh;
}

.donut-chart {
  width: clamp(250px, 40vh, 350px);
  height: clamp(250px, 40vh, 350px);
  margin: 0 auto 5vh;
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
