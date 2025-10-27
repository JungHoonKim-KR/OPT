<template>
  <div class="landing-container">
    <!-- 모든 콘텐츠를 감싸는 비율 유지 래퍼 -->
    <div class="aspect-ratio-wrapper">
      <!-- 1. 로고가 표시될 헤더 영역 -->
      <header class="header">
        <img :src="logoImage" alt="OPT Logo" class="logo" />
      </header>

      <!-- 2. 캐릭터 목록과 구분선을 모두 감싸는 메인 영역 -->
      <main class="character-section-wrapper">
        <div class="separator-bar-wrapper">
          <img :src="backgroundBarImage" alt="separator" class="separator-bar-img" />
        </div>
        <template v-for="(row, rowIndex) in characterRows" :key="rowIndex">
          <!-- 캐릭터 한 줄 (4개) -->
          <div class="character-row">
            <div
              v-for="char in row"
              :key="char.id"
              class="character-item"
              @mouseover="char.isHovered = true"
              @mouseleave="char.isHovered = false"
              @click="startQuiz"
            >
              <div class="image-wrapper">
                <img :src="char.isHovered ? char.animatedGif : char.staticImage" :alt="'Character ' + char.id" />
              </div>
            </div>
          </div>

          <div class="separator-bar-wrapper">
            <img :src="backgroundBarImage" alt="separator" class="separator-bar-img" />
          </div>
        </template>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";

// --- 이미지 임포트 ---
import logoImage from "@/assets/images/logo.png";
import backgroundBarImage from "@/assets/images/backgroundBar_black.png";

// --- 라우터 설정 ---
const router = useRouter();
function startQuiz() {
  router.push("/quiz-intro");
}

// --- 캐릭터 데이터 ---
const characters = ref([
  {
    id: 1,
    staticImage: "/src/assets/images/DMFP.png",
    animatedGif: "/src/assets/images/characters/DMFP.gif",
    isHovered: false,
  },
  {
    id: 2,
    staticImage: "/src/assets/images/DMIP.png",
    animatedGif: "/src/assets/images/characters/DMIP.gif",
    isHovered: false,
  },
  {
    id: 3,
    staticImage: "/src/assets/images/NMIP.png",
    animatedGif: "/src/assets/images/characters/NMIP.gif",
    isHovered: false,
  },
  {
    id: 4,
    staticImage: "/src/assets/images/NEIP.png",
    animatedGif: "/src/assets/images/characters/NEIP.gif",
    isHovered: false,
  },
  {
    id: 5,
    staticImage: "/src/assets/images/NMIA.png",
    animatedGif: "/src/assets/images/characters/NMIA.gif",
    isHovered: false,
  },
  {
    id: 6,
    staticImage: "/src/assets/images/DEIA.png",
    animatedGif: "/src/assets/images/characters/DEIA.gif",
    isHovered: false,
  },
  {
    id: 7,
    staticImage: "/src/assets/images/DEIP.png",
    animatedGif: "/src/assets/images/characters/DEIP.gif",
    isHovered: false,
  },
  {
    id: 8,
    staticImage: "/src/assets/images/DMIA.png",
    animatedGif: "/src/assets/images/characters/DMIA.gif",
    isHovered: false,
  },
  {
    id: 9,
    staticImage: "/src/assets/images/DEFA.png",
    animatedGif: "/src/assets/images/characters/DEFA.gif",
    isHovered: false,
  },
  {
    id: 10,
    staticImage: "/src/assets/images/NMFA.png",
    animatedGif: "/src/assets/images/characters/NMFA.gif",
    isHovered: false,
  },
  {
    id: 11,
    staticImage: "/src/assets/images/DEFP.png",
    animatedGif: "/src/assets/images/characters/DEFP.gif",
    isHovered: false,
  },
  {
    id: 12,
    staticImage: "/src/assets/images/DMFA.png",
    animatedGif: "/src/assets/images/characters/DMFA.gif",
    isHovered: false,
  },
  {
    id: 13,
    staticImage: "/src/assets/images/NEFP.png",
    animatedGif: "/src/assets/images/characters/NEFP.gif",
    isHovered: false,
  },
  {
    id: 14,
    staticImage: "/src/assets/images/NEIA.png",
    animatedGif: "/src/assets/images/characters/NEIA.gif",
    isHovered: false,
  },
  {
    id: 15,
    staticImage: "/src/assets/images/NEFA.png",
    animatedGif: "/src/assets/images/characters/NEFA.gif",
    isHovered: false,
  },
  {
    id: 16,
    staticImage: "/src/assets/images/NMFP.png",
    animatedGif: "/src/assets/images/characters/NMFP.gif",
    isHovered: false,
  },
]);

// 16개 캐릭터 배열을 4개씩 묶어서 2차원 배열(rows)로 만들어주는 computed 속성
const characterRows = computed(() => {
  const rows = [];
  for (let i = 0; i < characters.value.length; i += 4) {
    rows.push(characters.value.slice(i, i + 4));
  }
  return rows;
});
</script>

<style scoped>
.landing-container {
  min-height: 100vh;
  width: 100vw;
  background: #000; /* 레터박스 배경색 */
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

/* 비율을 유지하는 핵심 래퍼 */
.aspect-ratio-wrapper {
  position: relative;
  width: calc(100vh * 9 / 16);
  height: 100vh;
  max-width: 100vw;
  max-height: calc(100vw * 16 / 9);
  background: #e9f7ff;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
}

.header {
  background-image: url("../assets/images/homebackground.png");
  background-size: cover;
  height: 30%;
  flex-shrink: 0;
  display: flex;
  align-items: flex-end; /* 로고를 아래로 */
  justify-content: flex-end; /* 로고를 오른쪽으로 */
  box-sizing: border-box;
  /*max값min값 조정하자 아니면 미디어쿼리*/
  padding-right: 5rem;
}

.logo {
  height: 60%;
  width: auto;
}

.character-section-wrapper {
  flex-grow: 1;
  justify-content: space-around;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  box-sizing: border-box;
  /*max값 min 값 조정하자 아니면 미디어쿼리 */
  padding: 10rem 3rem;
}

.character-row {
  display: flex;
  justify-content: space-around;
  width: 90%;
  box-sizing: border-box;
}

.character-item {
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  transition: transform 0.2s ease;
  width: 22%; /* 각 아이템이 한 줄 너비의 22%를 차지 */
}

.character-item:hover {
  transform: scale(1.05);
}

.image-wrapper {
  border-radius: 50%;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%; /* 부모(.character-item)의 너비를 꽉 채움 */
  aspect-ratio: 1 / 1; /* 너비와 높이를 1:1 비율로 유지 (원 모양) */
}

.image-wrapper img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.separator-bar-wrapper {
  width: 90%;
  display: flex;
  justify-content: center;
  box-sizing: border-box;
}

.separator-bar-img {
  width: 100%;
  height: auto;
}
</style>
