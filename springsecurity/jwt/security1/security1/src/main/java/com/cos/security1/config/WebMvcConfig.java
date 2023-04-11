package com.cos.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // 머스태치를 오버라이딩해서 재설정함
        MustacheViewResolver resolver = new MustacheViewResolver();
        resolver.setCharset("UTF-8"); // 기본적으로 UTF8이고
        resolver.setContentType("text/html; charset=UTF-8"); // 기본적으로 HTML 쓸꺼야
        resolver.setPrefix("classpath:/templates/"); // classpath: - 나의 프로젝트 경로
        resolver.setSuffix(".html"); // 머스태치가 .html 파일을 인식하게됨

        registry.viewResolver(resolver); // 레지스터로 viewResolver를 등록해줌
    }
}
