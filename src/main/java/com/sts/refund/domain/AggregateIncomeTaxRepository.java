package com.sts.refund.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregateIncomeTaxRepository extends JpaRepository<AggregateIncomeTax, Long> {
}
