<template>
  <div class="result-container">
    <div class="result-scroll-container" ref="scrollContainerRef">
      <!-- 1. 타입 소개 패널 -->
      <section class="panel type-intro-panel" v-if="resultData">
        <div class="type-intro-content">
          <p class="intro-text">{{ resultData.typeName }}</p>

          <!-- 타입 이미지 -->
          <img
            :src="getTypeImage(resultData.typeCode)"
            :alt="resultData.typeCode"
          />

          <!-- 캐릭터 GIF -->
          <div class="character-image">
            <img
              :src="getCharacterGif(resultData.typeCode)"
              :alt="resultData.typeCode"
            />
          </div>

          <!-- 해시태그 -->
          <div class="hashtags">
            <div
              v-for="(tag, index) in resultData.hashTags"
              :key="index"
              class="hashtag"
            >
              {{ tag }}
            </div>
          </div>

          <div>{{ resultData.summary }}</div>
          <div class="type-description">{{ resultData.description }}</div>
        </div>
      </section>

      <!-- 2. BEST / WORST MATCH 패널 -->
      <section class="panel match-panel" v-if="resultData">
        <div class="match-content">
          <h3 class="panel-title">BEST / WORST<br />MATCH</h3>

          <div class="match-card-container">
            <!-- BEST -->
            <div class="match-card best-match">
              <div class="match-info">
                <div class="match-character">
                  <div class="match-label">BEST</div>
                  <img
                    :src="getCharacterMatchImage(resultData.bestType)"
                    alt="Best Match"
                  />
                </div>

                <div class="match-details">
                  <img
                    :src="getCharacterMatchTypeImage(resultData.bestType)"
                    alt="Best Match Type"
                  />
                  <p class="match-description">
                    {{ resultData.bestTypeDescription }}
                  </p>
                </div>
              </div>
            </div>

            <!-- WORST -->
            <div class="match-card worst-match">
              <div class="match-info">
                <div class="match-character">
                  <div class="match-label">WORST</div>
                  <img
                    :src="getCharacterMatchImage(resultData.worstType)"
                    alt="Worst Match"
                  />
                </div>

                <div class="match-details">
                  <img
                    :src="getCharacterMatchTypeImage(resultData.worstType)"
                    alt="Worst Match Type"
                  />
                  <p class="match-description">
                    {{ resultData.worstTypeDescription }}
                  </p>
                </div>
              </div>
            </div>
          </div>

          <!-- 추천 루틴 -->
          <div class="routine-text">ROUTINE SUGGESTION</div>
          <div class="routine-suggestion">
            <ul class="routine-list">
              <li v-for="(routine, index) in routineLines" :key="index">
                {{ routine }}
              </li>
            </ul>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRoute } from "vue-router";
import { personaDataMap } from "../stores/qrData.js"; // 데이터 가져오기

const route = useRoute();
const scrollContainerRef = ref(null);

// URL에서 type 파라미터 추출 (예: /qr/NEFA → "NEFA")
const typeCode = route.params.type;

// 해당 타입 데이터 가져오기
const resultData = computed(() => personaDataMap[typeCode]);

// 루틴 텍스트를 줄바꿈 기준으로 분리
const routineLines = computed(() => {
  const routineText = resultData.value?.routines || "";
  return routineText.split("\n").filter((line) => line.trim() !== "");
});

// ===== 이미지 경로 함수들 =====
function getTypeImage(typeCode) {
  try {
    return new URL(
      `../assets/images/typeNames/${typeCode}.png`,
      import.meta.url
    ).href;
  } catch {
    return "";
  }
}

function getCharacterGif(typeCode) {
  try {
    return new URL(
      `../assets/images/characters/${typeCode}.gif`,
      import.meta.url
    ).href;
  } catch {
    return "";
  }
}

function getCharacterMatchImage(typeCode) {
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
  try {
    return new URL(
      `../assets/images/matchCharactersType/${typeCode}.png`,
      import.meta.url
    ).href;
  } catch {
    return "";
  }
}

// ===== 스크롤 애니메이션 =====
onMounted(() => {
  const observerOptions = {
    threshold: 0.5,
    rootMargin: "0px",
  };

  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) entry.target.classList.add("visible");
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

.donut-chart-container {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}

/* 변경 후 */
.donut-chart {
  width: 640px; /* 250px → 640px */
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

.chart-label {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-right: 40px;
}
.chart-label text {
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

.qr-title {
}

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
