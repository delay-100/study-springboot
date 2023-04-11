package com.cos.jwt.jwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.jwt.config.auth.PrincipalDetails;
import com.cos.jwt.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


// 스프링 시큐리티에 UsernamePasswordAuthenticationFilter가 있음
// /login 요청해서 username, password 전송하면(post)
// UsernamePasswordAuthenticationFilter 가 동작을 함
// 근데 지금은 formlogin.disable 해놔서 기존거가 동작을 안함!
// 다시 등록을 해주면 됨! 이거를 .addFilter(new JwtAuthenticationFilter())


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    // 넘어온 파라미터 받기
    private final AuthenticationManager authenticationManager;

    // login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter: 로그인 시도중");
       // 1. username, password 받아서
       // 2. 정상인지 로그인 시도를 해보는거에요.
        // authenticationManager로 로그인 시도를 하면 !! PrincipalDetailsService가 호출이 됨. 그럼 loadUserByUsername이게 자동실행됨
       //3. PrincipalDetails를 세션에 담고 ==> 이걸 세션에 안담으면 권한관리가 안됨!!, 권한 관리를 위해 세션에 담는것
        // 4. jwt 토큰을 만들어서 응답해주면 됨.


        // ---------------------------


            // 1. username, password 받아서

//            System.out.println(request.getInputStream().toString());

            // 일반 java 방식 + x-www.form.urlencoded
            // 결과가 이렇게 옴 username=ssar&password=1234

//            BufferedReader

            // json 데이터로 파싱하기 - ObjectMapper 이용
            // { "username"="ssar","password"="1234"}
            ObjectMapper om = new ObjectMapper();
        User user = null;
        try {
            user = om.readValue(request.getInputStream(), User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(user);  // 결과: User(id=0, username=ssar, password=1234, roles=null)

            // 2. 정상인지 로그인 시도를 해보는거에요.
            // authenticationManager로 로그인 시도를 하면 !! PrincipalDetailsService가 호출이 됨. 그럼 loadUserByUsername이게 자동실행됨
        // 위 함수가 실행된 후 정상이면 authentication이 리턴됨 => DB에 있는 username과 password가 일치한다.
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
            // authentication에는 나의 로그인 정보가 담겨짐
            Authentication authentication = authenticationManager.authenticate(authenticationToken);   // 이게 실행될 때 PrincipalDetailsService의 loadUserByUsername() 함수가 실행됨!!
            // authentication 객체가 session 영역에 저장됨 => getUsername이 되었다는 것은 로그인이 되었다는뜻
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("로그인 완료됨: " +principalDetails.getUser().getUsername()); // username이 나온다는거는 로그인이 정상적으로 되었다는 것
            // authentication 객체가 session영역에 저장해야하고 그 방법이 return임
            // 리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하려고 하는 것
        // 굳이 jwt 토큰을 사용하면서 세션을 만들 이유가 없음 근데 단지 권한처리때문에 session을 넣어줌

         return authentication;
//        System.out.println("=====================");

    }

    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행됨
    // 여기서 jwt 토큰을 만들어서 request 요청한 사용자에게 jwt 토큰을 response 해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
       System.out.println("successfulAuthentication실행됨: 인증이 완료되었음!");
        
       // jwt 토큰 만들기! - 라이브러리 이용

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        // RSA 방식은 아니고 Hash 암호방식
        String jwtToken = JWT.create()
                .withSubject("cos토큰")
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10))) // 토큰 유효 시간(10000:10초, 60000*10: 10분)    , 다른 사람에게 탈취됐을 때 일정 시간 지나면 쓸 수 없게 만듦
                .withClaim("id",principalDetails.getUser().getId()) // 내가 넣고싶은 key, value 값 넣는것 ,, 토큰에
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512("cos"));
        // 응답 값
        response.addHeader("Authorization", "Bearer " + jwtToken);
        System.out.println("===============================");
        System.out.println(response.getHeader("Authorization"));
        System.out.println("===============================");
//        try {
//            String encodedToken = URLEncoder.encode(jwtToken, StandardCharsets.UTF_8.toString());
//            System.out.println(encodedToken);
//            response.addHeader("Authorization", "Bearer " + encodedToken);
//        } catch (UnsupportedEncodingException ex) {
//            // 예외 처리 코드
//        }
        System.out.println("token 값:"+ jwtToken);
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
