package com.example.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 정적 리소스 (예: 이미지, CSS 파일 등)의 경로를 설정합니다.
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/static/resources/");
    }

    // 추가적인 웹 설정 (예: 인터셉터, 포맷터 등)도 이곳에서 정의할 수 있습니다.
}