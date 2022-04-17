package com.sts.refund.domain.taxcreditlimit;

import org.springframework.stereotype.Component;

@Component
public class ExcessValueTaxCreditLimit implements TaxCreditLimit{
    @Override
    public int calculateTaxCreditLimit(int totalAmount) {
        int result = (int) (660000- ((totalAmount- 70000000) * 0.5));
        return (result < 500000) ? 500000 : result;
    }

    @Override
    public boolean isSatisIedBy(int totalAmount) {
        if(totalAmount > 70000000) {
            return true;
        }
        return false;
    }
}