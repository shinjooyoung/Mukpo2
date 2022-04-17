package com.sts.refund.domain;

import com.sts.refund.domain.taxcredit.TaxCredit;
import com.sts.refund.domain.taxcreditlimit.TaxCreditLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
public class TaxCreditFactory {

    private final List<TaxCredit> taxCredits;
    private final List<TaxCreditLimit> taxCreditLimits;

    @Autowired
    public TaxCreditFactory(List<TaxCredit> taxCredits, List<TaxCreditLimit> taxCreditLimits) {
        this.taxCredits = taxCredits;
        this.taxCreditLimits = taxCreditLimits;
        log.debug("[TaxCreditFactory] taxCredits={}, TaxCreditLimit={}", taxCredits.size(), taxCreditLimits.size());
    }

    /**
     * 세액공제 계산 객체 생성
     * @param calculatedAmount 산출세액
     * @return
     */
    public TaxCredit createTaxCredit(int calculatedAmount) {
        for (TaxCredit taxCredit : taxCredits) {
            log.debug("[TaxCreditFactory] TaxCredit={}", taxCredit.getClass());
            if(taxCredit.isSatisIedBy(calculatedAmount)){
                return taxCredit;
            }
        }
        return null;
    }

    /**
     * 세액공제 한도 계산 객체 생성
     * @param totalAmount 총급여액
     * @return
     */
    public TaxCreditLimit createTaxCreditLimit(int totalAmount) {
        for (TaxCreditLimit taxCreditLimit : taxCreditLimits) {
            log.debug("[TaxCreditFactory] TaxCreditLimit={}", taxCreditLimit.getClass());
            if(taxCreditLimit.isSatisIedBy(totalAmount)){
                return taxCreditLimit;
            }
        }
        return null;
    }
}
