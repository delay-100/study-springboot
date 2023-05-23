package hello.core.member;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository ;

    @Autowired // 생성자에 Autowired 를 써주면 자동으로 생성자 주입됨(ComponentScan을 쓰면 spring이 의존관계를 모르니까 autowired로 써줌)
    // 생성자가 하나인 경우에만 해당
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);

    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
