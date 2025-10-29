package com.example.opt.domain.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class QRController {

    // application.yml에서 주입받는 값
    @Value("${service.api.base-url}")
    private String baseUrl;

    @Value("${service.api.qr-path}")
    private String qrPath;

    /**
     * 최종 리다이렉션 URL을 생성하는 헬퍼 메서드
     * @param mappingName @GetMapping의 경로 이름 (예: "DEFA")
     * @return 전체 리다이렉션 URL (예: http://...:80/qr/DEFA)
     */
    private String createRedirectUrl(String mappingName) {
        return baseUrl + qrPath + mappingName;
    }

    // 1행
    @GetMapping("/DEFA")
    public RedirectView defa() {
        return new RedirectView(createRedirectUrl("DEFA"));
    }

    @GetMapping("/DEFP")
    public RedirectView defp() {
        return new RedirectView(createRedirectUrl("DEFP"));
    }

    @GetMapping("/DEIA")
    public RedirectView deia() {
        return new RedirectView(createRedirectUrl("DEIA"));
    }

    @GetMapping("/DEIP")
    public RedirectView deip() {
        return new RedirectView(createRedirectUrl("DEIP"));
    }

    // 2행
    @GetMapping("/DMFA")
    public RedirectView dmfa() {
        return new RedirectView(createRedirectUrl("DMFA"));
    }

    @GetMapping("/DMFP")
    public RedirectView dmfp() {
        return new RedirectView(createRedirectUrl("DMFP"));
    }

    @GetMapping("/DMIA")
    public RedirectView dmia() {
        return new RedirectView(createRedirectUrl("DMIA"));
    }

    @GetMapping("/DMIP")
    public RedirectView dmip() {
        return new RedirectView(createRedirectUrl("DMIP"));
    }

    // 3행
    @GetMapping("/NEFA")
    public RedirectView nefa() {
        return new RedirectView(createRedirectUrl("NEFA"));
    }

    @GetMapping("/NEFP")
    public RedirectView nefp() {
        return new RedirectView(createRedirectUrl("NEFP"));
    }

    @GetMapping("/NEIA")
    public RedirectView neia() {
        return new RedirectView(createRedirectUrl("NEIA"));
    }

    @GetMapping("/NEIP")
    public RedirectView neip() {
        return new RedirectView(createRedirectUrl("NEIP"));
    }

    // 4행
    @GetMapping("/NMFA")
    public RedirectView nmfa() {
        return new RedirectView(createRedirectUrl("NMFA"));
    }

    @GetMapping("/NMFP")
    public RedirectView nmfp() {
        return new RedirectView(createRedirectUrl("NMFP"));
    }

    @GetMapping("/NMIA")
    public RedirectView nmia() {
        return new RedirectView(createRedirectUrl("NMIA"));
    }

    @GetMapping("/NMIP")
    public RedirectView nmip() {
        return new RedirectView(createRedirectUrl("NMIP"));
    }
}