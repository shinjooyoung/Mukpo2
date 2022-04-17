package com.sts.refund.web.aes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AES256UtilTest {

    @Test
    @DisplayName("비번, 주민번호 암호화 테스트")
    void aes256() throws Exception {

        // given
        AES256Util aes256Util = new AES256Util();
        String password = "홍길동";
        String regNo = "860824-1655068";

        // when

        String passwordEncrypt = aes256Util.encrypt(password);
        String passwordDecrypt = aes256Util.decrypt(passwordEncrypt);

        String regNoEncrypt = aes256Util.encrypt(regNo);
        String regNoDecrypt = aes256Util.decrypt(regNoEncrypt);

        // then
        assertThat(passwordEncrypt).isNotEqualTo(password);
        assertThat(passwordDecrypt).isEqualTo(password);

        assertThat(regNoEncrypt).isNotEqualTo(regNo);
        assertThat(regNoDecrypt).isEqualTo(regNo);
    }

}