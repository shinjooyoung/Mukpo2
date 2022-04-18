package com.jshin.muckpo.web;

import com.jshin.muckpo.service.UserService;
import com.jshin.muckpo.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
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

            int refund = earnedIncomeService.calculateRefundAmount(userDto.getEarnedIncomeDto());
            int taxCreditLimit = earnedIncomeService.calculateTaxCreditLimit(userDto.getEarnedIncomeDto());
        int taxCredit = earnedIncomeService.calculateTaxCredit(userDto.getEarnedIncomeDto());

        Map<String, String> map = new HashMap<>();
        map.put("이름", userDto.getName());
        map.put("한도", convertMoney(taxCreditLimit));
        map.put("공제액", convertMoney(taxCredit));
        map.put("환급액", convertMoney(refund));
        
        return map;
    }

    /**
     * 금액 한글단위로 변환
     * @param money
     * @return
     */
    private String convertMoney(int money) {
        int inputNumber = money;

        String[] unitWords = { "", "만", "억", "조", "경" };
        int splitUnit = 10000;
        int splitCount = unitWords.length;
        int[] resultArray = new int[String.valueOf(money).length()];
        String resultString = "";

        for (int i = 0; i < splitCount; i++) {
            int unitResult = (int) ((inputNumber % Math.pow(splitUnit, i + 1)) / Math.pow(splitUnit, i));
            unitResult = (int) Math.floor(unitResult);
            resultArray[i] = unitResult;
        }
        DecimalFormat decFormat = new DecimalFormat(",###");
        for (int i = 0; i < resultArray.length; i++) {
            if (resultArray[i] == 0) continue;
            resultString = ((decFormat.format(resultArray[i])) + unitWords[i]+ " " + resultString) + "";
        }

        resultString = resultString.replace(",","천 ");
        resultString = resultString.substring(0, resultString.length() - 1) + "원";

        return resultString.replace(" 000원", "원");
    }


}
