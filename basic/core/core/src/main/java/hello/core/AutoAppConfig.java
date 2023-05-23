package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration // 설정정보
@ComponentScan ( // 자동으로 스프링 빈을 끌어오는거
        basePackages = "hello.core.member", // 탐색할 패키지의 시작 위치를 지정한다. 이 패키지를 포함해서 하위 패키지를 모두 탐색한다 -> hello.core.member부터 찾아보면 시간이 오래걸림!!
        basePackageClasses = AutoAppConfig.class,
        // 예제를 안전하게 유지하기위헤 뺄려고 쓰는 문장
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION,classes = Configuration.class)// 다른 예제코드들을 남기기 위해 Configuration클래스를 스캔대상에서 제외함
) // @Component 가 붙은 애들만 자동으로 빈에 등록!!+ @Autowired로 의존관계 자동주입시킴
public class AutoAppConfig {

    // 수동 빈 등록 (자동과 겹치게 등록한 경우)
    // The bean 'memoryMemberRepository', defined in class path resource [hello/core/AutoAppConfig.class], could not be registered. A bean with that name has already been defined in file [D:\git\study-springboot\basic\core\core\out\production\classes\hello\core\member\MemoryMemberRepository.class] and overriding is disabled.
    // 위의 오류 발생
    @Bean(name="memoryMemberRepository")
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

}
