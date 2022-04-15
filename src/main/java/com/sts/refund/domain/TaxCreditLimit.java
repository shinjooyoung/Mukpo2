package com.sts.refund.domain;

public interface TaxCreditLimit {
    long calculateTaxCreditLimit(long totalAmount);
    boolean isSatisIedBy(long totalAmount);
}
