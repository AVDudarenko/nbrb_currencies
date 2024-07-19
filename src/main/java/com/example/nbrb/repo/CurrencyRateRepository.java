package com.example.nbrb.repo;

import com.example.nbrb.domain.entity.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

/**
 * Repository interface for managing CurrencyRate entities.
 */
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {

    /**
     * Finds a currency rate by date and currency abbreviation.
     *
     * @param date            the date of the currency rate
     * @param curAbbreviation the abbreviation of the currency
     * @return an Optional containing the CurrencyRate if found
     */
    Optional<CurrencyRate> findByDateAndCurAbbreviation(Date date, String curAbbreviation);
}
