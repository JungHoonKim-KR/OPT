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
      opt: {
        typeCode: "DMFP",
        typeName: "은밀한 눈팅러",
        hashTags: ["#맥락감지", "#눈팅장인", "#관찰자모드"],
        summary: "모든걸 보지만 아무것도 남기지 않는 유저",
        description:
          "누구보다 피드를 자주 들여다보지만, 존재는 드래너지 않는다. 콘텐츠를 직접 만들기보단, 타인의 흐름 속에서 자연스럽게 동화된다. 하지만 트렌드와 상황 파악은 누구보다 빠르며, 상화에 따라 은미랗게 반응하는 능력을 갖췄다. 이들은 들은 말보다 맥락을 본다. 피드 속 작은 뉘앙스를 읽고, 사람들의 흐름을 예측하는 감각이 탁월하다. 겉보기엔 조용하지만, 사실상 디지털 감각이 날카로운 유형.",
        surveyListByAge: [10, 30, 25, 20, 10, 5],
        // 'routines' -> 'routineSuggestion'로 키 이름을 변경했습니다.
        routineSuggestion: [
          "📂  저장한 콘텐츠를 정리해보세요. ",
          "🔗  공감한 콘텐츠는 조용히 공유해도 좋아요. ",
          "💬  직접적인 표현을 시도하면, 의외의 연결이 생깁니다.",
        ],
      },
      totalCount: 100,
      // 'bestMatch' -> 'bestOPT'로 키 이름을 변경했습니다.
      bestOPT: {
        typeCode: "DEIP",
        summary: "시각적 감수성과 맥락 읽기가 자연스럽게 맞닿는 조합.",
        description:
          "DMFP와 DEIP는 모두 피드를 관찰하고 정보를 해석하는 능력이 뛰어나며, 과도한 표현보다 맥락을 중요시한다. DEIP는 정돈된 기준으로 콘텐츠를 선별하고, DMFP는 그 안의 분위기와 흐름을 읽는다. 둘 다 주목받지 않는 것을 선호하지만, 서로의 조용한 리듬을 존중하며 안정적인 교류를 이룬다. 말 없이도 공감이 가능하고, 서로의 침묵을 이해할 수 있는 이상적인 밸런스형 관계다.",
      },
      // 'worstMatch' -> 'worstOPT'로 키 이름을 변경했습니다.
      worstOPT: {
        typeCode: "NEIA",
        summary: "감각이 폭발하는 창조형과, 조용히 맥락을 읽는 관찰형의 충돌",
        description:
          "NEIA는 새벽의 에너지로 피드를 적극 활용하며, 감정과 트렌드를 동시에 표현하고자 한다.반면 DMFP는 반응을 절제하고, 표현보다 상황 인지에 집중한다. NEIA는 DMFP의 침묵을 무관심으로 받아들이고, DMFP는 NEIA의 빠른 감정 변화를 부담스럽게 느낀다. 결국 두 사람은 같은 공간에 있어도 서로 다른 주파수로 움직이며, 이해보다는 오해가 쌓이는 조합이다.",
      },
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
