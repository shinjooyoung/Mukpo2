package com.sts.refund.domain;

import com.sts.refund.domain.taxcredit.TaxCredit;
import com.sts.refund.domain.taxcreditlimit.TaxCreditLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TaxCreditFactory {

    private final List<TaxCredit> taxCredits;
    private final List<TaxCreditLimit> taxCreditLimits;

    @Autowired
    public TaxCreditFactory(List<TaxCredit> taxCredits, List<TaxCreditLimit> taxCreditLimits) {
        this.taxCredits = taxCredits;
        this.taxCreditLimits = taxCreditLimits;
    }

    /**
     * 세액공제 계산 객체 생성
     * @param calculatedAmount 산출세액
     * @return
     */
    public TaxCredit createTaxCredit(int calculatedAmount) throws IllegalAccessException {
        for (TaxCredit taxCredit : taxCredits) {
            if(taxCredit.isSatisIedBy(calculatedAmount)){
                return taxCredit;
            }
        }
        throw new IllegalAccessException("잘못된 요청입니다.");
    }

    /**
     * 세액공제 한도 계산 객체 생성
     * @param totalAmount 총급여액
     * @return
     */
    public TaxCreditLimit createTaxCreditLimit(int totalAmount) throws IllegalAccessException {
        for (TaxCreditLimit taxCreditLimit : taxCreditLimits) {
            if(taxCreditLimit.isSatisIedBy(totalAmount)){
                return taxCreditLimit;
            }
        }
        throw new IllegalAccessException("잘못된 요청입니다.");
    }
}
