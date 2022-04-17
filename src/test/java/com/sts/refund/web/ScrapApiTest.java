package com.sts.refund.web;

import com.sts.refund.domain.DomainConfig;
import com.sts.refund.domain.TaxCreditFactory;
import com.sts.refund.web.dto.EarnedIncomeDto;
import com.sts.refund.web.jwt.JwtTokenProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ScrapApiTest {

    @Test
    @DisplayName("scrap api 호출")
    void requestScrap() throws Exception {
        // given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ScrapApi.class);
        ScrapApi scrapApi = ac.getBean(ScrapApi.class);
        String name = "홍길동";
        String regNo = "860824-1655068";

        // when
        Map<String, Object> scrap = scrapApi.requestScrap(name, regNo);

        Map<String, Object> data = (Map<String, Object>) scrap.get("data");
        Map<String, Object> jsonList = (Map<String, Object>) data.get("jsonList");

        List<Map<String, String>> scrap002 = (List<Map<String, String>>) jsonList.get("scrap002");
        List<Map<String, String>> scrap001 = (List<Map<String, String>>) jsonList.get("scrap001");

        Map<String, String> taxCredit = scrap002.get(0);
        Map<String, String> amount = scrap001.get(0);


        // then
        assertThat(amount.get("총지급액")).isEqualTo("24,000.000");
        assertThat(taxCredit.get("총사용금액")).isEqualTo("2,000,000");

    }
}