package com.example.opt.domain.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Slf4j
public class QRController {
    @GetMapping("/result/{typeCode}")
    public String showResultPage(@PathVariable String typeCode, Model model) {

        // 1. "typeCode"라는 이름으로 PathVariable 값을 Model에 추가
        //    (JSP의 request.setAttribute("typeCode", typeCode)와 동일)
        model.addAttribute("typeCode", typeCode.toUpperCase());
        log.info(typeCode);
        // 2. templates/result.html 템플릿을 찾아 렌더링
        return "qr";
    }
}