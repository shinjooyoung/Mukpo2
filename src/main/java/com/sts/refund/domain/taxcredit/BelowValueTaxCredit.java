package com.sts.refund.domain.taxcredit;

import org.springframework.stereotype.Component;

/**
 * 세액공제 계산 클래스, 산출세액이 130만원 이하일때
 */
@Component
public class BelowValueTaxCredit implements TaxCredit{
    @Override
    public int calculateTaxCredit(int calculatedAmount) {
        return (int) (calculatedAmount * 0.55);
    }

    @Override
    public boolean isSatisIedBy(int calculatedAmount) {
        if(calculatedAmount <= 1300000) {
            return true;
        }
        return false;
    }
}
