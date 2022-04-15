package com.sts.refund.service;

import com.sts.refund.domain.User;
import com.sts.refund.repository.EarnedIncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EarnedIncomeService {

    public long calculateRefund(User user) {
        return user.getAggregateIncomeTax().getCalculatedAmount();
    }

}
