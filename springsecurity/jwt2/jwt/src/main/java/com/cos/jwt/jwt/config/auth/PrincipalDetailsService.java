package com.cos.jwt.jwt.config.auth;


import com.cos.jwt.jwt.model.User;
import com.cos.jwt.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// http://localhost:8080/login 에 들오올때 아래의 코드가 실행됨-> 시큐리티에서 기본적으로 login 하는 url이 /login이라 이게 실행댐
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService의 loadUserByUsername()");
        User userEntity = userRepository.findByUsername(username);
        System.out.println("userEntity: "+ userEntity);
        return new PrincipalDetails(userEntity);
    }
}
