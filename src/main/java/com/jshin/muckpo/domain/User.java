package com.jshin.muckpo.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 회원 도메인 클래스
 */
@NoArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
    private long id;

    @Column(unique = true)
    private String userId;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @Column(unique = true)
    private String regNo;



    @Builder
    public User(long id, String userId, String password, String name, String regNo){
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
    }

}
