package com.sts.refund.domain;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class EarnedIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private long calculatedAmount;

    @NotNull
    private long totalAmount;

    @OneToOne(mappedBy = "user")
    private User user;


    /**
     * 환급액 계산
     * @param taxCreditLimit 세액공제 한도계산
     * @param taxCredit 세액공제 계산
     * @return*/
    private long calculateRefundAmount(TaxCreditLimit taxCreditLimit, TaxCredit taxCredit) {
        return Math.min(taxCreditLimit.calculateTaxCreditLimit(totalAmount),taxCredit.calculateTaxCredit(calculatedAmount));
    }

}
