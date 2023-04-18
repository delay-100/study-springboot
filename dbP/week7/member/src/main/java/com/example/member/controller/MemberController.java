package com.example.member.controller;

import com.example.member.domain.Member;
import com.example.member.dto.MemberForm;
import com.example.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired // 생성자 함수를 이용한 의존성 주입
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
    
    @GetMapping(value="/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping(value="/members/new")
    public String create(MemberForm form){
        System.out.println("** 회원가입 폼 실행 시작 **");
        System.out.println("입력 값 = "+form.getName());
        Member member = new Member();
        member.setName(form.getName());
        System.out.println("member.id= "+member.getId());
        System.out.println("member.name= "+member.getName());
        memberService.join(member); // 회원가입 로직 처리 함수 - MemberService 클래스 만든 후, 스프링 컨테이너에 Bean으로 등록하고, 의존성 주입을 해야 오류가 사라짐
        return "redirect:/";
    }
}
