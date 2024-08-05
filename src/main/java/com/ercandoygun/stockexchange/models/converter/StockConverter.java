package com.ercandoygun.stockexchange.models.converter;

import com.ercandoygun.stockexchange.entities.Stock;
import com.ercandoygun.stockexchange.models.StockResponseDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class StockConverter {

    private final ModelMapper modelMapper;

    public StockResponseDto convertToDto(Stock stock) {
        return modelMapper.map(stock, StockResponseDto.class);
    }
}
