package com.sts.refund.domain;

import com.sun.istack.NotNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

public class AggregateIncomeTax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private long taxAmountCalculated;

    @NotNull
    private long totalAmount;

    @OneToOne(mappedBy = "user")
    private User user;
}
