package com.sts.refund.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


@Getter
//@Entity
public class Refunds {
    private long limit;
    private long deductible;
    private long refundAmount;
}
