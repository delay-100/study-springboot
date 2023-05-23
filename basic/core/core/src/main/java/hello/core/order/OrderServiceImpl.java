package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OrderServiceImpl implements OrderService{

//    private final MemberRepository memberRepository = new MemoryMemberRepository(); // 멤버 찾으려고 가져옴
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); // 고정 할인 정책
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
      // final 은 무조건 값이 할당되어야 함
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy; // DIP를 지킴! 근데 null을 가리키는 중..


    @Autowired // 자동으로 memberRepository, discountPolicy를 주입해줌!! - 생성자 주입
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
          Member member = memberRepository.findById(memberId);
          int discountPrice = discountPolicy.discount(member, itemPrice);
            return new Order(memberId, itemName, itemPrice,discountPrice);
        }
}
