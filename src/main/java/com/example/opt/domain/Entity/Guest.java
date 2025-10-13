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
@Schema(description = "사용자 정보")
public class Guest {
    @Schema(description = "사용자 임시 식별값, UUID값 부여")
    private String randomNumber;
    @Schema(description = "성별", example = "님/여")
    private String gender = "여";
    @Schema(description = "나이")
    private Integer age = 20;
}
