package com.example.nbrb.mapper;

import com.example.nbrb.domain.entity.CurrencyRate;
import com.example.nbrb.rest.CurrencyRateDto;
import org.springframework.stereotype.Component;

/**
 * Maps between CurrencyRateDto and CurrencyRate entities.
 */
@Component
public class CurrencyRateMapper {
    public CurrencyRate toEntity(CurrencyRateDto dto) {
        CurrencyRate entity = new CurrencyRate();
        entity.setDate(dto.getDate());
        entity.setCurAbbreviation(dto.getCurAbbreviation());
        entity.setCurScale(dto.getCurScale());
        entity.setCurName(dto.getCurName());
        entity.setCurOfficialRate(dto.getCurOfficialRate());
        return entity;
    }

    public CurrencyRateDto toDto(CurrencyRate entity) {
        CurrencyRateDto dto = new CurrencyRateDto();
        dto.setCurId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setCurAbbreviation(entity.getCurAbbreviation());
        dto.setCurScale(entity.getCurScale());
        dto.setCurName(entity.getCurName());
        dto.setCurOfficialRate(entity.getCurOfficialRate());
        return dto;
    }
}
