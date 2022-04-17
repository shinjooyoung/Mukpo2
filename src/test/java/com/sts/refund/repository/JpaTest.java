package com.sts.refund.repository;

import com.sts.refund.domain.EarnedIncome;
import com.sts.refund.domain.User;
import com.sts.refund.web.dto.EarnedIncomeDto;
import com.sts.refund.web.dto.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class JpaTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EarnedIncomeRepository earnedIncomeRepository;

    @BeforeEach
    void setup() {
        String userId = "hong12";
        String password = "123456";
        String name = "홍길동";
        String regNo = "860824-1655068";

        User user = UserDto.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .regNo(regNo)
                .build()
                .toEntity();

        User dbUser = userRepository.save(user);

        int calculatedAmount = 4000;
        int totalAmount = 100;
        EarnedIncome earnedIncome = EarnedIncome.builder()
                .calculatedAmount(calculatedAmount)
                .totalAmount(totalAmount)
                .build();

        dbUser.setEarnedIncome(earnedIncome);
        userRepository.save(dbUser);
        EarnedIncome dbEarnedIncome = earnedIncomeRepository.save(earnedIncome);

        System.out.println("dbuser ::  "+dbUser.toString());
        System.out.println("earnedIncome ::  "+earnedIncome.toString());
    }

 //   @Test
    @DisplayName("유저 등록")
    void saveUser() {
        //given
        String userId = "hong12";
        String password = "123456";
        String name = "홍길동";
        String regNo = "860824-1655068";

        EarnedIncomeDto earnedIncomeDto = EarnedIncomeDto.builder()
                .calculatedAmount(3000)
                .totalAmount(4000)
                .build();
        //when
        User user = UserDto.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .regNo(regNo)
                .build()
                .toEntity();

        User dbUser = userRepository.save(user);

        //then
        assertThat(dbUser.getUserId()).isEqualTo(userId);
    }

 //   @Test
    @DisplayName("근로소득 저장")
    void saveEarnedIncome() {

        //given
        String userId = "hong12";
        int calculatedAmount = 4000;
        int totalAmount = 100;
        EarnedIncome earnedIncome = EarnedIncome.builder()
                .calculatedAmount(calculatedAmount)
                .totalAmount(totalAmount)
                .build();

        //when
        User user = userRepository.findByUserId(userId);
        earnedIncome.setUser(user);
        EarnedIncome dbEarnedIncome = earnedIncomeRepository.save(earnedIncome);

        //then
        assertThat(dbEarnedIncome.getTotalAmount()).isEqualTo(totalAmount);
    }

    @Test
    @DisplayName("유저 조회")
    void findUser() {

        //given
        String userId = "hong12";

        //when
        User dbUser = userRepository.findByUserId(userId);

        //then
        assertThat(dbUser.getRegNo()).isEqualTo("860824-1655068");
        assertThat(dbUser.getEarnedIncome().getTotalAmount()).isEqualTo(100);
    }


    @AfterEach
    void after() {
        userRepository.deleteAll();
    }
}