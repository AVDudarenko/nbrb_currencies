package com.example.nbrb;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);
    private final CurrencyRateRepository currencyRateRepository;
    private final CurrencyApiClient currencyApiClient;

    public CurrencyServiceImpl(CurrencyRateRepository currencyRateRepository, CurrencyApiClient currencyApiClient) {
        this.currencyRateRepository = currencyRateRepository;
        this.currencyApiClient = currencyApiClient;
    }

    @Transactional
    public boolean loadRatesForDate(String date) {
        try {
            CurrencyRate[] rates = currencyApiClient.getRatesForDate(date);
            if (rates != null && rates.length > 0) {
                for (CurrencyRate rate : rates) {
                    saveOrUpdateRate(rate);
                }
                return true;
            }
        } catch (Exception e) {
            logger.error("Failed to load rates for date: " + date, e);
        }
        return false;
    }

    public CurrencyRate getRateForDateAndCurrency(String date, String currencyCode) {
        try {
            CurrencyRate rate = currencyApiClient.getRateForDateAndCurrency(date, currencyCode);
            if (rate != null) {
                return saveOrUpdateRate(rate);
            }
        } catch (Exception e) {
            logger.error("Failed to get rate for date: " + date + " and currency code: " + currencyCode, e);
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
