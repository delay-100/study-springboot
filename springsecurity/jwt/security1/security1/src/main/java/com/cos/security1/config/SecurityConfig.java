package com.cos.security1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.cos.security1.config.oauth.PrincipalOauth2UserService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨
                    // 스프링 시큐리티 필터 = SecurityConfig
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true) // secured 어노테이션 활성화, preAuthorize와 postAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    // password 암호화를 bean으로 등록
    // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해줌
//    @Bean
//    public BCryptPasswordEncoder encodePwd() {
//        return new BCryptPasswordEncoder();
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 인증만되면 들어갈 수 있는 주소!!-> 로그아웃상태에서 user 접근하면 login으로 가지는데, login 하면 바로 접속됨
                .antMatchers("/manager/**")
                .access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // 로그인 했지만 ADMIN이나 MANAGER권한
                .antMatchers("/admin/**")
                .access("hasRole('ROLE_ADMIN')")
//                .antMatchers("/admin/**").hasAnyRole("ROLE_ADMIN")
//                .antMatchers("/admin/**").hasRole("ROLE_ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm")
//                .usernameParameter("username2") // 만약 loginForm에 username 변수이름이 username2라면 이렇게 해줘야 함
                .loginProcessingUrl("/login") // login 주소가 호출이 되면 시큐리티가 낚아채서 신 로그인을 진행해줌
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm") // 구글 로그인이 완료된 후의 후처리가 필요함.
        // 1. 코드 받기(구글과 인증됨), 2. 엑세스 토큰 받기(사용자 권한 샹감), 3. 권한으로 사용자프로필 정보를 가져오고 4. 그 정보를 토대로 회원가입을 자동으로 진행시키기도 함.
        //4-2. (이메일, 전화번호, 이름, 아이디) ex. 쇼핑몰 -> (집주소), 백화점몰 -> VIP등급, 일반등급)
        //  oAuth 쓰면구글 로그인이 완료가 되면! 코드가 아니라, (엑세스 토큰+사용자 프로필 저정보를 한번에 받아줌)
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
    }

    // 로그인 실패 원인 파악 코드
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException, ServletException, IOException {
                logger.error("로그인 실패: " + exception.getMessage());
                super.onAuthenticationFailure(request, response, exception);
            }
        };
    }
}

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import com.cos.security1.config.oauth.PrincipalOauth2UserService;
//
//@Configuration // IoC 빈(bean)을 등록
//public class SecurityConfig {
//
//    @Autowired
//    private PrincipalOauth2UserService principalOauth2UserService;
//
//    @Bean
//    public BCryptPasswordEncoder encodePwd() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.authorizeRequests()
//                .antMatchers("/user/**").authenticated()
//                // .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or
//                // hasRole('ROLE_USER')")
//                // .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and
//                // hasRole('ROLE_USER')")
//                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//                .anyRequest().permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/loginForm")
//                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/")
//                .and()
//                .oauth2Login()
//                .loginPage("/loginForm")
//                .userInfoEndpoint()
//                .userService(principalOauth2UserService);
//
//        return http.build();
//    }
//}