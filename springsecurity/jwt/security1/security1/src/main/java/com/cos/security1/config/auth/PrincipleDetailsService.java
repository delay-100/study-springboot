package com.cos.security1.auth;

import com.cos.security1.Repository.UserRepository;
import com.cos.security1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// 시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
@Service
public class PrincipleDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    // 시큐리티 session => Authentication => UserDetails인데
    // 이 메소드에서  return new PrincipleDetails(userEntity);
    // 이렇게 리턴해주면 자동으로 session(내부 Authentication(내부 UserDetails)) 이렇게 들어가짐
    // 이럼 로그인이 완료가 됨
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username: "+ username);

        User userEntity = userRepository.findByUsername(username); // 가져온 username으로 user가 있는지 확인해야 함 -> 근데 이게 자동으로 없어서 직접 만들어줘야 함
        System.out.println(userEntity);
        // 그래서 repository에서 userRepository에다가 findByUsername 생성!! 그리고 위에 변수 선언
        // 그 후
        if (userEntity != null) {
            return new PrincipleDetails(userEntity);
        }
        return null;
    }
}
