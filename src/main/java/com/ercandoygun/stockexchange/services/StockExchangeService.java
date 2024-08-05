package com.ercandoygun.stockexchange.services;

import com.ercandoygun.stockexchange.entities.Stock;
import com.ercandoygun.stockexchange.entities.StockExchange;
import com.ercandoygun.stockexchange.exception.BadRequestException;
import com.ercandoygun.stockexchange.models.StockExchangeUpdateDto;
import com.ercandoygun.stockexchange.repositories.StockExchangeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class StockExchangeService {

    public static final int LIVE_LIMIT = 5;
    private final StockExchangeRepository stockExchangeRepository;

    public StockExchange findByName(String name) {
        return stockExchangeRepository.findByName(name).orElseThrow();
    }

    public List<StockExchange> saveAll(List<StockExchange> stockExchanges) {
        return stockExchangeRepository.saveAll(stockExchanges);
    }

    public List<StockExchange> findByStocksName(String name) {
        return stockExchangeRepository.findByStocksName(name);
    }

    @Transactional
    public StockExchange updateStockExchange(String name, StockExchangeUpdateDto updateDto, List<Stock> stocks) {
        if(CollectionUtils.isEmpty(stocks) || stocks.size() != updateDto.getStockNames().size()) {
            log.error("Stock exchange cannot be updated.");
            throw new BadRequestException("Requested stocks do not exist.");
        }

        StockExchange stockExchange = stockExchangeRepository.findByName(name).orElseThrow();
        stockExchange.setStocks(stocks);
        stockExchange.setLiveInMarket(stocks.size() >= LIVE_LIMIT);
        StockExchange savedStockExchange = stockExchangeRepository.save(stockExchange);
        log.info("Stock exchange updated successfully.");
        return savedStockExchange;
    }
}
