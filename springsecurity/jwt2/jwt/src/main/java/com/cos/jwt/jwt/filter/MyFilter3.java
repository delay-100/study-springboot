package com.cos.jwt.jwt.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter3 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;


//        System.out.println(req.getMethod());
        // 토큰이라는 인증을 만들었따고 가정하고,토큰이름을 "cos"로 설정
        // 토큰이 넘어오면 필터를 넘어가게 하고, 토큰이 없으면 컨트롤러까지도 진입을 못하게 막기
        // => 토큰: cos 이걸 만들어줘야 함. id, pw 정상적으로 들어와서 로그인이 완료가 되면 토큰을 만들어주고 그걸 응답해준다.
        // 요청할 때마다 header에 Authorization에 value 값으로 토큰을 가지고 오겠죠?
        // 그때 토큰이 넘어오면 이 토큰이 내가 만든 토큰이 맞는지만 검증만 하면 됨
        if(req.getMethod().equals("POST")){
            System.out.println("post 요청됨");
            String headerAuth =req.getHeader("" +
                    "");
            System.out.println(headerAuth);

            if(headerAuth.equals("cos")){

                filterChain.doFilter(servletRequest,servletResponse);
            } else {
                PrintWriter out = res.getWriter();
                out.println("인증안됨");
            }
        }

    }
}
