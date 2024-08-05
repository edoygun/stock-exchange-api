package com.ercandoygun.stockexchange.repositories;

import com.ercandoygun.stockexchange.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findAllByNameIn(List<String> name);
    Optional<Stock> findByName(String name);
}
