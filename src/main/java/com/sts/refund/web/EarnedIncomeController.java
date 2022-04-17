package com.sts.refund.web;

import com.sts.refund.service.EarnedIncomeService;
import com.sts.refund.service.UserService;
import com.sts.refund.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class EarnedIncomeController {

    private final UserService userService;
    private final EarnedIncomeService earnedIncomeService;

    @GetMapping("/szs/refund")
    public Map<String, String> refund(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String userId = request.getAttribute("userId").toString();

        UserDto userDto = userService.findByUserId(userId);

        long refund = earnedIncomeService.calculateRefundAmount(userDto);
        long taxCreditLimit = earnedIncomeService.calculateTaxCreditLimit(userDto);
        long taxCredit = earnedIncomeService.calculateTaxCredit(userDto);

        //토큰 셋팅
        Map<String, String> map = new HashMap<>();
        map.put("이름", userDto.getName());
        map.put("한도", convertMoney(taxCreditLimit));
        map.put("공제액", convertMoney(taxCredit));
        map.put("환급액", convertMoney(refund));
        
        return map;
    }

    private String convertMoney(long money) {
        String[] han1 = { "", "일","이","삼", "사", "오", "육", "칠", "팔", "구" };
        String[] han2 = { "", "십", "백", "천" };
        String[] han3 = { "", "만", "억", "조", "경" };

        StringBuffer result = new StringBuffer();
        String moneyStr = String.valueOf(money);
        int length = moneyStr.length();
        int initInt=0;

        for (int i = length-1; i >= 0; i--) {
            initInt=Integer.parseInt(String.valueOf(moneyStr.charAt(length-i-1)));

            if (initInt > 0) {
                result.append(han1[initInt]);
                result.append(han2[i % 4]); // 십,백,천

            }
            // 만, 억, 조 ,경 단위
            if (i % 4 == 0) {
                result.append(han3[i / 4]); // 천단위
                result.append(" ");
            }

        }
        result.append("원");
        return result.toString();
    }


}
