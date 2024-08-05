package com.ercandoygun.stockexchange.services;

import com.ercandoygun.stockexchange.entities.Stock;
import com.ercandoygun.stockexchange.entities.StockExchange;
import com.ercandoygun.stockexchange.models.StockRequestDto;
import com.ercandoygun.stockexchange.models.StockUpdateDto;
import com.ercandoygun.stockexchange.repositories.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.ercandoygun.stockexchange.services.StockExchangeService.LIVE_LIMIT;

@Slf4j
@AllArgsConstructor
@Service
public class StockService {

    private StockRepository stockRepository;
    private StockExchangeService stockExchangeService;

    public List<Stock> findAllByName(List<String> stockNames) {
        return stockRepository.findAllByNameIn(stockNames);
    }

    public Stock create(StockRequestDto stockRequestDto) {
        Stock stock = new Stock();
        stock.setName(stockRequestDto.getName());
        stock.setDescription(stockRequestDto.getDescription());
        stock.setCurrentPrice(stockRequestDto.getCurrentPrice());
        Stock savedStock = stockRepository.save(stock);
        log.info("Stock created: {}", savedStock);
        return savedStock;
    }

    public Stock update(StockUpdateDto stockUpdateDto, String name) {
        Stock stock = stockRepository.findByName(name).orElseThrow();
        stock.setCurrentPrice(stockUpdateDto.getCurrentPrice());
        stock.setLastUpdate(new Date());
        Stock savedStock = stockRepository.save(stock);
        log.info("Stock updated with the current price {}", savedStock.getCurrentPrice());
        return savedStock;
    }

    @Transactional
    public void delete(String name) {
        Stock stock = stockRepository.findByName(name).orElseThrow();
        List<StockExchange> stockExchanges = stockExchangeService.findByStocksName(name);
        stockExchanges.forEach(stockExchange -> {
            stockExchange.getStocks().remove(stock);
            stockExchange.setLiveInMarket(stockExchange.getStocks().size() >= LIVE_LIMIT);
        });
        stockRepository.delete(stock);
        stockExchangeService.saveAll(stockExchanges);
        log.info("Stock deleted with the name {}", name);
    }
}
