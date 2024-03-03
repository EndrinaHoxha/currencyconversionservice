package com.example.currencyconversionservice.model.mapper;

import com.example.currencyconversionservice.model.dto.CurrencyConverterResponse;
import com.example.currencyconversionservice.model.entities.CurrencyConverterEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyConverterMapper {
  CurrencyConverterResponse toDto(CurrencyConverterEntity currencyConverter);

  CurrencyConverterEntity toEntity(CurrencyConverterResponse converterDto);
}
