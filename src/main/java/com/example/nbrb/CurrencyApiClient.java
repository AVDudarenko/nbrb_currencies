package com.example.nbrb;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CurrencyApiClient {

    private final RestTemplate restTemplate;

    public CurrencyApiClient() {
        this.restTemplate = new RestTemplate();
    }

    public CurrencyRate[] getRatesForDate(String date) {
        String url = "https://api.nbrb.by/exrates/rates?ondate=" + date + "&periodicity=0";
        return restTemplate.getForObject(url, CurrencyRate[].class);
    }

    public CurrencyRate getRateForDateAndCurrency(String date, String currencyCode) {
        String url = "https://api.nbrb.by/exrates/rates/" + currencyCode + "?parammode=2&ondate=" + date;
        return restTemplate.getForObject(url, CurrencyRate.class);
    }
}
