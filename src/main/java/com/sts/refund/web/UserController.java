package com.sts.refund.web;


import com.sts.refund.response.StsResponse;
import com.sts.refund.service.UserService;
import com.sts.refund.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/szs/signup")
    public ResponseEntity save(@RequestBody UserDto userDto, HttpServletRequest request, HttpServletResponse response){
        int status = response.getStatus();
        userService.save(userDto);
        StsResponse<UserDto> stsResponse = StsResponse.response(status,"회원가입");
        return new ResponseEntity(stsResponse, HttpStatus.OK);
    }
}
