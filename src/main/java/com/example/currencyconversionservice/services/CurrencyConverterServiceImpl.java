package com.example.currencyconversionservice.services;

import com.example.currencyconversionservice.config.AppConfig;
import com.example.currencyconversionservice.model.dto.CurrencyConverterResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

  private static final Logger logger = LoggerFactory.getLogger(CurrencyConverterServiceImpl.class);
  private final RestTemplate restTemplate;
  private final AppConfig config;
  private final CacheService cacheService;

  @Autowired
  public CurrencyConverterServiceImpl(
      AppConfig config, RestTemplate restTemplate, CacheService cacheService) {
    this.config = config;
    this.restTemplate = restTemplate;
    this.cacheService = cacheService;
  }

  @Override
  public Double currencyConverter(Double amount, String to) {
    Double rate = getRate(to);
    return amount * rate;
  }

  public Double getRate(String to) {

    if (cacheService.getValueFromCache(to).isEmpty()) {
      CompletableFuture<Double> rateFuture = callExchangeRateApi(to);
      return rateFuture.join();
    } else {
      logger.info("Rate retrieved from cache");
      return cacheService.getValueFromCache(to).get();
    }
  }

  @Async
  @CircuitBreaker(name = "currencyConverterService")
  public CompletableFuture<Double> callExchangeRateApi(String to) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            String uri = config.getApiUrl() + config.getApiKey();
            CurrencyConverterResponse currencyResponse =
                restTemplate.getForObject(uri, CurrencyConverterResponse.class);
            if (currencyResponse == null
                || currencyResponse.getRates() == null
                || !currencyResponse.getRates().containsKey(to)) {
              throw new IllegalStateException("Failed to fetch exchange rate");
            }

            String currencyTarget =
                currencyResponse.getRates().keySet().stream()
                    .filter(a -> a.equals(to))
                    .findFirst()
                    .get();
            Double rate = currencyResponse.getRates().get(currencyTarget);
            cacheService.addToCache(to, rate);

            return rate;

          } catch (Exception e) {
            logger.error("Error calling the exchange rate API", e);
            throw new IllegalStateException("Error calling the exchange rate API", e);
          }
        });
  }
}
