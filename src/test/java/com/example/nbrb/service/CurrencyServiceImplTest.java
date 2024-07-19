package com.example.nbrb.service;

import com.example.nbrb.domain.entity.CurrencyRate;
import com.example.nbrb.mapper.CurrencyRateMapper;
import com.example.nbrb.repo.CurrencyRateRepository;
import com.example.nbrb.rest.CurrencyApiClient;
import com.example.nbrb.rest.CurrencyRateDto;
import com.example.nbrb.service.errors.CurrencyRateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceImplTest {
    @Mock
    private CurrencyRateRepository currencyRateRepository;

    @Mock
    private CurrencyApiClient currencyApiClient;

    @Mock
    private CurrencyRateMapper currencyRateMapper;

    @InjectMocks
    private CurrencyServiceImpl currencyService;


    @Test
    void loadRatesForDate_success() {
        CurrencyRateDto[] rateDtos = {new CurrencyRateDto()};
        CurrencyRate rate = new CurrencyRate();

        given(currencyApiClient.getRatesForDate(anyString())).willReturn(rateDtos);
        given(currencyRateMapper.toEntity(any(CurrencyRateDto.class))).willReturn(rate);
        given(currencyRateRepository.saveAndFlush(any(CurrencyRate.class))).willReturn(rate);

        boolean result = currencyService.loadRatesForDate("2023-01-10");
        assertTrue(result);
    }

    @Test
    void loadRatesForDate_failure() {
        given(currencyApiClient.getRatesForDate(anyString())).willReturn(null);

        boolean result = currencyService.loadRatesForDate("2023-01-10");
        assertFalse(result);
    }

    @Test
    void loadRatesForDate_exception() {
        doThrow(new RuntimeException("Error")).when(currencyApiClient).getRatesForDate(anyString());

        Exception exception = assertThrows(CurrencyRateException.class, () -> {
            currencyService.loadRatesForDate("2023-01-10");
        });
        assertEquals("Failed to load rates for date: 2023-01-10", exception.getMessage());
    }

    @Test
    void getRateForDateAndCurrency_success() {
        CurrencyRateDto rateDto = new CurrencyRateDto();
        CurrencyRate rate = new CurrencyRate();

        given(currencyApiClient.getRateForDateAndCurrency(anyString(), anyString())).willReturn(rateDto);
        given(currencyRateMapper.toEntity(any(CurrencyRateDto.class))).willReturn(rate);
        given(currencyRateRepository.saveAndFlush(any(CurrencyRate.class))).willReturn(rate);

        Optional<CurrencyRate> result = currencyService.getRateForDateAndCurrency("2023-01-10", "USD");
        assertTrue(result.isPresent());
        assertEquals(rate, result.get());
    }

    @Test
    void getRateForDateAndCurrency_notFound() {
        given(currencyApiClient.getRateForDateAndCurrency(anyString(), anyString())).willReturn(null);

        Optional<CurrencyRate> result = currencyService.getRateForDateAndCurrency("2023-01-10", "USD");
        assertFalse(result.isPresent());
    }

    @Test
    void getRateForDateAndCurrency_exception() {
        doThrow(new RuntimeException("Error")).when(currencyApiClient).getRateForDateAndCurrency(anyString(), anyString());

        Exception exception = assertThrows(CurrencyRateException.class, () -> {
            currencyService.getRateForDateAndCurrency("2023-01-10", "USD");
        });
        assertEquals("Failed to get rate for date: 2023-01-10 and currency code: USD", exception.getMessage());
    }
}