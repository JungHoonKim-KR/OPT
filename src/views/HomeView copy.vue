<template>
  <div class="landing-container">
    <!-- 1. 로고가 표시될 헤더 영역 -->
    <header class="header">
      <img :src="logoImage" alt="OPT Logo" class="logo" />
    </header>

    <!-- 2. 캐릭터 목록과 구분선을 모두 감싸는 메인 영역 -->
    <main class="character-section-wrapper">
      <!-- 
        v-for를 사용하여 4개씩 묶인 characterRows 배열을 반복합니다.
        한 번 반복할 때마다 캐릭터 한 줄과 구분선 한 줄이 생성됩니다.
      -->
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
            <img :src="char.isHovered ? char.animatedGif : char.staticImage" :alt="'Character ' + char.id" />
          </div>
        </div>

        <!-- 마지막 줄이 아닐 경우에만 구분선 이미지를 추가합니다. -->
        <div v-if="rowIndex < characterRows.length - 1" class="separator-bar-wrapper">
          <img :src="backgroundBarImage" alt="separator" class="separator-bar-img" />
        </div>
      </template>
    </main>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";

// --- 이미지 임포트 ---
// Vite에서는 @/가 src/ 폴더를 가리키는 별칭(alias)입니다.
import logoImage from "@/assets/images/logo.png";
import backgroundBarImage from "@/assets/images/backgroundBar_black.png";

// --- 라우터 설정 ---
const router = useRouter();
function startQuiz() {
  router.push("/quiz-intro");
}

// --- 캐릭터 데이터 ---
// 보내주신 코드와 이미지 파일 이름에 맞춰 경로를 수정했습니다.
const characters = ref([
  {
    id: 1,
    staticImage: "/src/assets/images/1.png",
    animatedGif: "/src/assets/images/character1_animated.gif",
    isHovered: false,
  },
  {
    id: 2,
    staticImage: "/src/assets/images/2.png",
    animatedGif: "/src/assets/images/character2_animated.gif",
    isHovered: false,
  },
  {
    id: 3,
    staticImage: "/src/assets/images/3.png",
    animatedGif: "/src/assets/images/character3_animated.gif",
    isHovered: false,
  },
  {
    id: 4,
    staticImage: "/src/assets/images/4.png",
    animatedGif: "/src/assets/images/character4_animated.gif",
    isHovered: false,
  },
  {
    id: 5,
    staticImage: "/src/assets/images/5.png",
    animatedGif: "/src/assets/images/character5_animated.gif",
    isHovered: false,
  },
  {
    id: 6,
    staticImage: "/src/assets/images/6.png",
    animatedGif: "/src/assets/images/character6_animated.gif",
    isHovered: false,
  },
  {
    id: 7,
    staticImage: "/src/assets/images/7.png",
    animatedGif: "/src/assets/images/character7_animated.gif",
    isHovered: false,
  },
  {
    id: 8,
    staticImage: "/src/assets/images/8.png",
    animatedGif: "/src/assets/images/character8_animated.gif",
    isHovered: false,
  },
  {
    id: 9,
    staticImage: "/src/assets/images/9.png",
    animatedGif: "/src/assets/images/character9_animated.gif",
    isHovered: false,
  },
  {
    id: 10,
    staticImage: "/src/assets/images/10.png",
    animatedGif: "/src/assets/images/character10_animated.gif",
    isHovered: false,
  },
  {
    id: 11,
    staticImage: "/src/assets/images/11.png",
    animatedGif: "/src/assets/images/character11_animated.gif",
    isHovered: false,
  },
  {
    id: 12,
    staticImage: "/src/assets/images/12.png",
    animatedGif: "/src/assets/images/character12_animated.gif",
    isHovered: false,
  },
  {
    id: 13,
    staticImage: "/src/assets/images/13.png",
    animatedGif: "/src/assets/images/character13_animated.gif",
    isHovered: false,
  },
  {
    id: 14,
    staticImage: "/src/assets/images/14.png",
    animatedGif: "/src/assets/images/character14_animated.gif",
    isHovered: false,
  },
  {
    id: 15,
    staticImage: "/src/assets/images/15.png",
    animatedGif: "/src/assets/images/character15_animated.gif",
    isHovered: false,
  },
  {
    id: 16,
    staticImage: "/src/assets/images/16.png",
    animatedGif: "/src/assets/images/character16_animated.gif",
    isHovered: false,
  },
]);

// 16개 캐릭터 배열을 4개씩 묶어서 2차원 배열(rows)로 만들어주는 computed 속성
const characterRows = computed(() => {
  const rows = [];
  // 16번 반복하는데, 한 번에 4칸씩 건너뜁니다.
  for (let i = 0; i < characters.value.length; i += 4) {
    // 원본 배열에서 4개씩 잘라서 rows 배열에 추가합니다.
    rows.push(characters.value.slice(i, i + 4));
  }
  return rows;
});
</script>

<style scoped>
.landing-container {
  min-height: 100vh;
  background: linear-gradient(
    to bottom,
    #000000 0%,
    #000000 30%,
    /* 검은색 영역 30% */ #e9f7ff 30%,
    /* 하늘색 영역 70% */ #e9f7ff 100%
  );
  padding: 2rem;
  box-sizing: border-box;
}

.header {
  height: 30vh; /* 화면 높이의 30% 차지 */
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo {
  max-width: 250px;
  height: auto;
}

/* 캐릭터와 구분선 전체를 감싸는 영역 */
.character-section-wrapper {
  display: flex;
  flex-direction: column; /* 아이템들을 세로로 쌓음 */
  align-items: center; /* 가로 중앙 정렬 */
  gap: 1.5rem; /* 각 줄(캐릭터 행, 구분선) 사이의 간격 */
}

/* 캐릭터 4개를 감싸는 한 줄 */
.character-row {
  display: flex;
  justify-content: space-around; /* 캐릭터들 사이에 동일한 간격 부여 */
  width: 100%;
  max-width: 1200px; /* 전체 컨텐츠의 최대 너비 제한 */
}

.character-item {
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.character-item:hover {
  transform: scale(1.1); /* 마우스를 올리면 살짝 커짐 */
}

.character-item img {
  width: 100%;
  /* 캐릭터 이미지 크기 조절 */
  /* max-width: 150px;  */
  border-radius: 50%;
}

/* 구분선 이미지를 감싸는 영역 */
.separator-bar-wrapper {
  width: 100%;
  max-width: 1200px;
  display: flex;
  justify-content: center;
}

.separator-bar-img {
  width: 100%;
  height: auto;
}
</style>
