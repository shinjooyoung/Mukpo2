package com.sts.refund.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sts.refund.domain.EarnedIncome;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 근로소득 DTO
 */
@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EarnedIncomeDto {

    private int calculatedAmount;
    private int totalAmount;


    @Builder
    public EarnedIncomeDto(int calculatedAmount, int totalAmount){
        this.calculatedAmount = calculatedAmount;
        this.totalAmount = totalAmount;
    }

    /**
     * EarnedIncome Entity를 EarnedIncomeDto로 변환
     * @param earnedIncome 
     * @return
     */
    public static EarnedIncomeDto of(EarnedIncome earnedIncome){
        return builder()
                .calculatedAmount(earnedIncome.getCalculatedAmount())
                .totalAmount(earnedIncome.getTotalAmount())
                .build();
    }

    /**
     * EarnedIncome Entity로 변환
     * @return 
     */
    public EarnedIncome toEntity(){
        return EarnedIncome.builder()
                .calculatedAmount(calculatedAmount)
                .totalAmount(totalAmount)
                .build();
    }

}
