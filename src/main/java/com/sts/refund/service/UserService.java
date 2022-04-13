package com.sts.refund.service;

import com.sts.refund.domain.User;
import com.sts.refund.domain.UserRepository;
import com.sts.refund.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public void save(UserDto userDto){
        userRepository.save(userDto.toEntity());
    }

    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);
    }


    public String createToken(UserDto userDto) {
        return "";
    }
}
