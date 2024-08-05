package com.ercandoygun.stockexchange.controllers;

import com.ercandoygun.stockexchange.auth.util.JwtUtil;
import com.ercandoygun.stockexchange.entities.Stock;
import com.ercandoygun.stockexchange.entities.StockExchange;
import com.ercandoygun.stockexchange.models.StockExchangeDto;
import com.ercandoygun.stockexchange.models.StockExchangeUpdateDto;
import com.ercandoygun.stockexchange.models.converter.StockExchangeConverter;
import com.ercandoygun.stockexchange.services.StockExchangeService;
import com.ercandoygun.stockexchange.services.StockService;
import com.ercandoygun.stockexchange.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = StockExchangeController.class)
public class StockExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private StockExchangeService stockExchangeService;

    @MockBean
    private StockService stockService;

    @SpyBean
    private StockExchangeConverter stockExchangeConverter;

    @SpyBean
    private ModelMapper modelMapper;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Stock stock;
    private Stock stock1;
    private Stock stock2;
    private StockExchange stockExchange;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void getStockExchangeByName_ReturnsStockExchangeWithListedStocks() throws Exception {
        stock = Stock.builder().id(1).currentPrice(BigDecimal.TEN).name("GOOG").description("Alphabet").lastUpdate(Date.valueOf(LocalDate.now())).build();
        List<Stock> stocks = Collections.singletonList(stock);
        stockExchange = StockExchange.builder()
                .id(1L)
                .name("Nasdaq")
                .description("American stock exchange based in New York City.")
                .liveInMarket(false)
                .stocks(stocks)
                .build();

        when(stockExchangeService.findByName("Nasdaq")).thenReturn(stockExchange);

        MvcResult result = mockMvc.perform(get("/api/v1/stock-exchange/{name}", "Nasdaq")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        StockExchangeDto response = objectMapper.readValue(json, StockExchangeDto.class);

        assertThat(response.getName(), equalTo("Nasdaq"));
        assertThat(response.getStocks().get(0).getName(), equalTo("GOOG"));
    }

    @Test
    public void updateStockExchangeByName_ReturnsStockExchangeWithUpdatedStocks() throws Exception {
        stock1 = Stock.builder().id(2).currentPrice(BigDecimal.valueOf(20d)).name("NVDA").description("NVIDIA Corp.").lastUpdate(Date.valueOf(LocalDate.now())).build();
        stock2 = Stock.builder().id(3).currentPrice(BigDecimal.valueOf(15d)).name("MSFT").description("Microsoft Corp.").lastUpdate(Date.valueOf(LocalDate.now())).build();

        List<Stock> stocks = Arrays.asList(stock1, stock2);

        stockExchange = StockExchange.builder()
                .id(1L)
                .name("Nasdaq")
                .description("American stock exchange based in New York City.")
                .liveInMarket(false)
                .stocks(stocks)
                .build();

        StockExchangeUpdateDto updateDto = new StockExchangeUpdateDto();
        updateDto.setStockNames(Arrays.asList("MSFT","NVDA"));
        String updateJson = objectMapper.writeValueAsString(updateDto);

        when(stockService.findAllByName(List.of("MSFT", "NVDA"))).thenReturn(stocks);
        when(stockExchangeService.updateStockExchange("Nasdaq", updateDto, stocks)).thenReturn(stockExchange);

        MvcResult result = mockMvc.perform(put("/api/v1/stock-exchange/{name}", "Nasdaq")
                        .contentType("application/json")
                        .content(updateJson))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        StockExchangeDto response = objectMapper.readValue(json, StockExchangeDto.class);

        assertThat(response.getName(), equalTo("Nasdaq"));
        assertThat(response.getStocks().size(), equalTo(2));
        assertThat(response.getStocks().get(0).getName(), equalTo("NVDA"));
        assertThat(response.getStocks().get(1).getName(), equalTo("MSFT"));
    }

}
