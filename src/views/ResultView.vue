<template>
  <div class="result-container">
    <div class="result-scroll-container" ref="scrollContainerRef">
      <!-- 1. 타입 소개 패널 -->
      <section class="panel type-intro-panel">
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
                    alt="Best Match Type"
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
                    alt="Worst Match Type"
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
            설문에 참여한<br />
            <strong>{{ resultData.totalCount }}명</strong> 중
          </div>

          <div class="statistics-subtitle">
            나와 같은 OPT 유형을 가진 사람들은...
          </div>

          <div class="statistics-detail">
            전체 유형의 <strong>{{ myTypePercentage }}%</strong>
          </div>

          <div class="donut-chart-container">
            <!-- 왼쪽 라벨 (남성) -->
             <div class="chart-label">
              <text class="chart-percentage">
                {{ genderPercentage }}%
              </text>
              <text class = "chart-text">● 남성</text>
             </div>
              
               <div class="donut-chart">
            
            <svg viewBox="0 0 200 200" width="640" height="640">
              
              <!-- 회색 배경 원 -->
              <circle
                cx="100"
                cy="100"
                r="70"
                fill="none"
                stroke="#D9D9D9"
                stroke-width="20"
              />
              <!-- 검은색 남성 비율 -->
              <circle
                cx="100"
                cy="100"
                r="70"
                fill="none"
                stroke="#000000"
                stroke-width="20"
                :stroke-dasharray="`${genderPercentage * 4.4} 440`"
                stroke-dashoffset="0"
                transform="rotate(-90 100 100)"
                class="donut-segment"
              />

              

             
            </svg>
             
          </div>


              <!-- 오른쪽 라벨 (여성) -->
               <div class="chart-label">
              <text class="chart-percentage">
                {{100- genderPercentage }}%
              </text>
              <text class = "chart-text">● 여성</text>
             </div>
          </div>
          <!-- 도넛 차트 -->
         

          <!-- 연령대별 동그라미 그래프 -->
          <div class="age-statistics">
            <div v-for="(data, index) in ageData" :key="index" class="age-row">
              <span class="age-label">{{ data.label }}</span>
              <div class="circle-dots">
                <span
                  v-for="i in 10"
                  :key="i"
                  class="circle-dot"
                  :class="{ filled: i <= data.filledDots }"
                ></span>
              </div>
              <span class="age-percentage">{{ data.percentage }}%</span>
            </div>
          </div>
        </div>
      </section>

      <!-- 4. QR/프린트 패널 -->
      <section class="panel qr-panel">
        <div class="qr-content">
                      <img :src="titleImage" alt="print title" class="qr-title" />

          <!-- 클립보드 프린트 섹션 -->
          <div class="print-section" @click="handlePrint">
            <div class="clipboard-icon" :class="{ printing: isPrinting }">
              <img :src="clipboardGif" alt="Print" />
            </div>
                                <h3 class="qr-title">지금 바로 프린트하세요!</h3>

            <p v-if="isPrinting" class="print-status">인쇄 중...</p>
            <p v-if="printError" class="print-error">{{ printError }}</p>
          </div>



          <!-- 서울숲 저장 섹션
          <div class="save-section">
            <div class="cursor-icon">
              <img :src="cursorGif" alt="Save" />
            </div>
            <p class="save-label">서울숲으로</p>
          </div> -->
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useUserSelectionStore } from "@/stores/userSelection";
import titleImage from "@/assets/images/print-title.png";


const userSelectionStore = useUserSelectionStore();
const scrollContainerRef = ref(null);
const isPrinting = ref(false);
const printError = ref("");

// 서버에서 받은 결과 데이터 (디폴트 값 포함)
const resultData = computed(() => userSelectionStore.getResult);

// 연령대별 데이터 가공
const ageData = computed(() => {
  const ageLabels = ["10대", "20대", "30대", "40대", "50대", "기타"];
  const surveyList = resultData.value.surveyListByAge || [0, 0, 0, 0, 0, 0];
  const total = Math.max(resultData.value.totalCount, 1);

  return ageLabels.map((label, index) => {
    const count = surveyList[index] || 0;
    const percentage = total > 0 ? Math.round((count / total) * 100) : 0;
    const filledDots = Math.round(percentage / 10); // 10개 중 몇 개를 채울지

    return {
      label,
      count,
      percentage,
      filledDots: Math.min(Math.max(filledDots, 0), 10),
    };
  });
});

// 내 타입의 전체 비율 계산
const myTypePercentage = computed(() => {
  const totalResponses = resultData.value.totalCount || 100;
  const myTypeCount =
    resultData.value.surveyListByAge?.reduce((sum, count) => sum + count, 0) ||
    0;
  return totalResponses > 0
    ? Math.round((myTypeCount / totalResponses) * 100)
    : 0;
});

// 성별 비율 계산
const genderPercentage = computed(() => {
  return 60; // 서버에서 성별 데이터가 오면 수정
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

const clipboardGif = new URL("../assets/images/print.png", import.meta.url)
  .href;
const cursorGif = new URL("../assets/images/cursor.gif", import.meta.url).href;

// 프린트 핸들러
async function handlePrint() {
  if (isPrinting.value) return;

  isPrinting.value = true;
  printError.value = "";

  try {
    console.log("프린트 요청:", resultData.value.typeCode);

    // 로컬 프린터 서버로 요청 (포트 3001)
    const response = await fetch("http://localhost:3001/api/print", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        typeCode: resultData.value.typeCode,
        gender: userSelectionStore.gender,
        age: userSelectionStore.age,
      }),
    });

    if (!response.ok) {
      throw new Error(`프린트 요청 실패: ${response.status}`);
    }

    const result = await response.json();
    console.log("프린트 성공:", result);

    // 성공 피드백
    alert("인쇄가 시작되었습니다! 🖨️");
  } catch (error) {
    console.error("프린트 오류:", error);
    printError.value =
      "인쇄 중 오류가 발생했습니다. 프린터 서버를 확인해주세요.";
    alert("인쇄 실패: " + error.message);
  } finally {
    isPrinting.value = false;
  }
}

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
  background: #e8f4fa;
}

.statistics-content {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.statistics-title {
  font-family: Pretendard;
  font-weight: 700;
  font-size: 50px;
  line-height: 140%;
  text-align: center;
  color: #000;
  margin-bottom: 30px;
}

.statistics-subtitle {
  font-family: Pretendard;
  font-weight: 500;
  font-size: 40px;
  line-height: 140%;
  text-align: center;
  color: #000;
  margin-bottom: 20px;
}

.statistics-detail {
  font-family: Pretendard;
  font-weight: 700;
  font-size: 45px;
  line-height: 140%;
  text-align: center;
  color: #000;
  margin-bottom: 60px;
}

.donut-chart-container{
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}

/* 변경 후 */
.donut-chart {
  width: 640px;  /* 250px → 640px */
  height: 640px; /* 250px → 640px */
  margin: 60px auto;
  /* display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center; */
}

.donut-segment {
  transition: stroke-dasharray 1s ease;
}

.chart-label{
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-right: 40px;
}
.chart-label text{
  white-space: nowrap;
}

.age-statistics {
  width: 100%;
  margin: 40px auto 0;
}

.age-row {
  display: flex;
  align-items: center;
  margin-bottom: 25px;
  gap: 20px;
}

.age-label {
  font-family: Pretendard;
  font-weight: 700;
  font-size: 32px;
  width: 80px;
  text-align: left;
  color: #000;
  white-space: nowrap;
}

.circle-dots {
  display: flex;
  gap: 8px;
  flex: 1;
}

.circle-dot {
  width: 94px;
  height: 94px;
  border-radius: 50%;
  border: 2px solid #000;
  background: transparent;
  transition: background-color 0.3s ease;
}

.circle-dot.filled {
  background-color: #000;
}

.age-percentage {
  font-family: Pretendard;
  font-weight: 700;
  font-size: 32px;
  width: 80px;
  text-align: right;
  color: #000;
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

.qr-title{}

.print-section,
.save-section {
  margin: 5vh 0;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.print-section:hover,
.save-section:hover {
  transform: scale(1.05);
}

.print-section:active {
  transform: scale(0.95);
}

.clipboard-icon,
.cursor-icon {
  
}

.clipboard-icon.printing {
  animation: pulse 1s infinite;
}

@keyframes pulse {
  0%,
  100% {
    transform: scale(1);
    border-color: #fff;
  }
  50% {
    transform: scale(1.05);
    border-color: #ff4444;
  }
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

.print-status {
  font-size: clamp(14px, 2vh, 18px);
  color: #ffaa00;
  margin-top: 1vh;
}

.print-error {
  font-size: clamp(12px, 1.8vh, 16px);
  color: #ff4444;
  margin-top: 1vh;
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