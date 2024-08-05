package com.ercandoygun.stockexchange.services;

import com.ercandoygun.stockexchange.entities.Stock;
import com.ercandoygun.stockexchange.entities.StockExchange;
import com.ercandoygun.stockexchange.models.StockRequestDto;
import com.ercandoygun.stockexchange.models.StockUpdateDto;
import com.ercandoygun.stockexchange.repositories.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @InjectMocks
    private StockService service;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockExchangeService stockExchangeService;


    @Test
    public void create_returnsNewlyCreatedStock() {
        StockRequestDto requestDto = new StockRequestDto();
        requestDto.setName("DEF");
        requestDto.setDescription("Default Corp.");
        requestDto.setCurrentPrice(BigDecimal.valueOf(10d));

        service.create(requestDto);

        ArgumentCaptor<Stock> argumentCaptor = ArgumentCaptor.forClass(Stock.class);
        verify(stockRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName(), equalTo("DEF"));
    }

    @Test
    public void update_updatesCurrentPriceOfStock() {
        Stock stock = Stock.builder().id(2).currentPrice(BigDecimal.valueOf(20d)).name("DEF").description("Default Corp.").lastUpdate(Date.valueOf(LocalDate.now())).build();

        StockUpdateDto updateDto = new StockUpdateDto();
        updateDto.setCurrentPrice(BigDecimal.TEN);

        when(stockRepository.findByName("DEF")).thenReturn(Optional.of(stock));
        when(stockRepository.save(stock)).thenReturn(stock);

        service.update(updateDto, "DEF");

        ArgumentCaptor<Stock> argumentCaptor = ArgumentCaptor.forClass(Stock.class);
        verify(stockRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getCurrentPrice(), equalTo(BigDecimal.TEN));
    }

    @Test
    public void delete_deletesStockByNameAndUpdatesStockExchanges() {
        Stock stock = Stock.builder().id(2).currentPrice(BigDecimal.valueOf(20d)).name("DEF").description("Default Corp.").lastUpdate(Date.valueOf(LocalDate.now())).build();
        Stock stock1 = Stock.builder().id(3).currentPrice(BigDecimal.valueOf(22d)).name("APPL").description("Apple Corp.").lastUpdate(Date.valueOf(LocalDate.now())).build();
        Stock stock2 = Stock.builder().id(4).currentPrice(BigDecimal.valueOf(11d)).name("TSLA").description("Tesla").lastUpdate(Date.valueOf(LocalDate.now())).build();
        Stock stock3 = Stock.builder().id(5).currentPrice(BigDecimal.valueOf(253d)).name("NFLX").description("Netflix").lastUpdate(Date.valueOf(LocalDate.now())).build();
        Stock stock4 = Stock.builder().id(6).currentPrice(BigDecimal.valueOf(23d)).name("GOOG").description("Google").lastUpdate(Date.valueOf(LocalDate.now())).build();

        List<Stock> stockList = new ArrayList<>();
        stockList.add(stock);
        stockList.add(stock1);
        stockList.add(stock2);
        stockList.add(stock3);
        stockList.add(stock4);

        StockExchange stockExchange = StockExchange.builder()
                .id(1L)
                .name("Nasdaq")
                .description("American stock exchange based in New York City.")
                .liveInMarket(false)
                .stocks(stockList)
                .build();

        List<StockExchange> stockExchanges = Collections.singletonList(stockExchange);

        when(stockRepository.findByName("DEF")).thenReturn(Optional.of(stock));
        when(stockExchangeService.findByStocksName("DEF")).thenReturn(stockExchanges);

        service.delete("DEF");

        ArgumentCaptor<List<StockExchange>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(stockRepository).delete(stock);
        verify(stockExchangeService).saveAll(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().get(0).getLiveInMarket(), equalTo(false));
        assertThat(argumentCaptor.getValue().get(0).getStocks().size(), equalTo(4));
    }
}
