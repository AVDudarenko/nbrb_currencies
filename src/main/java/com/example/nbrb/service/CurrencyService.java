package com.example.nbrb.service;

import com.example.nbrb.domain.entity.CurrencyRate;

import java.util.Optional;

/**
 * Service interface for managing currency rates.
 */
public interface CurrencyService {

    /**
     * Loads and saves currency rates for a specific date.
     *
     * @param date the date to load rates for
     * @return true if successful, false otherwise
     */
    boolean loadRatesForDate(String date);

    /**
     * Retrieves the currency rate for a specific date and currency code.
     *
     * @param date         the date to retrieve the rate for
     * @param currencyCode the currency code
     * @return an Optional containing the CurrencyRate if found
     */
    Optional<CurrencyRate> getRateForDateAndCurrency(String date, String currencyCode);

}
