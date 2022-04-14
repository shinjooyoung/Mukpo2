package com.sts.refund.service;

import com.sts.refund.domain.User;
import com.sts.refund.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final UserRepository userRepository;

    public Optional<User> findById(long id){
        return userRepository.findById(id);
    }
}
