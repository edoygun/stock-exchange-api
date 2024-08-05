package com.ercandoygun.stockexchange.converters;

import com.ercandoygun.stockexchange.entities.Stock;
import com.ercandoygun.stockexchange.entities.StockExchange;
import com.ercandoygun.stockexchange.models.StockExchangeDto;
import com.ercandoygun.stockexchange.models.converter.StockExchangeConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class StockExchangeConverterTest {

    @InjectMocks
    private StockExchangeConverter converter;

    @Spy
    private ModelMapper modelMapper;

    private Stock stock;
    private StockExchange stockExchange;

    @Test
    public void testConvert() {
        stock = Stock.builder().id(1).currentPrice(BigDecimal.TEN).name("GOOG").description("Alphabet").lastUpdate(Date.valueOf(LocalDate.now())).build();
        List<Stock> stocks = new ArrayList<>();
        stocks.add(stock);
        stockExchange = StockExchange.builder()
                .id(1L)
                .name("Nasdaq")
                .description("American stock exchange based in New York City.")
                .liveInMarket(false)
                .stocks(stocks)
                .build();

        StockExchangeDto dto = converter.convertToDto(stockExchange);

        System.out.println(dto);
    }
}
