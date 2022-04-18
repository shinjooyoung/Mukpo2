package com.sts.refund.web;


import com.sts.refund.service.UserService;
import com.sts.refund.web.dto.UserDto;
import com.sts.refund.web.response.StsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 로그인 처리 컨트롤러
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    /**
     * 로그인 매핑
     * @param userDto 
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/szs/login")
    public ResponseEntity login(@RequestBody UserDto userDto, HttpServletResponse response) throws Exception {

        //검증
        if(!userService.userMatch(userDto.getUserId(), userDto.getPassword())){
            throw new IllegalArgumentException("아이디 또는 비번이 맞지 않습니다.");
        }
        
        //토큰 셋팅
        String token = userService.createToken(userDto.getUserId());

        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("type", "BEARER");

        //토큰 헤더입력
        response.setHeader("Authorization", "Bearer " + token);

        //응답 메시지
        StsResponse<Map<String, String>> stsResponse = StsResponse.response(response.getStatus(), "로그인", map);
        
        return new ResponseEntity(stsResponse, HttpStatus.OK);
    }


}
