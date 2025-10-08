package com.example.dmfp.domain.dto;

import com.example.dmfp.domain.Entity.Guest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "사용자의 설문 응답을 받는 DTO")
public class SurveyDto {
    @Schema(description = "사용자 정보")
    private Guest guest;
    @Schema(description = "응답 정보", example = "0,1,0,1")
    private List<Boolean> resultList;
}
