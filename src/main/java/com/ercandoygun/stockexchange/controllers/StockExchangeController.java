package com.ercandoygun.stockexchange.controllers;

import com.ercandoygun.stockexchange.entities.Stock;
import com.ercandoygun.stockexchange.models.StockExchangeDto;
import com.ercandoygun.stockexchange.entities.StockExchange;
import com.ercandoygun.stockexchange.models.StockExchangeUpdateDto;
import com.ercandoygun.stockexchange.models.converter.StockExchangeConverter;
import com.ercandoygun.stockexchange.services.StockExchangeService;

import com.ercandoygun.stockexchange.services.StockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock-exchange")
@AllArgsConstructor
@Slf4j
public class StockExchangeController {

    private final StockExchangeService stockExchangeService;
    private final StockService stockService;
    private final StockExchangeConverter converter;


    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public StockExchangeDto getStockExchangeByName(@PathVariable String name) {
        StockExchange stockExchange = stockExchangeService.findByName(name);
        return converter.convertToDto(stockExchange);
    }


    @PutMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public StockExchangeDto addStock(@PathVariable String name,
                                     @RequestBody StockExchangeUpdateDto stockExchangeUpdateDto) {
        List<Stock> stocks = stockService.findAllByName(stockExchangeUpdateDto.getStockNames());
        StockExchange stockExchange = stockExchangeService.updateStockExchange(name, stockExchangeUpdateDto, stocks);
        return converter.convertToDto(stockExchange);
    }
}
