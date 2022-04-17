package com.sts.refund.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sts.refund.repository.UserRepository;
import com.sts.refund.web.dto.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class szsTest {

    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();


    }

    @Test
    @DisplayName("회원가입 테스트")
    void save() throws Exception {

        // given
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
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("로그인 테스트")
    void login() throws Exception {
        System.out.println("로그인 테스트 시작~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        // given
        String userId = "hong12";
        String password = "123456";
        String name = "홍길동";
        String regNo = "860824-1655068";

        String insertContent = objectMapper.writeValueAsString(
                UserDto.builder()
                        .userId(userId)
                        .password(password)
                        .name(name)
                        .regNo(regNo)
                        .build());

        mvc.perform(post("/szs/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(insertContent)
                .accept(MediaType.APPLICATION_JSON));

        String content = objectMapper.writeValueAsString(
                UserDto.builder()
                        .userId(userId)
                        .password(password)
                        .build());

        // when
        MockHttpServletResponse response = mvc.perform(post("/szs/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        //then
        System.out.println(response.getContentAsString());

    }

    @Test
    void findScrap() {
    }

    @AfterEach
    void after() {
        userRepository.deleteAll();
    }
}