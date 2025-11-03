<template>
  <div class="result-container">
    <div
      class="result-scroll-container"
      ref="scrollContainerRef"
      :style="{ '--bg-color': resultData?.backgroundColor }"
    >
      <!-- 1️⃣ 타입 소개 패널 -->
      <section class="panel type-intro-panel" v-if="resultData">
        <div class="type-intro-content">
          <p class="intro-text">{{ resultData.typeName }}</p>

          <!-- 타입 이미지 -->
          <img
            :src="getTypeImage(resultData.typeCode)"
            :alt="resultData.typeCode"
            class="type-image"
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

          <div class="type-description">
            <div class="sumarry-description">{{ resultData.summary }}</div>

            {{ resultData.description }}
          </div>

        </div>
      </section>

      <!-- 2️⃣ BEST / WORST MATCH -->
      <section class="panel match-panel" v-if="resultData">
        <div class="match-content">
          <h3 class="panel-title">BEST / WORST<br />MATCH</h3>

          <div class="match-card-container">
            <!-- BEST -->
            <div class="match-card">
              <div>
                <div class="match-label">BEST</div>
                <img
                  :src="getCharacterMatchImage(resultData.bestType)"
                  alt="Best Match"
                />
              </div>

              <p class="match-description">
                {{ resultData.bestTypeDescription }}
              </p>
            </div>

            <!-- WORST -->
            <div class="match-card">
              <div>
                <div class="match-label">WORST</div>
                <img
                  :src="getCharacterMatchImage(resultData.worstType)"
                  alt="Worst Match"
                />
              </div>

              <p class="match-description">
                {{ resultData.worstTypeDescription }}
              </p>
            </div>
          </div>

          <!-- 추천 루틴 -->
          <div class="routine-text">ROUTINE SUGGESTION</div>
          <ul class="routine-list">
            <li v-for="(routine, index) in routineLines" :key="index">
              {{ routine }}
            </li>
          </ul>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick } from "vue";
import { useRoute } from "vue-router";
import { personaDataMap } from "../stores/qrData.js";

const route = useRoute();
const scrollContainerRef = ref(null);

const typeCode = route.params.type;
const resultData = computed(() => personaDataMap[typeCode]);

const routineLines = computed(() => {
  const text = resultData.value?.routines || "";
  return text.split("\n").filter((line) => line.trim() !== "");
});

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

// ===== 스크롤 애니메이션: IntersectionObserver를 내부 스크롤 컨테이너를 root로 사용 =====
onMounted(async () => {
  // scrollContainerRef가 마운트되고 렌더링된 뒤에 observer를 설정
  await nextTick();

  const container = scrollContainerRef.value;
  if (!container) return;

  const observerOptions = {
    root: container, // <<--- 핵심: 내부 스크롤 컨테이너를 루트로 지정
    threshold: 0.45, // 패널이 약 45% 이상 보이면 활성화
    rootMargin: "0px",
  };

  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      const el = entry.target;
      if (entry.isIntersecting) {
        el.classList.add("visible");
      } else {
        // 원하는 경우 비활성 시 visible을 제거 (다시 애니 재생 원치 않으면 주석처리)
        el.classList.remove("visible");
      }
    });
  }, observerOptions);

  const panels = container.querySelectorAll(".panel");
  panels.forEach((panel) => observer.observe(panel));

  // cleanup: 컴포넌트 언마운트시 옵저버 해제 (선택적)
  // onBeforeUnmount(() => panels.forEach(p => observer.unobserve(p)));
});
</script>

<style scoped>
/* 전체 레이아웃 */
.result-container {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  position: relative;
  display: flex;
  flex-direction: column;
}

.result-scroll-container {
  width: 100%;
  height: 100vh; /* 뷰포트 높이로 고정 (패널 스냅이 정확해짐) */
  overflow-y: auto;
  scroll-snap-type: y mandatory;
  scroll-behavior: smooth;
  -webkit-overflow-scrolling: touch;
  scroll-padding-top: 0;
}

/* 패널 공통: 화면에 딱 맞게 */
.panel {
  width: 100vw;
  height: 100vh; /* 한 패널 = 한 화면 */
  scroll-snap-align: start;
  scroll-snap-stop: always; /* 스냅 안정화 */
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 8vh 6vw;
  box-sizing: border-box;

  /* 애니메이션 초기 스타일 */
  opacity: 0;
  transform: translateY(30px);
  transition: opacity 0.8s ease, transform 0.8s ease;
  will-change: opacity, transform; /* 성능 향상 */
}

/* 보일 때 동작 (fade + lift) */
.panel.visible {
  opacity: 1;
  transform: translateY(0);
}

/* 1️⃣ 타입 소개 패널 */
.type-intro-panel {
  background: #101010;
  color: white;
  text-align: center;
}
.type-image {
  width: 50vw;
  max-width: 150px;
  margin: 2vh 0;
  object-fit: contain;
}
.character-image img {
  width: 50vw;
  max-width: 350px;
  height: auto;
  object-fit: contain;
  margin-top: 2vh;
}
.intro-text {
  font-size: clamp(1.5rem, 5vw, 2.5rem);
  color: var(--bg-color); /* 🔴 수정: 받아온 색상 사용 */
  font-weight: 700;
  margin-bottom: 1vh;
}
.hashtags {
  display: flex;
  flex-wrap: wrap;
  gap: 3vw;
  justify-content: center;
  margin: 2vh 0;
}
.hashtag {
  padding: 1.2vh 4vw;
  border: 2px solid #fff;
  border-radius: 50px;
  font-size: clamp(0.9rem, 3vw, 1.2rem);
  color: var(--bg-color); /* 🔴 수정: 받아온 색상 사용 */
  line-height: 40%;
}

.sumarry-description {
  border-radius: 50px;
  background-color: var(--bg-color);
  color: #ffffff;
  font-family: Pretendard;
  font-weight: 800;
  font-style: ExtraBold;
  font-size: 10px;
  line-height: 40%;
  letter-spacing: 0.25px;
  text-align: center;
  padding: 10px;
  width: 80%;
}
.summary {
  font-size: 10px;
  line-height: 40%;
}
.type-description {
  font-size: clamp(0.5rem, 2.0vw, 1rem);
  line-height: 160%;
  color: var(--bg-color); 
  margin-top: 3vh;
  display: flex;
  border-radius: 72.51px;
  background: transparent;
  font-family: Pretendard;
  font-style: SemiBold;
  letter-spacing: 0.25px;
  text-align: center;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border: 2px solid #fff;
  cursor: pointer;
  transition: all 0.3s ease;

}

/* 2️⃣ 매치 패널 */
.match-panel {
  background: var(--bg-color); /* 🔴 수정: 받아온 색상 사용 */
  color: #fff;
  text-align: center;
}
.panel-title {
  font-size: clamp(0.8rem, 4vw, 2.5rem);
  margin-bottom: 4vh;
  font-weight: 800;
  line-height: 100%;
}
.match-card-container {
  display: flex;
  flex-direction: column;
  gap: 6vh;
}
.match-card {
  display: flex;
  border: 2px solid #fff;
  border-radius: 40px;
  padding: 3vh 4vw;
  align-items: center;
}
.match-label {
  font-size: clamp(0.8rem, 2vw, 2rem);
  font-weight: bold;
  margin-bottom: 2vh;
  line-height: 50%;
  text-align: start;
}
.match-card img {
  width: 30vw;
  max-width: 300px;
  height: auto;
  object-fit: contain;
}
.match-description {
  font-size: clamp(0.6rem, 2vw, 1.3rem);
  line-height: 150%;
  margin-top: 1.5vh;
}

/* 루틴 */
.routine-text {
  margin-top: 3vh;
  font-size: clamp(0.8rem, 2vw, 2rem);
  font-weight: 800;
  line-height: 100%;
}
.routine-list {
  list-style: none;
  padding: 0;
  margin: 2vh 0;
}
.routine-list li {
  font-size: clamp(0.5rem, 3vw, 1.3rem);
  line-height: 160%;
}

/* 스크롤바 숨기기 */
.result-scroll-container::-webkit-scrollbar {
  display: none;
}
.result-scroll-container {
  -ms-overflow-style: none;
  scrollbar-width: none;
}
</style>
