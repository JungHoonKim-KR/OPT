package com.example.opt.domain.controller;

import com.example.opt.domain.Entity.OPT;
import com.example.opt.domain.dto.OPTRequestDto;
import com.example.opt.domain.dto.OPTResponseDto;
import com.example.opt.domain.service.GuestService;
import com.example.opt.domain.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
    private final GuestService guestService;
    @Operation(summary = "설문 결과 응답", description = "4가지 설문의 응답 데이터를 받고 이에 따른 결과값 반환")
    @PostMapping("/survey")
    public OPTResponseDto survey(@RequestBody OPTRequestDto optRequestDto) {

        OPT opt = questionService.createResult(optRequestDto.getQuestionList());
        guestService.save(optRequestDto.getGender(), optRequestDto.getAge(), opt.getTypeCode());
        int totalCount = guestService.findTotalCount();
        int totalCountOfType = guestService.findTotalCountByType(opt.getTypeCode());
        int percentOfMan = guestService.findPercentOfGender("남", opt.getTypeCode(), totalCountOfType);
        int percentOfWoman = 100 - percentOfMan;

        int[] ageList = guestService.findSurveyListByAge(opt.getTypeCode());
        OPT bestCode = questionService.findByTypeCode(opt.getBestType());
        OPT worstCode = questionService.findByTypeCode(opt.getWorstType());
        return new OPTResponseDto(opt,totalCount,totalCountOfType, percentOfMan, percentOfWoman,ageList, bestCode, worstCode);
    }

}
