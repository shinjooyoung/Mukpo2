package com.sts.refund.domain.taxcreditlimit;

import org.springframework.stereotype.Component;

@Component
public interface TaxCreditLimit {
    int calculateTaxCreditLimit(int totalAmount);
    boolean isSatisIedBy(int totalAmount);
}
