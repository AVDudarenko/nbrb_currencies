package com.example.nbrb.controllers;

import com.example.nbrb.domain.entity.CurrencyRate;
import com.example.nbrb.errors.CurrencyRateException;
import com.example.nbrb.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * REST controller for managing currency rates.
 */
@RestController
@RequestMapping("/api/currency-rate")
public class CurrencyRateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyRateController.class);
    private final CurrencyService service;

    public CurrencyRateController(final CurrencyService service) {
        this.service = service;
    }

    /**
     * Loads currency rates for a specific date.
     *
     * @param date the date to load rates for
     * @return a ResponseEntity with the status of the operation
     */
    @GetMapping("/load/{date}")
    public ResponseEntity<String> loadRates(@PathVariable String date) {
        try {
            boolean isSuccess = service.loadRatesForDate(date);
            if (isSuccess) {
                LOGGER.info("Data loaded successfully for date: " + date);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Data loaded successfully for date: " + date);
            } else {
                LOGGER.error("Failed to load data for date: " + date);
                return ResponseEntity.status(500).body("Failed to load data for date: " + date);
            }
        } catch (CurrencyRateException e) {
            LOGGER.error("Error occurred while loading rates for date: " + date, e);
            return ResponseEntity.status(500).body("Error occurred while loading rates for date: " + date);
        }
    }

    /**
     * Retrieves the currency rate for a specific date and currency code.
     *
     * @param date         the date to retrieve the rate for
     * @param currencyCode the currency code
     * @return a ResponseEntity containing the CurrencyRate if found, or a 404 status if not found
     */
    @GetMapping("/{date}/{currencyCode}")
    public ResponseEntity<CurrencyRate> getRate(@PathVariable String date, @PathVariable String currencyCode) {
        try {
            Optional<CurrencyRate> rate = service.getRateForDateAndCurrency(date, currencyCode);
            if (rate.isPresent()) {
                LOGGER.info("Rate found for date: " + date + ", currencyCode: " + currencyCode);
                return ResponseEntity.ok(rate.get());
            } else {
                LOGGER.error("Rate not found for date: " + date + ", currencyCode: " + currencyCode);
                return ResponseEntity.notFound().build();
            }
        } catch (CurrencyRateException e) {
            LOGGER.error("Error occurred while fetching rate for date: " + date + " and currency code: " + currencyCode, e);
            return ResponseEntity.status(500).body(null);
        }
    }
}
