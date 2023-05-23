package com.example.jwttutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
//@EnableWebSecurity // 기본 웹 보안을 설정
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests() // httpServiceRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다.
                .requestMatchers("/api/hello").permitAll() // api/hello에 오는 요청은 모두 허용
                .anyRequest().authenticated(); // 그 외의 요청들은 검증을 받겠다

        return http.build();
    }



    // h2-console 하위 모든 요청들과 파비콘 관련 요청은 SpringSecurity 로직을 수행하지 않고도 접속할 수 있게
    // configure 메소드를 오버라이드하여 내용을 추가해줌
//   @Bean
//    public void configure(WebSecurity web) {
//        web
//                .ignoring()
//                .requestMatchers(
//                        "/h2-console/**",
//                        "/favicon.ico"
//                );
//    }
//    @Override
//    @Bean
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests()
//                .requestMatchers("/h2-console")
//                .permitAll();
//    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web
//                .ignoring()
//                .dispatcherTypeMatchers(HttpMethod.valueOf("/h2-console/**"));
//    }
@Bean
public WebSecurityCustomizer configure() {
    return (web) -> web.ignoring().mvcMatchers(
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api/v1/login" // 임시
    );
}

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
//    }
}
