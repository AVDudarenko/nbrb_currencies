package com.example.nbrb;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long curId;
    private Date date;
    private String curAbbreviation;
    private Integer curScale;
    private String curName;
    private Double curOfficialRate;
}
