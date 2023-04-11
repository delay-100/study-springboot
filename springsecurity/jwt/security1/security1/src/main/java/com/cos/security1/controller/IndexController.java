package com.cos.security1.controller;

import com.cos.security1.Repository.UserRepository;
import com.cos.security1.config.auth.PrincipleDetails;
import com.cos.security1.config.oauth.PrincipalOauth2UserService;
import com.cos.security1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder BCryptPasswordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipleDetails userDetails) {
        System.out.println("/test/login =============");
//        System.out.println("Authentication: "+authentication.getPrincipal()); // Object 타입으로 반환됨

        PrincipleDetails principleDetails = (PrincipleDetails) authentication.getPrincipal();
        System.out.println("authentication: "+principleDetails.getUser());

        System.out.println("userDetails: "+ userDetails.getUsername()); // @AuthenticationPrincipal 어노테이션을 통해 세션 정보에 접근할 수 있음

        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOAuthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) { // DI( 의존성 주입)
        System.out.println("/test/oauth/login =============");
//        System.out.println("Authentication: "+authentication.getPrincipal()); // Object 타입으로 반환됨

        OAuth2User oAuth2User=(OAuth2User) authentication.getPrincipal();
        System.out.println("authentication: "+oAuth2User.getAttributes());
        System.out.println("oauth2User: "+ oauth.getAttributes());
//        System.out.println("userDetails: "+ userDetails.getUsername()); // @AuthenticationPrincipal 어노테이션을 통해 세션 정보에 접근할 수 있음

        return "OAuth2세션 정보 확인하기";
    }
    @GetMapping({"","/"})
    public String index() {
        // 머스테치 기본폴더는 src/main/resources/임
        // 뷰리졸버 설정: templates (prefix), .mustache (suffix) -> 기본 설정 때문에 생략 가능(application.yml 주석)
        return "index"; //src/main/resources/templates/index.mustache
    }

    // 일반 로그인, oauth 로그인을 해도 principaldetails로 처리 가능
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipleDetails principleDetails){
        System.out.println("principalDetails: "+principleDetails.getUser());

        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }


    // 스프링 시큐리티가 해당 주소를 낚아챔
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/login")
    public @ResponseBody String login(){
        return "login";
    }


    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){
        System.out.println(user);
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = BCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
//        user.setId ,,, 등등은 해줄 필요가 없음!!! 자동으로 생성되기 때문
        userRepository.save(user); // 회원가입 잘됨. 비밀번호 :123 => 시큐리티로 로그인을 할 수 없음. 이유는 패스워드가 암호화가 안 되어있음.
        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인 정보";
    }

//    @PostAuthorize() // 잘 안슴
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // data 메소드가 실행되기 직전에 실행
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터정보";
    }


}
