package com.sts.refund.web;


import com.sts.refund.service.UserService;
import com.sts.refund.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/szs/me")
    public ResponseEntity login(@RequestBody UserDto userDto){

        String token = userService.createToken(userDto);
        return null;
    }




}
