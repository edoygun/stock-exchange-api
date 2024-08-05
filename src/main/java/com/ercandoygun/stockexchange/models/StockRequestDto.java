package com.ercandoygun.stockexchange.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private BigDecimal currentPrice;
}
