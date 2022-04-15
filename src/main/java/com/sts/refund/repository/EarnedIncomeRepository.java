package com.sts.refund.repository;

import com.sts.refund.domain.EarnedIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EarnedIncomeRepository extends JpaRepository<EarnedIncome, Long> {
}
