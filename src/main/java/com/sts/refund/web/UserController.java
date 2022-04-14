package com.sts.refund.web;


import com.sts.refund.web.aes.AES256Util;
import com.sts.refund.web.response.StsResponse;
import com.sts.refund.service.UserService;
import com.sts.refund.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.jcajce.provider.symmetric.AES;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AES256Util aes256Util;

    @PostMapping("/szs/signup")
    public ResponseEntity save(@RequestBody UserDto userDto, HttpServletResponse response) throws Exception {

        int status = response.getStatus();

        userDto.passwordEncoding(passwordEncoder.encode(userDto.getPassword()));
        userDto.regNoEncoding(aes256Util.encrypt(userDto.getRegNo()));
        userService.save(userDto);

        StsResponse<UserDto> stsResponse = StsResponse.response(status,"회원가입");

        return new ResponseEntity(stsResponse, HttpStatus.OK);
    }

    @GetMapping("/szs/me")
    public ResponseEntity find(){

        return null;
    }
}
