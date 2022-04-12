package com.sts.refund.service;

import com.sts.refund.domain.UserRepository;
import com.sts.refund.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public void save(UserDto userDto){
        userRepository.save(userDto.toEntity());
    }
}
