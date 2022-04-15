package com.sts.refund.web;


import com.sts.refund.service.EarnedIncomeService;
import com.sts.refund.service.UserService;
import com.sts.refund.web.dto.UserDto;
import com.sts.refund.web.response.StsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity refund(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String userId = request.getAttribute("userId").toString();

        userService.findByUserId(userId);

        
        //토큰 셋팅
        String token = "";
        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        //응답 메시지
        StsResponse<Map<String, String>> stsResponse = StsResponse.response(response.getStatus(), "로그인", map);
        
        return new ResponseEntity(stsResponse, HttpStatus.OK);
    }


}
