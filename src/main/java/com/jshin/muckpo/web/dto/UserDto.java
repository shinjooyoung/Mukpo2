package com.jshin.muckpo.web.dto;

import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jshin.muckpo.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 회원정보 DTO
 */

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

    private menuDto earnedIncomeDto;

    @Builder
    public UserDto(String userId, String password, String name, String regNo, menuDto earnedIncomeDto, long userNo){
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
        this.earnedIncomeDto = earnedIncomeDto;
        this.userNo = userNo;
    }

    /**
     * User Entity를 UserDto로 변환
     * @param user 
     * @return
     */
    public static UserDto of(User user){
        return builder()
                .userId(user.getUserId())
                .name(user.getName())
                .regNo(user.getRegNo())
                .earnedIncomeDto(user.getEarnedIncome() != null ? menuDto.of(user.getEarnedIncome()): null)
                .userNo(user.getId())
                .build();
    }

    /**
     * User Entity 변환
     * @return 
     */
    public User toEntity(){
        return User.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .regNo(regNo)
                .build();
    }

    public void setEarnedIncomeDto(menuDto earnedIncomeDto){
        this.earnedIncomeDto = earnedIncomeDto;
    }

    /**
     * 비밀번호 암호화
     * @param password
     */
    public void passwordEncoding(String password){
        this.password = password;
    }

    public void setRegNo(String regNo){
        this.regNo = regNo;
    }

    /**
     * 가입 가능 유저 검증
     * @return
     */
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
