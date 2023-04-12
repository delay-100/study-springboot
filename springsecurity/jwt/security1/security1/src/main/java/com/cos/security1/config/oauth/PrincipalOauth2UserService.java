package com.cos.security1.config.oauth;

import com.cos.security1.Repository.UserRepository;
import com.cos.security1.config.CustomBCryptPasswordEncoder;
import com.cos.security1.config.auth.PrincipleDetails;
import com.cos.security1.config.oauth.provider.GoogleUserInfo;
import com.cos.security1.config.oauth.provider.NaverUserInfo;
import com.cos.security1.config.oauth.provider.OAuth2UserInfo;
import com.cos.security1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private CustomBCryptPasswordEncoder customBCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    // 구글로 부터 받은 userRequest 데이터에 대한 후처리 되는 함수
    // 함수 종료 시 @AuthenticationPrincipal 어노테이션이 만들어진다
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest: " + userRequest);
        System.out.println("getClientRegistration: "+ userRequest.getClientRegistration()); // registrationId로 어떤 OAuth로 로그인했는지 확인가능
        System.out.println("getAccessToken: "+ userRequest.getAccessToken().getTokenValue());
        // 구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인을 완료 -> code를 리턴(OAuth-client 라이브러리) -> AccessToken요청
        // 이렇게 받은 userRequest 정보 -> 회원 프로필을 받아야 함(loadUser 함수) -> 회원 프로필 받음
//        System.out.println("getAttributes: "+ super.loadUser(userRequest).getAttributes());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getAttributes: "+ oAuth2User.getAttributes()); //super.loadUser(userRequest)가 oAuth2User와 동일


        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            System.out.println("google login/구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            System.out.println("naver login / 네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
            System.out.println(oAuth2UserInfo);
        }
        else{
            System.out.println("we only google, naver/우리는 구글, 네이버만 지원해용~");
        }

        // 회원가입을 강제로 진행할 것
//        String provider = userRequest.getClientRegistration().getRegistrationId();
//        String providerId = oAuth2User.getAttribute("sub");
//        String username = provider+"_"+providerId; // google_1239103213
//        String password = customBCryptPasswordEncoder.encode("겟인데어");
//        String email = oAuth2User.getAttribute("email");
//        String role = "ROLE_USER";
        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider+"_"+providerId; // google_1239103213
        String password = customBCryptPasswordEncoder.encode("겟인데어");
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";

        System.out.println("provider: "+provider+" providerId: "+providerId+" username: "+username+" password: "+password+" email: "+email);
        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null) {
            System.out.println("oauth login first/ OAuth 로그인이 처음입니다.");
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }
        else{
            System.out.println("oauth login already/ OAuth 로그인을 이미 한 적이 있습니다. 당신은 자동 회원가입이 되어있습니다.");
        }
//        return super.loadUser(userRequest);
        return new PrincipleDetails(userEntity, oAuth2User.getAttributes());
    }
}
