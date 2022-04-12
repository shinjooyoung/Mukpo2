package com.sts.refund.web.dto;

import com.sts.refund.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
//@NoArgsConstructor
@RequiredArgsConstructor
public class UserDto
{
    private String userId;
    private String password;
    private String name;
    private String regNo;

//    @Builder
//    public UserDto(String userId, String password, String name, String regNo){
//        this.userId = userId;
//        this.password = password;
//        this.name = name;
//        this.regNo = regNo;
//    }

    public User toEntity(){
        return User.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .regNo(regNo)
                .build();
    }
}
