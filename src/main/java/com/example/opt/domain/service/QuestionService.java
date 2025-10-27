package com.example.opt.domain.service;

import com.example.opt.domain.Entity.OPT;
import com.example.opt.domain.respository.OPTRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final OPTRepository questionRepository;
    /**
     * 각 질문 섹터별 매핑 규칙을 저장하는 리스트.
     * 리스트의 인덱스가 질문의 순서를 의미합니다. (0번 인덱스 = 첫 번째 질문)
     */
    private static final List<Map<Integer, String>> MAPPING_SECTORS = new ArrayList<>();

    @PostConstruct
    public void init() {
        // 질문 1번 섹터: 0 -> "D", 1 -> "N"
        MAPPING_SECTORS.add(Map.of(0, "D", 1, "N")); // Time: Day / Night

        // 질문 2번 섹터: 0 -> "M", 1 -> "E"
        MAPPING_SECTORS.add(Map.of(0, "M", 1, "E")); // Style: Minimal / Expressive

        // 질문 3번 섹터: 0 -> "F", 1 -> "I"
        MAPPING_SECTORS.add(Map.of(0, "F", 1, "I")); // Purpose: Fun / Informative

        // 질문 4번 섹터: 0 -> "P", 1 -> "A"
        MAPPING_SECTORS.add(Map.of(0, "P", 1, "A")); // Activity: Passive / Active
    }

    public OPT createResult(List<Integer> answers) {
        String resultCode = createOPTCode(answers);
        return questionRepository.findByTypeCode(resultCode);
    }

    public OPT findByTypeCode(String typeCode){
        return questionRepository.findByTypeCode(typeCode);
    }
    private String createOPTCode(List<Integer> answers) {
        // 입력값 유효성 검사
        if (answers == null || answers.size() != MAPPING_SECTORS.size()) {
            throw new IllegalArgumentException("답변의 개수가 올바르지 않습니다. " + MAPPING_SECTORS.size() + "개가 필요합니다.");
        }

        // StringBuilder를 사용하여 문자열을 효율적으로 조합
        StringBuilder resultCodeBuilder = new StringBuilder();

        for (int i = 0; i < answers.size(); i++) {
            Integer answer = answers.get(i); // 사용자의 i번째 답변 (0 또는 1)
            Map<Integer, String> currentSectorMap = MAPPING_SECTORS.get(i); // i번째 질문의 매핑 규칙

            // 답변(0 또는 1)에 해당하는 문자를 찾음
            String character = currentSectorMap.get(answer);

            // 유효성 검사 (답변이 0 또는 1이 아닐 경우)
            if (character == null) {
                throw new IllegalArgumentException((i + 1) + "번째 답변이 유효하지 않습니다: " + answer);
            }

            resultCodeBuilder.append(character);
        }

        return resultCodeBuilder.toString();
    }
}