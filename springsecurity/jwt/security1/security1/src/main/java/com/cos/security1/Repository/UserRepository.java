package com.cos.security1.Repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// crud 함수를 JpaRepository 가 들고 있음.
// @Repository라는 어노테이션이 없어도 IoC가 됨. 이유는 JpaRepository를 상속했기 떄문에.
// UserRepository는 bean으로 이미 등록되어있음
public interface UserRepository extends JpaRepository<User, Integer> {
    // findBy 까지는 이름 규칙임 -> Useername은 문법
    // select * from user where username = 1?
    public User findByUsername(String username); // JPA query method

//    // select * from user where email =?
//    publlic User findByEmail(String email);

}
