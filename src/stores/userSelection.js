import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useUserSelectionStore = defineStore("userSelection", () => {
  // State
  const gender = ref(null);
  const age = ref(null);
  const quizAnswers = ref(null);
  const result = ref(null);

  // Actions
  function setGender(value) {
    gender.value = value;
    console.log("Pinia - 성별 저장됨:", gender.value);
  }

  function setAge(value) {
    age.value = value;
    console.log("Pinia - 연령대 저장됨:", age.value);
  }

  function setQuizAnswers(answers) {
    quizAnswers.value = answers;
    console.log("Pinia - 퀴즈 답변 저장됨:", quizAnswers.value);
  }

  function setResult(data) {
    result.value = data;
    console.log("Pinia - 결과 저장됨:", result.value);
  }

  // Getters - 디폴트 값을 포함한 결과 반환
  const getResult = computed(() => {
    if (result.value) {
      return result.value;
    }

    // 디폴트 값
    return {
      typeCode: "DMFP",
      typeName: "디지털 미니멀리스트",
      hashTags: ["#간결함", "#효율적", "#목적지향", "#정보수집", "#분석적"],
      summary: "온라인에서 필요한 것만 취하는 효율적인 당신",
      description:
        "디지털 공간에서 명확한 목적을 가지고 움직이는 당신은 불필요한 것을 과감히 제거하고 핵심만을 추구합니다.",
      surveyListByAge: [10, 30, 25, 20, 10, 5],
      totalCount: 100,
      bestMatch: {
        typeCode: "DEIP",
        description:
          "데이터와 효율을 중시하며 온라인 활동에서도 목적 지향적인 성향을 보입니다. 함께라면 체계적이고 생산적인 온라인 경험을 만들어갈 수 있습니다.",
      },
      worstMatch: {
        typeCode: "NEIA",
        description:
          "감성과 즉흥성을 중시하는 타입으로 다른 온라인 활동 패턴을 보입니다. 서로의 차이를 이해하고 존중한다면 새로운 시각을 얻을 수 있습니다.",
      },
      routines: [
        "아침에 일어나면 필요한 뉴스레터만 확인하세요.",
        "오후엔 관심있는 주제에 간단한 댓글을 남겨보세요.",
        "저녁에는 오늘 얻은 인사이트 1개를 정리해보세요.",
      ],
    };
  });

  return {
    gender,
    age,
    quizAnswers,
    result,
    setGender,
    setAge,
    setQuizAnswers,
    setResult,
    getResult, // getter 추가
  };
});
