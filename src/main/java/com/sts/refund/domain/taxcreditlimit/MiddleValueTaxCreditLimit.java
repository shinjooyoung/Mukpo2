package com.sts.refund.domain.taxcreditlimit;

import org.springframework.stereotype.Component;

/**
 * 세액공제 한도 계산 클래스, 총 급여가 3300만원 초과 7000만원 이하일때
 */
@Component
public class MiddleValueTaxCreditLimit implements TaxCreditLimit{
    @Override
    public int calculateTaxCreditLimit(int totalAmount) {
        int result = (int) (740000- ((totalAmount- 33000000) * 0.008));
        return (result < 600000) ? 600000 : result;
    }

    @Override
    public boolean isSatisIedBy(int totalAmount) {
        if(totalAmount <= 70000000 && totalAmount > 33000000) {
            return true;
        }
        return false;
    }
}
