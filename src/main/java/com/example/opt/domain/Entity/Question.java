package com.example.opt.domain.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Schema(description = "질문지")
public class Question {
    @Schema(description = "질문 번호", example = "1번 질문")
    private Integer id;
    @Schema(description = "질문 내용", example = "배고프신가요?")
    private String question;
    @Schema(description = "답변", example = "답변이 두 가지라서 true false로 표현")
    private boolean answer;
}
