package com.example.dmfp.domain.controller;

import com.example.dmfp.domain.dto.SurveyDto;
import com.example.dmfp.domain.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name="Question APIs", description = "메인 설문 APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
@Slf4j
public class QuestionController {
    private final QuestionService questionService;

    @Operation(summary = "설문 결과 응답", description = "4가지 설문의 응답 데이터를 받고 이에 따른 결과값 반환")
    @PostMapping("/survey")
    public ResponseEntity<?> survey(@RequestBody List<Integer>list) {
        // questionService.run()
        // dummy response
        for(int i =0; i<list.size(); i++){
            log.info("{}번 답변 : {}" , i+1, list.get(i));
        }

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Survey received successfully");
        responseBody.put("status", "OK");
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

}
