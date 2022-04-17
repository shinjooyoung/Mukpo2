package com.sts.refund.web.interceptor;

import com.sts.refund.web.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class LoginAuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        String token = request.getHeader("Authorization");
        if(jwtTokenProvider.validToken(token)){
            String userId = jwtTokenProvider.getUserId(token);
            request.setAttribute("userId", userId);
            return true;
        }

        return false;
    }

}
