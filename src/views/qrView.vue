<template>
  <div
    v-if="typeData"
    class="type-detail"
    :style="{ backgroundColor: typeData.backgroundColor }"
  >
    <h1>{{ typeData.typeName }}</h1>
    <p class="type-code">{{ typeData.typeCode }}</p>

    <div class="hashtags">
      <span v-for="tag in typeData.hashTags" :key="tag" class="tag">{{
        tag
      }}</span>
    </div>

    <p class="summary">{{ typeData.summary }}</p>
    <p class="description" v-html="formattedDescription"></p>
  </div>

  <div v-else class="error">존재하지 않는 유형입니다 😢</div>
</template>

<script setup>
import { useRoute } from "vue-router";
import { computed } from "vue";
import { personaDataMap } from "../stores/qrData.js"; // 상대 경로는 프로젝트 구조에 맞게 수정

const route = useRoute();

// URL에서 type 파라미터 가져오기 (예: /qr/NEFA → "NEFA")
const typeCode = route.params.type;

// QRDATA에서 해당 타입 찾기
const typeData = personaDataMap[typeCode];

// description 안의 줄바꿈을 <br>로 바꿔주기
const formattedDescription = computed(() =>
  typeData ? typeData.description.replace(/\n/g, "<br>") : ""
);
</script>

<style scoped>
.type-detail {
  padding: 24px;
  border-radius: 20px;
  max-width: 600px;
  margin: 40px auto;
  line-height: 1.6;
  font-family: "Pretendard", sans-serif;
}

.type-code {
  opacity: 0.8;
  font-size: 1rem;
  margin-bottom: 16px;
}

.hashtags {
  margin-bottom: 20px;
}

.tag {
  background: rgba(255, 255, 255, 0.3);
  border-radius: 8px;
  padding: 4px 8px;
  margin-right: 6px;
  font-size: 0.9rem;
}

.summary {
  font-weight: 600;
  margin-bottom: 12px;
}

.description {
  white-space: pre-line;
}
.error {
  text-align: center;
  margin-top: 100px;
  font-size: 1.2rem;
  color: #555;
}
</style>
