package com.sts.refund.service;

import com.sts.refund.domain.*;
import com.sts.refund.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EarnedIncomeService {

    private final TaxCreditFactory taxCreditFactory;

    public long calculateRefundAmount(UserDto userDto) {
        EarnedIncome earnedIncome = userDto.toEntity().getEarnedIncome();
        return earnedIncome.calculateRefundAmount(
                taxCreditFactory.createTaxCredit(earnedIncome.getCalculatedAmount()),
                taxCreditFactory.createTaxCreditLimit(earnedIncome.getTotalAmount()));
    }

    public long calculateTaxCredit(UserDto userDto) {
        EarnedIncome earnedIncome = userDto.toEntity().getEarnedIncome();
        return earnedIncome.calculateTaxCredit(taxCreditFactory.createTaxCredit(earnedIncome.getCalculatedAmount()));
    }

    public long calculateTaxCreditLimit(UserDto userDto) {
        EarnedIncome earnedIncome = userDto.toEntity().getEarnedIncome();
        return earnedIncome.calculateTaxCreditLimit(taxCreditFactory.createTaxCreditLimit(earnedIncome.getTotalAmount()));
    }

}
