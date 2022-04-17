package com.sts.refund.domain.taxcreditlimit;

import org.springframework.stereotype.Component;

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
