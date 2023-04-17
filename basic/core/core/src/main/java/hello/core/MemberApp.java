package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
        // 일반 java 코드
//        AppConfig appConfig = new AppConfig();
//
////        MemberService memberService = new MemberServiceImpl();
//        MemberService memberService = appConfig.memberService();

        // 스프링 컨테이너 - ApplicationContext
        ApplicationContext applicationContext = new AnnotationConfigReactiveWebApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class); // 메소드이름, 타입
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member= "+member.getName());
        System.out.println("find Member= "+findMember.getName());
    }
}
