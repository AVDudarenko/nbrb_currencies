package com.example.nbrb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);
    private final CurrencyService service;

    public CurrencyController(CurrencyService service) {
        this.service = service;
    }

    @GetMapping("/load/{date}")
    public ResponseEntity<String> loadRates(@PathVariable String date) {
        boolean isSuccess = service.loadRatesForDate(date);
        if (isSuccess) {
            logger.info("Data loaded successfully for date: " + date);
            return ResponseEntity.ok("Data loaded successfully for date: " + date);
        }
        logger.error("Failed to load data for date: " + date);
        return ResponseEntity.status(500).body("Failed to load data for date: " + date);
    }

    @GetMapping("/{date}/{currencyCode}")
    public ResponseEntity<CurrencyRate> getRate(@PathVariable String date, @PathVariable String currencyCode) {
        CurrencyRate rate = service.getRateForDateAndCurrency(date, currencyCode);
        if (rate != null) {
            logger.info("Rate found for date: " + date + ", currencyCode: " + currencyCode);
            return ResponseEntity.ok(rate);
        }
        logger.error("Rate not found for date: " + date + ", currencyCode: " + currencyCode);
        return ResponseEntity.notFound().build();
    }
}
