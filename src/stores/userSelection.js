import { ref } from "vue";
import { defineStore } from "pinia";

// 'userSelection'이라는 이름의 저장소(store)를 정의합니다.
export const useUserSelectionStore = defineStore("userSelection", () => {
  // state: 저장하고 싶은 데이터 (ref 사용)
  const gender = ref(null); // 초기값 null
  const age = ref(null); // 초기값 null

  // actions: state를 변경하는 함수들
  function setGender(value) {
    gender.value = value;
    console.log("Gender set to:", gender.value); // 확인용 로그
  }

  function setAge(value) {
    age.value = value;
    console.log("Age set to:", age.value); // 확인용 로그
  }

  // 저장소에서 사용할 state와 actions를 반환합니다.
  return { gender, age, setGender, setAge };
});
