package com.ercandoygun.stockexchange.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private BigDecimal currentPrice;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "stockexchange_stock",
            joinColumns = @JoinColumn(name = "stock_id"),
            inverseJoinColumns = @JoinColumn(name = "stockexchange_id"))
    private Set<StockExchange> stockExchanges;

    private Date lastUpdate = new Date();

}
