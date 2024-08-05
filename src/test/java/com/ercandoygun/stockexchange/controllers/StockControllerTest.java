package com.ercandoygun.stockexchange.controllers;

import com.ercandoygun.stockexchange.auth.util.JwtUtil;
import com.ercandoygun.stockexchange.entities.Stock;
import com.ercandoygun.stockexchange.models.*;
import com.ercandoygun.stockexchange.models.converter.StockConverter;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = StockController.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private StockService stockService;

    @SpyBean
    private StockConverter stockConverter;

    @SpyBean
    private ModelMapper modelMapper;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Stock stock;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void updateStockByName_ReturnsUpdated() throws Exception {
        stock = Stock.builder().id(2).currentPrice(BigDecimal.valueOf(20d)).name("NVDA").description("NVIDIA Corp.").lastUpdate(Date.valueOf(LocalDate.now())).build();

        StockUpdateDto updateDto = new StockUpdateDto();
        updateDto.setCurrentPrice(BigDecimal.valueOf(20d));
        String updateJson = objectMapper.writeValueAsString(updateDto);

        when(stockService.update(updateDto, "NVDA")).thenReturn(stock);

        MvcResult result = mockMvc.perform(put("/api/v1/stock/{name}", "NVDA")
                        .contentType("application/json")
                        .content(updateJson))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        StockResponseDto response = objectMapper.readValue(json, StockResponseDto.class);
        assertThat(response.getCurrentPrice(), equalTo(BigDecimal.valueOf(20d)));
    }

    @Test
    public void createStock_ReturnsCreatedStock() throws Exception {
        stock = Stock.builder().id(2).currentPrice(BigDecimal.valueOf(10d)).name("DEF").description("Default Corp.").lastUpdate(Date.valueOf(LocalDate.now())).build();

        StockRequestDto requestDto = new StockRequestDto();
        requestDto.setName("DEF");
        requestDto.setDescription("Default Corp.");
        requestDto.setCurrentPrice(BigDecimal.TEN);

        String json = objectMapper.writeValueAsString(requestDto);

        when(stockService.create(requestDto)).thenReturn(stock);

        MvcResult result = mockMvc.perform(post("/api/v1/stock")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        StockResponseDto response = objectMapper.readValue(responseJson, StockResponseDto.class);

        assertThat(response.getName(), equalTo("DEF"));
        assertThat(response.getCurrentPrice(), equalTo(BigDecimal.valueOf(10d)));
    }


    @Test
    public void deleteStock_deletesTheGivenStock() throws Exception {
        mockMvc.perform(delete("/api/v1/stock/{name}", "DEF")
                        .contentType("application/json"));

        verify(stockService).delete("DEF");
    }

}
