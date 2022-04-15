package com.sts.refund.domain;

public interface TaxCredit {
    long calculateTaxCredit(long calculatedAmount);
    boolean isSatisIedBy(long calculatedAmount);
}
