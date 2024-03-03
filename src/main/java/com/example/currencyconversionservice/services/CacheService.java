package com.example.currencyconversionservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CacheService {

  private final CacheManager cacheManager;

  @Autowired
  public CacheService(CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  public void addToCache(String key, Double value) {
    cacheManager.getCache("rates").put(key, value);
  }

  public Optional<Double> getValueFromCache(String key) {
    return Optional.ofNullable(cacheManager.getCache("rates"))
        .map(cache -> cache.get(key, Double.class));
  }

  public void removeFromCache(String key) {
    cacheManager.getCache("rates").evict(key);
  }
}
