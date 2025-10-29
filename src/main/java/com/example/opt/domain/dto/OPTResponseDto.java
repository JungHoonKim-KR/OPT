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
    private OPT opt;
    private int totalCount;
    private int totalCountOfType;
    private int percentOfMan;
    private int percentOfWomen;
    private int[]surveyListByAge;
    private OPT bestOPT;
    private OPT worstOPT;
}
