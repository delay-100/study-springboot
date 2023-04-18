package com.example.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // controller에 홈("/") 매핑이 있으면 index.html보다 우선함
    public String home() {
        return "home";
    }
}
