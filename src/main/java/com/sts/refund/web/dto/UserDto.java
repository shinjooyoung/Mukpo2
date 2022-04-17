package com.sts.refund.web.dto;

import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sts.refund.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto
{
    private String userId;
    private String password;
    private String name;
    private String regNo;
    private long userNo;

    private EarnedIncomeDto earnedIncomeDto;

    @Builder
    public UserDto(String userId, String password, String name, String regNo, EarnedIncomeDto earnedIncomeDto, long userNo){
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
        this.earnedIncomeDto = earnedIncomeDto;
        this.userNo = userNo;
    }

    public static UserDto of(User user){
        return builder()
                .userId(user.getUserId())
                .name(user.getName())
                .regNo(user.getRegNo())
                .earnedIncomeDto(EarnedIncomeDto.of(user.getEarnedIncome()))
                .userNo(user.getId())
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

    public void setEarnedIncomeDto(EarnedIncomeDto earnedIncomeDto){
        this.earnedIncomeDto = earnedIncomeDto;
    }

    public void passwordEncoding(String password){
        this.password = password;
    }

    public void setRegNo(String regNo){
        this.regNo = regNo;
    }

    @AssertTrue
    public boolean isValidUser() {
        Map<String, String> userMap = new HashMap<>(){{
            put("홍길동","860824-1655068");
            put("김둘리","921108-1582816");
            put("마징가","880601-2455116");
            put("베지터","910411-1656116");
            put("손오공","820326-2715702");
        }};
        if(userMap.containsKey(this.name) && userMap.get(this.name).equals(this.regNo)) {
            return true;
        }
        return false;
    }
}
