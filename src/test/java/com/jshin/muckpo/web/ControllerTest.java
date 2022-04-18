package com.jshin.muckpo.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jshin.muckpo.domain.User;
import com.jshin.muckpo.repository.UserRepository;
import com.jshin.muckpo.web.dto.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 회원가입, 로그인, 회원 검증, 회원정보 조회, 유저정보 스크랩, 환급액 계산 테스트
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ControllerTest {

    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    UserRepository userRepository;


    MockHttpServletResponse loginResponse;

    @BeforeEach
    public void setup() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();


        String userId = "hong12";
        String password = "123456";
        String name = "홍길동";
        String regNo = "860824-1655068";

        String content = objectMapper.writeValueAsString(
                UserDto.builder()
                        .userId(userId)
                        .password(password)
                        .name(name)
                        .regNo(regNo)
                        .build());

        mvc.perform(post("/szs/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON));


        loginResponse = mvc.perform(post("/szs/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
                .andReturn().getResponse();



    }

//    @Test
    @DisplayName("회원가입 테스트")
    void save() throws Exception {

        // given
        String userId = "test";
        String password = "654321";
        String name = "김둘리";
        String regNo = "921108-1582816";

        String content = objectMapper.writeValueAsString(
                UserDto.builder()
                        .userId(userId)
                        .password(password)
                        .name(name)
                        .regNo(regNo)
                        .build());

        //when
        mvc.perform(post("/szs/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":200,\"message\":\"회원가입\",\"data\":{}}"));

    }

    @Test
    @DisplayName("회원가입 유저 검증 테스트")
    void userCheck() throws Exception {

        // given
        String userId = "hong12";
        String password = "123456";
        String name = "동길홍";
        String regNo = "860824-1655068";

        String content = objectMapper.writeValueAsString(
                UserDto.builder()
                        .userId(userId)
                        .password(password)
                        .name(name)
                        .regNo(regNo)
                        .build());

        // when
        mvc.perform(post("/szs/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("로그인 테스트")
    void login() throws Exception {
        // given
        String userId = "hong12";
        String password = "123456";


        String content = objectMapper.writeValueAsString(
                UserDto.builder()
                        .userId(userId)
                        .password(password)
                        .build());

        // when
        loginResponse = mvc.perform(post("/szs/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
                    .accept(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
                .andReturn().getResponse();



    }

    @Test
    @DisplayName("회원정보 가져오기 테스트")
    void findUserInfo() throws Exception {
        // given
        String contentAsString = loginResponse.getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(contentAsString, new TypeReference<Map<String, Object>>() {
        });
        Map<String, String> dataMap = (Map<String, String>) responseMap.get("data");
        String token = dataMap.get("token");


        // when
        MockHttpServletResponse response = mvc.perform(get("/szs/me")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        String userInfo = response.getContentAsString();
        Map<String, Object> userInfoMap = objectMapper.readValue(userInfo, new TypeReference<Map<String, Object>>() {});
        Map<String, Object> userInfoDataMap = (Map<String, Object>) userInfoMap.get("data");

        //then
        assertThat(userInfoDataMap.get("name")).isEqualTo("홍길동");
        assertThat(userInfoDataMap.get("regNo")).isEqualTo("860824-1655068");
    }

    @Test
    @DisplayName("스크랩 데이터 조회, 저장 테스트")
    void findAndSaveScrap() throws Exception {

        // given
        String contentAsString = loginResponse.getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(contentAsString, new TypeReference<Map<String, Object>>() {
        });
        Map<String, String> dataMap = (Map<String, String>) responseMap.get("data");
        String token = dataMap.get("token");


        // when
        MockHttpServletResponse response = mvc.perform(post("/szs/scrap")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        //then
        User user = userRepository.findByUserId("hong12");
        assertThat(user.getEarnedIncome().getTotalAmount()).isEqualTo(24000000);
        assertThat(user.getEarnedIncome().getCalculatedAmount()).isEqualTo(2000000);
    }

    @Test
    @DisplayName("유저의 환급액 계산")
    void findRefund() throws Exception {

        // given
        String contentAsString = loginResponse.getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(contentAsString, new TypeReference<Map<String, Object>>() {
        });
        Map<String, String> dataMap = (Map<String, String>) responseMap.get("data");
        String token = dataMap.get("token");

        // when
        MockHttpServletResponse response2 = mvc.perform(post("/szs/scrap")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        MockHttpServletResponse response = mvc.perform(get("/szs/refund")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        Map<String, String> refundMap = objectMapper.readValue(response.getContentAsString(), new TypeReference<Map<String, String>>() {});

        //then
        assertThat(refundMap.get("한도")).isEqualTo("74만원");
        assertThat(refundMap.get("공제액")).isEqualTo("92만 5천원");
        assertThat(refundMap.get("환급액")).isEqualTo("74만원");

    }

    @AfterEach
    void after() {
        userRepository.deleteAll();
    }
}