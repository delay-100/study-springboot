package com.cos.security1.auth;

// 시큐리티가 /login을 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 세션 공간은 똑같은데! 시큐리티가 가지고있는 session을 만들어줍니다. (Security ContextHolder 라는 키값에 세션 정보를 저장시킴)
// 시큐리티의 세션에 들어갈 수 있는 오브젝트 타입 => Authentication 타입 객체
// Authentication 안에는 User 정보가 있어야 됨.
// User 오브젝트 타입은 UserDetails 타입 객체임

import com.cos.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// => Security Session 에는 => Authentication 객체가 들어가야하고 => 그거는 UserDetail 타입이어야 함
// 이 클래스가 UserDetails를 implements 하는거임!
public class PrincipleDetails implements UserDetails {

    private User user; // 콤포지션

    // 생성자
    public PrincipleDetails(User user) {
        this.user = user;
    }

    // 해당 User의 권한을 리턴하는 곳!!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        user.getRole(); //이렇게하면 해당 User의 권한이 나오지만 타입을 맞춰야 함!
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 오래사용한건 아닌지? 물어보는거
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화가 되어있는지?
    @Override
    public boolean isEnabled() {
        // 우리 사이트에서 1년동안 회원이 로그인을 안하면 휴면 계정으로 하기로 함.
        // user model에 TimeStamp에서 loginDate가 있어야 함 -> 로그인 할 때마다 시간 적음
//        user.getLoginDate() 해서
        // 현재시간 - 로그인시간 => 1년 초과하면 return false하면 됨
        return true;
    }
}
