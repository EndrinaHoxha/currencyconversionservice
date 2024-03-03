package com.example.currencyconversionservice.model.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HashMapConverter implements AttributeConverter<Map<String, Double>, String> {
  private static final Logger logger = LoggerFactory.getLogger(HashMapConverter.class);
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(Map<String, Double> customerInfo) {

    String customerInfoJson = null;
    try {
      customerInfoJson = objectMapper.writeValueAsString(customerInfo);
    } catch (final JsonProcessingException e) {
      logger.error("JSON writing error", e);
    }

    return customerInfoJson;
  }

  @Override
  public Map<String, Double> convertToEntityAttribute(String customerInfoJSON) {

    Map<String, Double> customerInfo = null;
    try {
      customerInfo =
          objectMapper.readValue(customerInfoJSON, new TypeReference<HashMap<String, Double>>() {});
    } catch (final IOException e) {
      logger.error("JSON reading error", e);
    }

    return customerInfo;
  }
}
