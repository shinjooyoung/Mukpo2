package com.jshin.muckpo.domain;

import com.jshin.muckpo.domain.taxcredit.TaxCredit;
import com.jshin.muckpo.domain.taxcreditlimit.TaxCreditLimit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

/**
 * 환급액 계산테스트
 */
class EarnedIncomeTest {

    TaxCreditFactory taxCreditFactory;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(DomainConfig.class);
        taxCreditFactory = ac.getBean(TaxCreditFactory.class);
    }

    @Test
    @DisplayName("세액공제 한도 3300만원 이하")
    void calculateTaxCreditLimit1() throws IllegalAccessException {
        // given
        TaxCreditLimit belowValue = taxCreditFactory.createTaxCreditLimit(28000000);

        // when
        int below = belowValue.calculateTaxCreditLimit(28000000);

        // then
        assertThat(below).isEqualTo(740000);
    }

    @Test
    @DisplayName("세액공제 한도 3300만원 초과 7000만 이하 이하")
    void calculateTaxCreditLimit2() throws IllegalAccessException {
        // given
        TaxCreditLimit middleValue = taxCreditFactory.createTaxCreditLimit(50000000);

        // when
        int middle = middleValue.calculateTaxCreditLimit(50000000);

        // then
        assertThat(middle).isEqualTo(604000);

    }

    @Test
    @DisplayName("세액공제 한도 7000만원 초과")
    void calculateTaxCreditLimit3() throws IllegalAccessException {
        // given
        TaxCreditLimit excessValue = taxCreditFactory.createTaxCreditLimit(90000000);

        // when
        int excess = excessValue.calculateTaxCreditLimit(90000000);

        // then
        assertThat(excess).isEqualTo(500000);

    }

    @Test
    @DisplayName("세액공제 130만원 이하 테스트")
    void calculateTaxCredit1() throws IllegalAccessException {
        // given
        TaxCredit belowValue = taxCreditFactory.createTaxCredit(1000000);

        // when
        int below = belowValue.calculateTaxCredit(1000000);

        // then
        assertThat(below).isEqualTo(550000);
    }

    @Test
    @DisplayName("세액공제 130만원 초과 테스트")
    void calculateTaxCredit2() throws IllegalAccessException {
        // given
        TaxCredit excessValue = taxCreditFactory.createTaxCredit(1500000);

        // when
        int excess = excessValue.calculateTaxCredit(1500000);

        // then
        assertThat(excess).isEqualTo(775000);
    }

    @Test
    @DisplayName("환급액 계산")
    void calculateRefundAmount() throws IllegalAccessException {
        // given
        EarnedIncome earnedIncome = EarnedIncome.builder()
                .totalAmount(40000000)
                .calculatedAmount(2000000)
                .build();
        // when
        int taxCreditLimit = taxCreditFactory.createTaxCreditLimit(40000000).calculateTaxCreditLimit(40000000);

        int taxCredit = taxCreditFactory.createTaxCredit(2000000).calculateTaxCredit(2000000);

        int refund = earnedIncome.calculateRefundAmount(
                taxCreditFactory.createTaxCredit(2000000),
                taxCreditFactory.createTaxCreditLimit(40000000));
        // then
        assertThat(taxCreditLimit).isEqualTo(684000);
        assertThat(taxCredit).isEqualTo(925000);
        assertThat(refund).isEqualTo(684000);
    }
}