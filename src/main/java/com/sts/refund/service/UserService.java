package com.sts.refund.service;

import com.sts.refund.domain.EarnedIncome;
import com.sts.refund.domain.User;
import com.sts.refund.repository.EarnedIncomeRepository;
import com.sts.refund.repository.UserRepository;
import com.sts.refund.web.ScrapApi;
import com.sts.refund.web.aes.AES256Util;
import com.sts.refund.web.dto.EarnedIncomeDto;
import com.sts.refund.web.dto.UserDto;
import com.sts.refund.web.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 회원 서비스
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EarnedIncomeRepository earnedIncomeRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AES256Util aes256Util;
    private final ScrapApi scrapApi;

    /**
     * 회원가입 서비스
     * @param userDto 
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(UserDto userDto) throws Exception{
        userDto.passwordEncoding(aes256Util.encrypt(userDto.getPassword()));
        userDto.setRegNo(aes256Util.encrypt(userDto.getRegNo()));
        userRepository.save(userDto.toEntity());
    }


    /**
     * 회원 조회 서비스
     * @param userId
     * @return
     * @throws Exception
     */
    public UserDto findByUserId(String userId) throws Exception {
        UserDto userDto = UserDto.of(userRepository.findByUserId(userId));
        return userDto;
    }

    /**
     * 입력 비밀번호, DB 비밀번호 확인 서비스
     * @param userId
     * @param password
     * @return
     * @throws Exception
     */
    public boolean userMatch(String userId, String password) throws Exception {
        String encodingPassword = userRepository.findByUserId(userId).getPassword();
        return aes256Util.encrypt(password).equals(encodingPassword);
    }


    /**
     * JWT 토큰 생성 서비스
     * @param userId 
     * @return
     */
    public String createToken(String userId) {
        return jwtTokenProvider.createAccessToken(userId);
    }

    /**
     * 가입한 유저 정보 스크랩 조회 서비스
     * @param userId 
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> getScrap(String userId) throws Exception {
        User user = userRepository.findByUserId(userId);

        Map<String, Object> scrap = scrapApi.requestScrap(user.getName(), aes256Util.decrypt(user.getRegNo()));
        Map<String, Object> data = (Map<String, Object>) scrap.get("data");

        saveEarnedIncome(user, data);

        return data;
    }

    /**
     * 스크랩 데어터 중 계산에 필요한 결과값 저장 서비스
     * @param user 
     * @param data
     */
    private void saveEarnedIncome(User user, Map<String, Object> data) {

        Map<String, Object> jsonList = (Map<String, Object>) data.get("jsonList");
        List<Map<String, String>> scrap002 = (List<Map<String, String>>) jsonList.get("scrap002");
        List<Map<String, String>> scrap001 = (List<Map<String, String>>) jsonList.get("scrap001");

        Map<String, String> taxCredit = scrap002.get(0);
        Map<String, String> amount = scrap001.get(0);

        int totalAmount = Integer.parseInt(amount.get("총지급액").replace(",", ""));
        int calculatedAmount = Integer.parseInt(taxCredit.get("총사용금액").replace(",", ""));

        EarnedIncome earnedIncome = EarnedIncomeDto.builder()
                .totalAmount(totalAmount)
                .calculatedAmount(calculatedAmount)
                .build().toEntity();
        user.setEarnedIncome(earnedIncome);
        userRepository.save(user);
        earnedIncomeRepository.save(earnedIncome);
    }


    /**
     * 가입한 회원정보 조회 서비스
     * @param userId 
     * @return
     * @throws Exception
     */
    public Map<String, Object> findUser(String userId) throws Exception {

        UserDto userDto = findByUserId(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("userNo", userDto.getUserNo());
        result.put("userId", userDto.getUserId());
        result.put("name", userDto.getName());
        result.put("regNo", aes256Util.decrypt(userDto.getRegNo()));
        return result;
    }
}
