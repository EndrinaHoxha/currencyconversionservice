package com.example.currencyconversionservice.services;

import com.example.currencyconversionservice.model.dto.CurrencyConverterResponse;

public interface CurrencyConverterService {

  Double currencyConverter(Double amount, String to);

  // void save (CurrencyConverterResponse converterDto);
}
