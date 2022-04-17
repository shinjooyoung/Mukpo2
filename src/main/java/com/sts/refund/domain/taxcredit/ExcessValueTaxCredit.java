package com.sts.refund.domain.taxcredit;

import org.springframework.stereotype.Component;

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
