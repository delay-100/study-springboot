package com.cos.jwt.jwt.controller;

import com.cos.jwt.jwt.model.User;
import com.cos.jwt.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin // 인증이 필요하지 않은 요청은 이렇게 걸어도 됨
@RequiredArgsConstructor
@RestController
public class RestApiController {

//    @Autowired
    private final UserRepository userRepository;
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//@Autowired
private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @GetMapping("home")
    public String home() {
        return "<h1>home</h1>";
    }

    @GetMapping("")
    public String index() { return "";}

    @PostMapping("token")
    public String token() {
        return "<h1>token</h1>";
    }

    @PostMapping("join")
    public String join(@RequestBody User user ){
        user.setUsername(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return "회원가입완료";
    }


    @GetMapping("/login")
    public @ResponseBody String login() {

        return "login";
    }
    @PostMapping("/login")
    public String login(String username, String password) {
        return "redirect:/";
    }

    
    // user 권한만 접근 가능
    @GetMapping("api/v1/user")
    public String user() {
        return "user";
    }

    // manager, admin 권한만 접근 가능
    @GetMapping("api/v1/manager")
    public String manager() {
        return "manager";
    }
    
    // admin 권한만 접근 가능
    @GetMapping("api/v1/admin")
    public String admin() {
        return "admin";
    }
}
