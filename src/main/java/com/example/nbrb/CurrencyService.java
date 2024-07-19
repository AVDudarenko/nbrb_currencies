package com.example.nbrb;

public interface CurrencyService {

    boolean loadRatesForDate(String date);

    CurrencyRate getRateForDateAndCurrency(String date, String currencyCode);

}
