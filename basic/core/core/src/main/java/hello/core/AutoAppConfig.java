package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration // 설정정보
@ComponentScan ( // 자동으로 스프링 빈을 끌어오는거
        // 예제를 안전하게 유지하기위헤 뺄려고 쓰는 문장
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION,classes = Configuration.class)// 다른 예제코드들을 남기기 위해 Configuration클래스를 스캔대상에서 제외함
)
public class AutoAppConfig {
}
