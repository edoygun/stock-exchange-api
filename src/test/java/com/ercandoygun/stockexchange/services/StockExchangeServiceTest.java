package com.ercandoygun.stockexchange.services;

import com.ercandoygun.stockexchange.entities.Stock;
import com.ercandoygun.stockexchange.entities.StockExchange;
import com.ercandoygun.stockexchange.exception.BadRequestException;
import com.ercandoygun.stockexchange.models.StockExchangeUpdateDto;
import com.ercandoygun.stockexchange.repositories.StockExchangeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StockExchangeServiceTest {

    @InjectMocks
    private StockExchangeService service;

    @Mock
    private StockExchangeRepository repository;

    @Mock
    private StockService stockService;

    private StockExchange stockExchange;

    @BeforeEach
    public void init() {
        stockExchange = StockExchange.builder()
                .id(1L)
                .name("Shanghai Stock Exchange")
                .description("A stock exchange based in the city of Shanghai, China.")
                .liveInMarket(true)
                .build();
    }

    @Test
    public void getStockExchangeByName_returnsStockExchange() {
        when(repository.findByName("Shanghai Stock Exchange")).thenReturn(Optional.of(stockExchange));

        StockExchange result = service.findByName("Shanghai Stock Exchange");

        assertThat(result.getDescription(), equalTo("A stock exchange based in the city of Shanghai, China."));
        assertTrue(result.getLiveInMarket());
    }

    @Test
    public void getStockExchangeByName_returnsNotFound() {
        assertThrows(NoSuchElementException.class, () -> service.findByName("Euronext"));
    }

    @Test
    public void updateStockExchange_updatesStockExchangeWithGivenStocks() {
        StockExchangeUpdateDto updateDto = new StockExchangeUpdateDto();
        updateDto.setStockNames(List.of("NVDA"));
        Stock stock = Stock.builder().id(1).name("NVDA").description("NVIDIA Corp.").build();

        when(repository.findByName(stockExchange.getName())).thenReturn(Optional.of(stockExchange));
        when(repository.save(stockExchange)).thenReturn(stockExchange);

        StockExchange result = service.updateStockExchange("Shanghai Stock Exchange", updateDto, Collections.singletonList(stock));

        assertThat(result.getStocks().size(), equalTo(1));
        assertThat(result.getStocks().getFirst().getName(), equalTo("NVDA"));
    }


    @Test
    public void updateStockExchangeWithFalseStocks_throwsBadRequestException() {
        StockExchangeUpdateDto updateDto = new StockExchangeUpdateDto();
        updateDto.setStockNames(List.of("NVDA"));

        assertThrows(BadRequestException.class, () -> service.updateStockExchange("Shanghai Stock Exchange", updateDto, Collections.emptyList()));
    }
}
