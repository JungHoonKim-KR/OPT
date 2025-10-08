package com.example.dmfp.domain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/result")
@RestController
public class ResultController {
    // 1행
    @GetMapping("/DEFA")
    public String defa() {
        return "안";
    }

    @GetMapping("/DEFP")
    public String defp() {
        return "녕";
    }

    @GetMapping("/DEIA")
    public String deia() {
        return "하";
    }

    @GetMapping("/DEIP")
    public String deip() {
        return "세";
    }

    // 2행
    @GetMapping("/DMFA")
    public String dmfa() {
        return "요";
    }

    @GetMapping("/DMFP")
    public String dmfp() {
        return "프";
    }

    @GetMapping("/DMIA")
    public String dmia() {
        return "로";
    }

    @GetMapping("/DMIP")
    public String dmip() {
        return "젝";
    }

    // 3행
    @GetMapping("/NEFA")
    public String nefa() {
        return "트";
    }

    @GetMapping("/NEFP")
    public String nefp() {
        return "잘";
    }

    @GetMapping("/NEIA")
    public String neia() {
        return "부";
    }

    @GetMapping("/NEIP")
    public String neip() {
        return "탁";
    }

    // 4행
    @GetMapping("/NMFA")
    public String nmfa() {
        return "드";
    }

    @GetMapping("/NMFP")
    public String nmfp() {
        return "립";
    }

    @GetMapping("/NMIA")
    public String nmia() {
        return "니";
    }

    @GetMapping("/NMIP")
    public String nmip() {
        return "다";
    }

}
