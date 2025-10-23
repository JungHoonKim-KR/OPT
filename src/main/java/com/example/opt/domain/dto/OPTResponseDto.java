package com.example.opt.domain.dto;

import com.example.opt.domain.Entity.OPT;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OPTResponseDto {
    private String typeCode;
    private String typeName;
    private List<String> hashTags;
    private String summary;
    private String description;
    private int totalCount;
    private List<Integer>surveyListByAge;
    // OPT 엔티티를 받아서 DTO를 생성하는 생성자
    public OPTResponseDto(OPT optEntity, int totalCount, List<Integer> surveyListByAge) {
        this.typeCode = optEntity.getTypeCode();
        this.typeName = optEntity.getTypeName();
        this.hashTags = optEntity.getHashTags();
        this.summary = optEntity.getSummary();
        this.description = optEntity.getDescription();
        this.totalCount = totalCount;
        this.surveyListByAge = surveyListByAge;
    }
}
