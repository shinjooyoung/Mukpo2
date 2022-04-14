package com.sts.refund.web;


import com.sts.refund.service.UserService;
import com.sts.refund.web.dto.UserDto;
import com.sts.refund.web.response.StsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/szs/login")
    public ResponseEntity login(@RequestBody UserDto userDto, HttpServletResponse response){

        //검증
        userService.userMatch(userDto.getUserId(), userDto.getPassword());
        
        //토큰 셋팅
        String token = userService.createToken(userDto.getUserId());
        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        //응답 메시지
        StsResponse<Map<String, String>> stsResponse = StsResponse.response(response.getStatus(), "로그인", map);
        
        return new ResponseEntity(stsResponse, HttpStatus.OK);
    }


}
