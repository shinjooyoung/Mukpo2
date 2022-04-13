package com.sts.refund.web;


import com.sts.refund.response.StsResponse;
import com.sts.refund.service.UserService;
import com.sts.refund.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/szs/signup")
    public ResponseEntity save(@RequestBody UserDto userDto, HttpServletRequest request, HttpServletResponse response){

        int status = response.getStatus();

        userDto.passwordEncoding(userDto.getPassword());
        userDto.regNoEncoding(userDto.getRegNo());
        userService.save(userDto);

        StsResponse<UserDto> stsResponse = StsResponse.response(status,"회원가입");

        return new ResponseEntity(stsResponse, HttpStatus.OK);
    }

    @GetMapping("/szs/me")
    public ResponseEntity find(){
        return null;
    }
}
