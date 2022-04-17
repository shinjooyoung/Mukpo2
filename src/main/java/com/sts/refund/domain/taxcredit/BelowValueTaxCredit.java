package com.sts.refund.domain.taxcredit;

import org.springframework.stereotype.Component;

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
