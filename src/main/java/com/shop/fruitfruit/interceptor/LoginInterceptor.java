package com.shop.fruitfruit.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 요청이 들어올때 마다 쿠키에서 사용자 정보 확인 후 로그인 상태 유지 로직 짜보기
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    // 쿠키에 유저 정보가 있으면 로그인 처리
                    return true;
                }
            }
        }

        response.sendRedirect("/user/login");
        return false;
    }
}
