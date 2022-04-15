package com.sts.refund.web;


import com.sts.refund.domain.User;
import com.sts.refund.web.aes.AES256Util;
import com.sts.refund.web.response.StsResponse;
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

    @PostMapping("/szs/signup")
    public ResponseEntity save(@RequestBody UserDto userDto, HttpServletResponse response) throws Exception {

        userService.save(userDto);
        findScrap();

        StsResponse<UserDto> stsResponse = StsResponse.response(response.getStatus(),"회원가입");

        return new ResponseEntity(stsResponse, HttpStatus.OK);
    }

    @GetMapping("/szs/me")
    public ResponseEntity find(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getAttribute("userId").toString();
        UserDto userDto = userService.findByUserId(userId);

        StsResponse<UserDto> stsResponse = StsResponse.response(response.getStatus(),"내정보");
        return new ResponseEntity(stsResponse, HttpStatus.OK);
    }

    @PostMapping("/szs/scrap")
    public ResponseEntity findScrap() {
        return null;
    }

    Object getScrapData() {
        return null;
    }
}
