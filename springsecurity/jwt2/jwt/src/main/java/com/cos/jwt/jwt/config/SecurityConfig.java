package com.cos.jwt.jwt.config;

import com.cos.jwt.jwt.config.jwt.JwtAuthenticationFilter;
import com.cos.jwt.jwt.config.jwt.JwtAuthorizationFilter;
import com.cos.jwt.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor    
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(new MyFilter3(), BasicAuthenticationFilter.class); // BasicAuthenticationFilter가 실행되기전에 실행하겠다
        // BasicAuthenticationFilter 말고 SecurityContext
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 안쓰겠다
                .and()
                .addFilter(corsFilter) //@CrossOrigin(인증이 없을 떄 사용), 시큐리티 필터에 등록 (인증이 필요할때, 있을 때 사용)
                .formLogin().disable() // 폼태그 만들어서 로그인 안하겠따
                .httpBasic().disable() // Http basic Auth  기반으로 로그인 인증창이 뜸.  disable 시에 인증창 뜨지 않음. +rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
//                .addFilter(new JwtAuthenticationFilter()) // JwtAuthenticationFilter가 꼭 넘겨줘야하는 파라미터가 있음 => AuthenticationManager을 파라미터로 넣어줘야 함! 왜냐! 이 필터가 로그인을 하는 필터인데, 이 매니저를 통해서 로그인을 진행하기 때문!
                // 근데 이 메소드를 WebSecurityConfigurerAdapter가 가지고 잇씀
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // 근데 이걸 호출만해주면 에러가 남!! -> filter로 가서 메소드 만들어줘야 함
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),userRepository)) // 근데 이걸 호출만해주면 에러가 남!! -> filter로 가서 메소드 만들어줘야 함
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
//                // 원래는 로그인창이 아래처럼 폼로그인으로 함
//                // 근데 위에서 disable 만들어 놨음!
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/loginForm")
                // 직접   PrincipalDetailService (login)을 실행하는 filter을 만들어줄것
                ;
//        super.configure(http);
    }
}
