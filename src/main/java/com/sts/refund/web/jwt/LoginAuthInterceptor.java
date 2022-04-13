package com.sts.refund.web.jwt;

import com.sts.refund.domain.UserRepository;
import com.sts.refund.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class LoginAuthInterceptor implements HandlerInterceptor {

//    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 {}", requestURI);

        String token = request.getHeader("token");

        if(token != null) {
//            userRepository.fin
        }

        return false;
    }
}
