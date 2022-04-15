package com.sts.refund.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class TaxCreditFactory {

    private final Set<TaxCredit> taxCredits;
    private final Set<TaxCreditLimit> taxCreditLimits;

    private TaxCredit findTaxCredit(long calculatedAmount) {
        for (TaxCredit taxCredit : taxCredits) {
            if(taxCredit.isSatisIedBy(calculatedAmount)){
                return taxCredit;
            }
        }
        return null;
    }

    private TaxCreditLimit findTaxCreditLimit(long totalAmount) {
        for (TaxCreditLimit taxCreditLimit : taxCreditLimits) {
            if(taxCreditLimit.isSatisIedBy(totalAmount)){
                return taxCreditLimit;
            }
        }
        return null;
    }
}
