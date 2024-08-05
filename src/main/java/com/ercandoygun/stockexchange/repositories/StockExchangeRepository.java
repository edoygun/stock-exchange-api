package com.ercandoygun.stockexchange.repositories;

import com.ercandoygun.stockexchange.entities.StockExchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockExchangeRepository extends JpaRepository<StockExchange, Long> {

    Optional<StockExchange> findByName(String name);
    List<StockExchange> findByStocksName(String name);
}
