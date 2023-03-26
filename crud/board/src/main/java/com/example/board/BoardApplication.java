package com.example.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;

//  auditing: Spring Data JPA에서 시간에 대해서 자동으로 값을 넣어주는 기능
@EnableJpaAuditing
@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}
	
	//  HiddenHttpMethodFilter를 Bean으로 등록하여, @PutMapping과 @DeleteMapping이작동할 수 있도록 해줌
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}
}