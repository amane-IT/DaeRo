package com.ssafy.daero.interceptor;

import com.ssafy.daero.user.service.JwtService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String jwt = request.getHeader("jwt");
        System.out.println("Interceptor Called");
        if (jwt != null && this.jwtService.isValid(jwt)) {
            System.out.println("Interceptor Passed");
            return true;
        }
        System.out.println("Invalid Request");
        response.setStatus(400);
        return false;
    }
}
