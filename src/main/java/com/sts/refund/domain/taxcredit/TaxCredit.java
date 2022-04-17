package com.sts.refund.domain.taxcredit;

import org.springframework.stereotype.Component;

@Component
public interface TaxCredit {
    int calculateTaxCredit(int calculatedAmount);
    boolean isSatisIedBy(int calculatedAmount);
}
