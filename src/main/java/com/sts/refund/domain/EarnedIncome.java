package com.sts.refund.domain;

import com.sts.refund.domain.taxcredit.TaxCredit;
import com.sts.refund.domain.taxcreditlimit.TaxCreditLimit;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class EarnedIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private int calculatedAmount;

    @NotNull
    private int totalAmount;

    @OneToOne(mappedBy = "earnedIncome")
    private User user;

    @Builder
    public EarnedIncome(int calculatedAmount, int totalAmount){
        this.calculatedAmount = calculatedAmount;
        this.totalAmount = totalAmount;
    }

    public void setUser(User user) {
        this.user = user;
    }


    /**
     * 환급액 계산
     * @param taxCreditLimit 한도 
     * @param taxCredit 공제액
     * @return
     */
    public int calculateRefundAmount(TaxCredit taxCredit, TaxCreditLimit taxCreditLimit) {
        return Math.min(calculateTaxCredit(taxCredit), calculateTaxCreditLimit(taxCreditLimit));
    }

    /**
     * 세액공제 계산
     * @param taxCredit
     * @return
     */
    public int calculateTaxCredit(TaxCredit taxCredit) {
        return taxCredit.calculateTaxCredit(calculatedAmount);
    }

    /**
     * 세액공제 한도 계산
     * @param taxCreditLimit
     * @return
     */
    public int calculateTaxCreditLimit(TaxCreditLimit taxCreditLimit) {
        return taxCreditLimit.calculateTaxCreditLimit(totalAmount);
    }


}
