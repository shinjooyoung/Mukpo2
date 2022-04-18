package com.jshin.muckpo.web.interceptor;

import com.jshin.muckpo.web.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 로그인 인증 인터셉터 클래스
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 헤더 토큰 확인
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String token = request.getHeader("Authorization");
        log.info("[LoginAuthInterceptor] requestURI={}, token", requestURI, token);

        if(token != null && token.startsWith("Bearer") && jwtTokenProvider.validToken(token.substring(7))){
            String userId = jwtTokenProvider.getUserId(token.substring(7));
            log.info("[LoginAuthInterceptor] userId={}",userId);
            request.setAttribute("userId", userId);
            return true;
        }

        throw new AuthenticationException();
    }

}
