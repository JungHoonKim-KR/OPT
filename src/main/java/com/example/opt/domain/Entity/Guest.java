package com.example.opt.domain.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Schema(description = "사용자 정보")
public class Guest {
    @Id
    @Schema(description = "사용자 임시 식별값, UUID값 부여")
    private String randomNumber;
    @Schema(description = "성별", example = "님/여")
    private String gender = "여";
    @Schema(description = "나이")
    private Integer age = 20;
    @Schema(description = "opt 타입")
    private String typeCode;

    public Guest(String gender, int age, String typeCode) {
        this.randomNumber = UUID.randomUUID().toString();
        this.gender = gender;
        this.age = age;
        this.typeCode = typeCode;
    }
}
