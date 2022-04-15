package com.sts.refund.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;


/**
 *
 */
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String userId;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @Column(unique = true)
    private String regNo;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "aggregateIncomeTax_id")
    private EarnedIncome aggregateIncomeTax;


    @Builder
    public User(long id, String userId, String password, String name, String regNo){
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
    }



}
