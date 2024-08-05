package com.ercandoygun.stockexchange.models.converter;

import com.ercandoygun.stockexchange.entities.StockExchange;
import com.ercandoygun.stockexchange.models.StockExchangeDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class StockExchangeConverter {

    private final ModelMapper modelMapper;

    public StockExchangeDto convertToDto(StockExchange stockExchange) {
        return modelMapper.map(stockExchange, StockExchangeDto.class);
    }
}
