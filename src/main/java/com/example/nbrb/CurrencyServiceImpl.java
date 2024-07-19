package com.example.nbrb;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRateRepository currencyRateRepository;

    public CurrencyServiceImpl(CurrencyRateRepository currencyRateRepository) {
        this.currencyRateRepository = currencyRateRepository;
    }

    @Transactional
    public boolean loadRatesForDate(String date) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.nbrb.by/exrates/rates?ondate=" + date + "&periodicity=0";
        CurrencyRate[] rates = restTemplate.getForObject(url, CurrencyRate[].class);

        if (rates != null && rates.length > 0) {
            for (CurrencyRate rate : rates) {
                saveOrUpdateRate(rate);
            }
            return true;
        }
        return false;
    }

    public CurrencyRate getRateForDateAndCurrency(String date, String currencyCode) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.nbrb.by/exrates/rates/" + currencyCode + "?parammode=2&ondate=" + date;
        CurrencyRate rate = restTemplate.getForObject(url, CurrencyRate.class);

        if (rate != null) {
            return saveOrUpdateRate(rate);
        }
        return null;
    }

    private CurrencyRate saveOrUpdateRate(CurrencyRate rate) {
        Optional<CurrencyRate> existingRateOpt = currencyRateRepository.findByDateAndCurAbbreviation(rate.getDate(), rate.getCurAbbreviation());
        if (existingRateOpt.isPresent()) {
            CurrencyRate existingRate = existingRateOpt.get();
            existingRate.setCurOfficialRate(rate.getCurOfficialRate());
            existingRate.setCurScale(rate.getCurScale());
            existingRate.setCurName(rate.getCurName());
            return currencyRateRepository.saveAndFlush(existingRate);
        } else {
            return currencyRateRepository.saveAndFlush(rate);
        }
    }
}
