package com.sts.refund.service;

import com.sts.refund.domain.User;
import com.sts.refund.domain.UserRepository;
import com.sts.refund.web.aes.AES256Util;
import com.sts.refund.web.dto.UserDto;
import com.sts.refund.web.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AES256Util aes256Util;

    @Transactional(rollbackFor = Exception.class)
    public void save(UserDto userDto) throws Exception{
        userDto.passwordEncoding(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRegNo(aes256Util.encrypt(userDto.getRegNo()));
        userRepository.save(userDto.toEntity());
    }


    public UserDto findByUserId(String userId) throws Exception {
        UserDto userDto = UserDto.of(userRepository.findByUserId(userId));
        userDto.setRegNo(aes256Util.decrypt(userDto.getPassword()));
        return userDto;
    }

    public boolean userMatch(String userId, String password){
        String encodingPassword = userRepository.findPasswordByUserId(userId);
        return passwordEncoder.matches(password, encodingPassword);
    }


    public String createToken(String userId) {
        return jwtTokenProvider.createToken(userId);
    }
}
