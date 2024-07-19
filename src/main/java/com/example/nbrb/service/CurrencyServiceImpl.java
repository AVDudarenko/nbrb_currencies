package com.example.nbrb.service;

import com.example.nbrb.domain.entity.CurrencyRate;
import com.example.nbrb.errors.CurrencyRateException;
import com.example.nbrb.mapper.CurrencyRateMapper;
import com.example.nbrb.repo.CurrencyRateRepository;
import com.example.nbrb.rest.CurrencyApiClient;
import com.example.nbrb.rest.CurrencyRateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service for managing currency rates.
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);
    private final CurrencyRateRepository currencyRateRepository;
    private final CurrencyApiClient currencyApiClient;
    private final CurrencyRateMapper currencyRateMapper;

    public CurrencyServiceImpl(
            CurrencyRateRepository currencyRateRepository,
            CurrencyApiClient currencyApiClient,
            CurrencyRateMapper currencyRateMapper
    ) {
        this.currencyRateRepository = currencyRateRepository;
        this.currencyApiClient = currencyApiClient;
        this.currencyRateMapper = currencyRateMapper;
    }

    /**
     * Loads and saves currency rates for a specific date.
     *
     * @param date the date to load rates for
     * @return true if successful, false otherwise
     */
    @Transactional
    public boolean loadRatesForDate(String date) {
        try {
            CurrencyRateDto[] rateDtos = currencyApiClient.getRatesForDate(date);
            if (rateDtos != null && rateDtos.length > 0) {
                for (CurrencyRateDto rateDto : rateDtos) {
                    CurrencyRate rate = currencyRateMapper.toEntity(rateDto);
                    saveOrUpdateRate(rate);
                }
                return true;
            }
        } catch (Exception e) {
            logger.error("Failed to load rates for date: " + date, e);
            throw new CurrencyRateException("Failed to load rates for date: " + date, e);
        }
        return false;
    }

    /**
     * Retrieves the currency rate for a specific date and currency code.
     *
     * @param date         the date to retrieve the rate for
     * @param currencyCode the currency code
     * @return an Optional containing the CurrencyRate if found
     */
    @Transactional(readOnly = true)
    public Optional<CurrencyRate> getRateForDateAndCurrency(String date, String currencyCode) {
        try {
            CurrencyRateDto rateDto = currencyApiClient.getRateForDateAndCurrency(date, currencyCode);
            if (rateDto != null) {
                CurrencyRate rate = currencyRateMapper.toEntity(rateDto);
                return Optional.of(saveOrUpdateRate(rate));
            }
        } catch (Exception e) {
            logger.error("Failed to get rate for date: " + date + " and currency code: " + currencyCode, e);
            throw new CurrencyRateException("Failed to get rate for date: " + date + " and currency code: " + currencyCode, e);
        }
        return Optional.empty();
    }

    /**
     * Saves or updates a currency rate in the database.
     *
     * @param rate the CurrencyRate to save or update
     * @return the saved or updated CurrencyRate
     */
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
