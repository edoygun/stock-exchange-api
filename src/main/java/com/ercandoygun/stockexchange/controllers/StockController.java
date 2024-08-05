package com.ercandoygun.stockexchange.controllers;

import com.ercandoygun.stockexchange.entities.Stock;
import com.ercandoygun.stockexchange.models.StockRequestDto;
import com.ercandoygun.stockexchange.models.StockResponseDto;
import com.ercandoygun.stockexchange.models.StockUpdateDto;
import com.ercandoygun.stockexchange.models.converter.StockConverter;
import com.ercandoygun.stockexchange.services.StockService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stock")
@AllArgsConstructor
@Slf4j
public class StockController {

    private final StockService stockService;
    private final StockConverter converter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockResponseDto createStock(@Valid @RequestBody StockRequestDto stockRequestDto) {
        Stock stock = stockService.create(stockRequestDto);
        return converter.convertToDto(stock);
    }

    @PutMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public StockResponseDto updateStock(@PathVariable("name") String name,
                                      @Valid @RequestBody StockUpdateDto stockUpdateDto) {
        Stock stock = stockService.update(stockUpdateDto, name);
        return converter.convertToDto(stock);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStock(@PathVariable String name) {
        stockService.delete(name);
    }
}
