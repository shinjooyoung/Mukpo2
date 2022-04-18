package com.jshin.muckpo.web;

import com.jshin.muckpo.web.response.StsResponse;
import com.jshin.muckpo.service.UserService;
import com.jshin.muckpo.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 유저 정보 관련 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 매핑
     * @param userDto
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("/user")
    public ResponseEntity save(@Valid @RequestBody UserDto userDto, HttpServletResponse response) throws Exception {

        userService.save(userDto);

        StsResponse<Map<String, Object>> stsResponse = StsResponse.response(response.getStatus(),"",new HashMap<String, Object>());

        return new ResponseEntity(stsResponse, HttpStatus.OK);
    }

    /**
     * 회원정보 조회 매핑
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/user")
    public ResponseEntity find(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String userId = request.getAttribute("userId").toString();
        Map<String, Object> userMap = userService.findUser(userId);

        StsResponse<Map<String, Object>> stsResponse = StsResponse.response(response.getStatus(),"", userMap);
        return new ResponseEntity(stsResponse, HttpStatus.OK);
    }

    /**
     * 스크랩 데이터 요청 매핑
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/api")
    public StsResponse findApi(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String userId = request.getAttribute("userId").toString();

        Map<String, Object> data = userService.getScrap(userId);

        StsResponse<Map<String, Object>> stsResponse = StsResponse.response(response.getStatus(),"", data);

        return stsResponse;
    }

}
