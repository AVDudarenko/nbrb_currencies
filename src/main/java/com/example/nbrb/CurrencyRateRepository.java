package com.example.nbrb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    CurrencyRate findByDateAndCurAbbreviation(Date date, String curAbbreviation);
}
