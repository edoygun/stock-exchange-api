package com.ercandoygun.stockexchange.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stock_exchange")
public class StockExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private Boolean liveInMarket = false;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "stockExchanges")
    private List<Stock> stocks = new ArrayList<>();
}
