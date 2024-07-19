package com.example.nbrb.controllers;

import com.example.nbrb.domain.entity.CurrencyRate;
import com.example.nbrb.service.CurrencyService;
import com.example.nbrb.service.errors.CurrencyRateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyRateController.class)
class CurrencyRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @Test
    void loadRatesForDate_success() throws Exception {
        given(currencyService.loadRatesForDate(anyString())).willReturn(true);

        mockMvc.perform(get("/api/currency-rate/load/2023-01-10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Data loaded successfully for date: 2023-01-10"));
    }

    @Test
    void loadRatesForDate_fail() throws Exception {
        given(currencyService.loadRatesForDate(anyString())).willReturn(false);

        mockMvc.perform(get("/api/currency-rate/load/2023-01-10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to load data for date: 2023-01-10"));
    }

    @Test
    void loadRatesForDate_exception() throws Exception {
        doThrow(new CurrencyRateException("Error")).when(currencyService).loadRatesForDate(anyString());

        mockMvc.perform(get("/api/currency-rate/load/2023-01-10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error occurred while loading rates for date: 2023-01-10"));
    }

    @Test
    void getRateForDateAndCurrency_success() throws Exception {

        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setDate(new java.util.Date());
        currencyRate.setCurAbbreviation("USD");
        currencyRate.setCurOfficialRate(2.5);

        given(currencyService.getRateForDateAndCurrency(anyString(), anyString())).willReturn(Optional.of(currencyRate));
        mockMvc.perform(get("/api/currency-rate/2023-01-10/USD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"curAbbreviation\":\"USD\",\"curOfficialRate\":2.5}"));
    }

    @Test
    void getRateForDateAndCurrency_notFound() throws Exception {
        given(currencyService.getRateForDateAndCurrency(anyString(), anyString())).willReturn(Optional.empty());

        mockMvc.perform(get("/api/currency-rate/2023-01-10/USD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getRateForDateAndCurrency_exception() throws Exception {
        doThrow(new CurrencyRateException("Error")).when(currencyService).getRateForDateAndCurrency(anyString(), anyString());

        mockMvc.perform(get("/api/currency-rate/2023-01-10/USD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}