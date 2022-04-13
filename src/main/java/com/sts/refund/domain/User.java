package com.sts.refund.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


@Getter
@Entity
public class User {

    private long userNo;
    private String userId;
    private String password;
    private String name;
    private String regNo;

    @Builder
    public User(long userNo, String userId, String password, String name, String regNo){
        this.userNo = userNo;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
    }

}
