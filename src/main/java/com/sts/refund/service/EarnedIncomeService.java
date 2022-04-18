package com.sts.refund.service;

import com.sts.refund.domain.*;
import com.sts.refund.web.dto.EarnedIncomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 근로소득 서비스
 */
@Service
@RequiredArgsConstructor
public class EarnedIncomeService {

    private final TaxCreditFactory taxCreditFactory;

    /**
     * 환급액 계산 서비스
     * @param earnedIncomeDto
                * @return
     * @throws IllegalAccessException
                */
        public int calculateRefundAmount(EarnedIncomeDto earnedIncomeDto) throws IllegalAccessException {
            return earnedIncomeDto.toEntity().calculateRefundAmount(
                taxCreditFactory.createTaxCredit(earnedIncomeDto.getCalculatedAmount()),
                taxCreditFactory.createTaxCreditLimit(earnedIncomeDto.getTotalAmount()));
    }

    /**
     * 세액공제 계산 서비스
     * @param earnedIncomeDto 
     * @return
     * @throws IllegalAccessException
     */
    public int calculateTaxCredit(EarnedIncomeDto earnedIncomeDto) throws IllegalAccessException {
        return earnedIncomeDto.toEntity().calculateTaxCredit(taxCreditFactory.createTaxCredit(earnedIncomeDto.getCalculatedAmount()));
    }

    /**
     * 세액공제 한도 계산 서비스
     * @param earnedIncomeDto 
     * @return
     * @throws IllegalAccessException
     */
    public int calculateTaxCreditLimit(EarnedIncomeDto earnedIncomeDto) throws IllegalAccessException {
        return earnedIncomeDto.toEntity().calculateTaxCreditLimit(taxCreditFactory.createTaxCreditLimit(earnedIncomeDto.getTotalAmount()));
    }

}
