package com.example.member.service;

import com.example.member.domain.Member;
import com.example.member.repository.MemberRepository;
import org.springframework.context.annotation.Bean;

import java.util.List;


public class MemberService {
    private final MemberRepository memberRepository;

    // memberService에서 MemberRepository의 의존성을 주입함
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    @Bean
    public Long join(Member member) {
        System.out.println();
        System.out.println("<<< MemberService.join 회원가입 서비스 시작 >>>>>> ");
        System.out.println("member.id = "+member.getId());
        System.out.println("member.id = "+member.getName());
        validateDuplicateMember(member);
        memberRepository.save(member); // Cannot resolve symbol 'memberRepository': Bean에 등록이 안돼서 MemberService에서 의존성 주입을 못해서 생긴 오류

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
