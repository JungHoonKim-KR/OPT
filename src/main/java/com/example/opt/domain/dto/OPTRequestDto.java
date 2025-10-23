package com.example.opt.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OPTRequestDto {
    private String gender;
    private Integer age;
    private List<Integer> questionList;
    @Override
    public String toString() {
        return "OPTRequestDto{" +
                "gender='" + gender + '\'' +
                ", age=" + age +
                ", questionList=" + questionList +
                '}';
    }
}
