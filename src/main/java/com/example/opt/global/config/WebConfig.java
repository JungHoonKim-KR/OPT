package com.example.opt.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 API 경로에 대해 CORS 허용
                .allowedOrigins("http://ec2-43-201-179-196.ap-northeast-2.compute.amazonaws.com") // ★★★ 프런트엔드 도메인으로 변경
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true) // 인증 정보(쿠키 등) 허용
                .maxAge(3600); // 캐싱 시간 (1시간)
    }
}
