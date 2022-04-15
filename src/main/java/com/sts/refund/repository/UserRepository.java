package com.sts.refund.repository;

import com.sts.refund.domain.User;
import org.apache.catalina.startup.HomesUserDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    String findPasswordByUserId(String userId);
    User findByUserId(String UserId);
}
