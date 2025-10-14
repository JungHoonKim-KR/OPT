package com.example.opt.global.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Converter // 이 클래스를 JPA 컨버터로 사용하겠다고 선언
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        // Java의 List<String> 객체를 -> DB의 단일 문자열로 변환
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return attribute.stream().collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        // DB의 단일 문자열을 -> Java의 List<String> 객체로 변환 (현재 에러가 발생하는 부분)
        if (dbData == null || dbData.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(dbData.split(SPLIT_CHAR));
    }
}