package com.sts.refund.domain.taxcreditlimit;

import org.springframework.stereotype.Component;

/**
 * 세액공제 한도 계산 클래스, 총 급여가 3300만원 이하일때
 */
@Component
public class BelowValueTaxCreditLimit implements TaxCreditLimit{
    @Override
    public int calculateTaxCreditLimit(int totalAmount) {
        return 740000;
    }

    @Override
    public boolean isSatisIedBy(int totalAmount) {
        if(totalAmount <= 33000000) {
            return true;
        }
        return false;
    }
}
