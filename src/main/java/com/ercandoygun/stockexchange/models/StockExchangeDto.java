package com.ercandoygun.stockexchange.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockExchangeDto {

    private String name;
    private String description;
    private Boolean liveInMarket = false;
    private List<StockRequestDto> stocks = new ArrayList<>();
}
