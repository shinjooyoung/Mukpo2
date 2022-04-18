package com.jshin.muckpo.web.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JWT 테스트
 */
class JwtTokenProviderTest {



    @Test
    @DisplayName("토큰 발급 검증")
    void successTest() {
        // given
        JwtTokenProvider jtp = new JwtTokenProvider();
        String userId = "test-1";

        // when
        String accessToken = jtp.createAccessToken(userId);
        System.out.println("accessToken = " + accessToken);
        boolean check = jtp.validToken(accessToken);

        // then
        assertThat(jtp.getUserId(accessToken)).isEqualTo(userId);
        assertThat(jtp.getUserId(accessToken)).isNotEqualTo("test-2");
        assertThat(check).isTrue();
    }


    @Test
    @DisplayName("토큰 유효시간 검증")
    void validateToken() throws InterruptedException {

        // given
        JwtTokenProvider jtp = new JwtTokenProvider(5L);
        String userId = "test-1";


        // when
        String accessToken = jtp.createAccessToken(userId);
        Thread.sleep(6000L);

        InvalidParameterException ex = assertThrows(
                InvalidParameterException.class
                , () -> jtp.validToken(accessToken));

        // then
        assertThat(ex.getMessage()).isEqualTo("유효하지 않은 토큰입니다");
    }
}