package com.sts.refund.repository;

import com.sts.refund.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 회원 데이터 처리 JpaRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    String findPasswordByUserId(String userId);
    User findByUserId(String UserId);
}
