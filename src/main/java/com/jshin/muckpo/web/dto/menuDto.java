package com.jshin.muckpo.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jshin.muckpo.domain.EarnedIncome;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class menuDto {

    private int calculatedAmount;
    private int totalAmount;


    @Builder
    public menuDto(int calculatedAmount, int totalAmount){
        this.calculatedAmount = calculatedAmount;
        this.totalAmount = totalAmount;
    }


}
