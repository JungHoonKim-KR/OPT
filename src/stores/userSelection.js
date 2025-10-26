import { ref } from "vue";
import { defineStore } from "pinia";

export const useUserSelectionStore = defineStore("userSelection", () => {
  // State
  const gender = ref(null);
  const age = ref(null);
  const quizAnswers = ref(null); // [새로 추가됨] 퀴즈 답변 저장
  const result = ref(null); // [새로 추가됨] 서버 결과 저장

  // Actions
  function setGender(value) {
    gender.value = value;
    console.log("Pinia - 성별 저장됨:", gender.value);
  }

  function setAge(value) {
    age.value = value;
    console.log("Pinia - 연령대 저장됨:", age.value);
  }

  // [새로 추가됨] 퀴즈 답변을 저장하는 함수
  function setQuizAnswers(answers) {
    quizAnswers.value = answers;
    console.log("Pinia - 퀴즈 답변 저장됨:", quizAnswers.value);
  }

  // [새로 추가됨] 결과를 저장하는 함수
  function setResult(data) {
    result.value = data;
    console.log("Pinia - 결과 저장됨:", result.value);
  }

  // 반환 목록에 새 state와 actions 추가
  return {
    gender,
    age,
    quizAnswers,
    result,
    setGender,
    setAge,
    setQuizAnswers,
    setResult,
  };
});
