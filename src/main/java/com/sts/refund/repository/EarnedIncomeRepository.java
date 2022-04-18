package com.sts.refund.repository;

import com.sts.refund.domain.EarnedIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 근로소득 데이터 처리 JpaRepository
 */
@Repository
public interface EarnedIncomeRepository extends JpaRepository<EarnedIncome, Long> {
}
