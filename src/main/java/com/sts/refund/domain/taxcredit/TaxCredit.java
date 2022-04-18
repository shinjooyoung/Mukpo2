package com.sts.refund.domain.taxcredit;

import org.springframework.stereotype.Component;

/**
 * 세액 공제 계산 인터페이스
 */
@Component
public interface TaxCredit {

    /**
     * 세액 공제 계산
     * @param calculatedAmount 산출세액
     * @return
     */
    int calculateTaxCredit(int calculatedAmount);

    /**
     * 세액 공제 계산식 적용여부
     * @param calculatedAmount 산출세액
     * @return
     */
    boolean isSatisIedBy(int calculatedAmount);
}
