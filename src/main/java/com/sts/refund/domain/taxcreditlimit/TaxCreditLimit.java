package com.sts.refund.domain.taxcreditlimit;

import org.springframework.stereotype.Component;

/**
 * 세액공제 한도를 계산 인터페이스
 */
@Component
public interface TaxCreditLimit {

    /**
     * 세액공제 한도 계산
     * @param totalAmount 총 지급액
     * @return
     */
    int calculateTaxCreditLimit(int totalAmount);

    /**
     * 세액공제 한도 계산식 적용여부
     * @param totalAmount
     * @return
     */
    boolean isSatisIedBy(int totalAmount);
}
