package com.example.nbrb.rest;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Client for accessing the National Bank of the Republic of Belarus (NBRB) API to retrieve currency rates.
 */
@Component
public class CurrencyApiClient {

    private final RestTemplate restTemplate;

    public CurrencyApiClient() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Retrieves currency rates for a specific date from the NBRB API.
     *
     * @param date the date to retrieve rates for
     * @return an array of CurrencyRate objects
     */
    public CurrencyRateDto[] getRatesForDate(String date) {
        String url = "https://api.nbrb.by/exrates/rates?ondate=" + date + "&periodicity=0";
        return restTemplate.getForObject(url, CurrencyRateDto[].class);
    }

    /**
     * Retrieves a specific currency rate for a given date and currency code from the NBRB API.
     *
     * @param date         the date to retrieve the rate for
     * @param currencyCode the code of the currency
     * @return a CurrencyRate object
     */
    public CurrencyRateDto getRateForDateAndCurrency(String date, String currencyCode) {
        String url = "https://api.nbrb.by/exrates/rates/" + currencyCode + "?parammode=2&ondate=" + date;
        return restTemplate.getForObject(url, CurrencyRateDto.class);
    }
}
