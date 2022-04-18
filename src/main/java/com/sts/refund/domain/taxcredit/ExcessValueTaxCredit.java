package com.sts.refund.domain.taxcredit;

import org.springframework.stereotype.Component;

/**
 * 세액공제 계산 클래스, 산출세액이 130만원 초과 일때
 */
@Component
public class ExcessValueTaxCredit implements TaxCredit{

    @Override
    public int calculateTaxCredit(int calculatedAmount) {
        return (int) (715000 + ((calculatedAmount-1300000) * 0.3));
    }

    @Override
    public boolean isSatisIedBy(int calculatedAmount) {
        if(calculatedAmount > 1300000) {
            return true;
        }
        return false;
    }
}
