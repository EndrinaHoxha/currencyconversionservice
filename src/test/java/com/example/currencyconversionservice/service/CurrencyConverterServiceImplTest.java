package com.example.currencyconversionservice.service;

import com.example.currencyconversionservice.config.AppConfig;
import com.example.currencyconversionservice.model.dto.CurrencyConverterRequest;
import com.example.currencyconversionservice.model.dto.CurrencyConverterResponse;
import com.example.currencyconversionservice.services.CacheService;
import com.example.currencyconversionservice.services.CurrencyConverterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrencyConverterServiceImplTest {

    @Mock
    RestTemplate restTemplate;
    @Mock
    CacheService cacheService;
    @Mock
    AppConfig appConfig;
    @InjectMocks
    CurrencyConverterServiceImpl currencyConverterService;


    @Test
    void testConvertCurrencyWhenCallingExchangeRateApi() {
        CurrencyConverterRequest converterRequest = new CurrencyConverterRequest(30.0, "USD");
        CurrencyConverterResponse converterResponse = new CurrencyConverterResponse();
        converterResponse.setBase("EUR");
        Map<String, Double> rates = new HashMap<>();
        rates.put("AUD", 1.566015);
        rates.put("CAD", 1.560132);
        rates.put("CHF", 1.154727);
        rates.put("CNY", 7.827874);
        rates.put("GBP", 0.882047);
        rates.put("JPY", 132.360679);
        rates.put("USD", 1.23396);
        converterResponse.setRates(rates);
        converterResponse.setSuccess(true);
        converterResponse.setDate(new Date());
        converterResponse.setTimestamp(1519328414);
        converterResponse.setResult(converterRequest.amount() * rates.get("USD"));
        when(appConfig.getApiKey()).thenReturn("apiKey");
        when(appConfig.getApiUrl()).thenReturn("apiUrl");

        when( restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(converterResponse);

        double result = currencyConverterService.currencyConverter(converterRequest.amount(), converterRequest.to());
        assertEquals(37.0188, result);
    }

    @Test
    void testConvertCurrencyWhenCallingExchangeRateApiThrowsException() {
        CurrencyConverterRequest converterRequest = new CurrencyConverterRequest(30.0, "USD");
        CurrencyConverterResponse converterResponse = new CurrencyConverterResponse();
        converterResponse.setBase("EUR");
        Map<String, Double> rates = new HashMap<>();
        rates.put("AUD", 1.566015);
        rates.put("CAD", 1.560132);
        rates.put("CHF", 1.154727);
        rates.put("CNY", 7.827874);
        rates.put("GBP", 0.882047);
        rates.put("JPY", 132.360679);
        rates.put("USD", 1.23396);
        converterResponse.setRates(rates);
        converterResponse.setSuccess(true);
        converterResponse.setDate(new Date());
        converterResponse.setTimestamp(1519328414);
        converterResponse.setResult(converterRequest.amount() * rates.get("USD"));
        when(appConfig.getApiKey()).thenReturn("apiKey");
        when(appConfig.getApiUrl()).thenReturn("apiUrl");

        when( restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenThrow(IllegalStateException.class);

        assertThrows(CompletionException.class,()->currencyConverterService.currencyConverter(converterRequest.amount(), converterRequest.to()));
    }

    @Test
    void testConvertCurrencyWhenCallingExchangeRateApiReturnsNull() {
        CurrencyConverterRequest converterRequest = new CurrencyConverterRequest(30.0, "USD");
        CurrencyConverterResponse converterResponse = new CurrencyConverterResponse();
        converterResponse.setBase("EUR");
        Map<String, Double> rates = new HashMap<>();
        rates.put("AUD", 1.566015);
        rates.put("CAD", 1.560132);
        rates.put("CHF", 1.154727);
        rates.put("CNY", 7.827874);
        rates.put("GBP", 0.882047);
        rates.put("JPY", 132.360679);
        rates.put("USD", 1.23396);
        converterResponse.setRates(rates);
        converterResponse.setSuccess(true);
        converterResponse.setDate(new Date());
        converterResponse.setTimestamp(1519328414);
        converterResponse.setResult(converterRequest.amount() * rates.get("USD"));
        when(appConfig.getApiKey()).thenReturn("apiKey");
        when(appConfig.getApiUrl()).thenReturn("apiUrl");

        when( restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(null);

        assertThrows(CompletionException.class,()->currencyConverterService.currencyConverter(converterRequest.amount(), converterRequest.to()));
    }

    @Test
    void testConvertCurrencyWhenCacheHasValue() {
        when(cacheService.getValueFromCache("USD")).thenReturn(Optional.of(0.85));

        double result = currencyConverterService.currencyConverter(30.0, "USD");
        assertEquals(25.5, result);
    }
}
