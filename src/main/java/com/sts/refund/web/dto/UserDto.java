package com.sts.refund.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sts.refund.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto
{
    private String userId;
    private String password;
    private String name;
    private String regNo;

    @Builder
    public UserDto(String userId, String password, String name, String regNo, long userNo){
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
    }

    public static UserDto of(User user){
        return builder()
                .userId(user.getUserId())
                .name(user.getName())
                .regNo(user.getRegNo())
                .build();
    }

    public User toEntity(){
        return User.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .regNo(regNo)
                .build();
    }

    public void passwordEncoding(String password){
        this.password = password;
    }

    public void regNoEncoding(String regNo){
        this.regNo = regNo;
    }
}
