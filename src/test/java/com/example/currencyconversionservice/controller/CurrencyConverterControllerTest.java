package com.example.currencyconversionservice.controller;

import com.example.currencyconversionservice.controllers.CurrencyConverterController;
import com.example.currencyconversionservice.model.dto.CurrencyConverterRequest;
import com.example.currencyconversionservice.services.CurrencyConverterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrencyConverterControllerTest {

  @Mock CurrencyConverterService currencyConverterService;
  @InjectMocks CurrencyConverterController currencyConverterController;

  @Test
  void testConvertCurrencyGetOK() {
    when(currencyConverterService.currencyConverter(30.0, "USD")).thenReturn(0.85);
    ResponseEntity<?> result = currencyConverterController.currencyConverter(30.0, "USD");
    assertEquals(HttpStatus.OK, result.getStatusCode());
  }

  @Test
  void testConvertCurrencyGetKO() {
    when(currencyConverterService.currencyConverter(30.0, "USD")).thenReturn(null);
    ResponseEntity<?> result = currencyConverterController.currencyConverter(30.0, "USD");
    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
  }

  @Test
  void testConvertCurrencyPostOK() {
    CurrencyConverterRequest converterRequest = new CurrencyConverterRequest(30.0, "USD");
    when(currencyConverterService.currencyConverter(
            converterRequest.amount(), converterRequest.to()))
        .thenReturn(0.85);
    ResponseEntity<?> result = currencyConverterController.currencyConverter(converterRequest);
    assertEquals(HttpStatus.OK, result.getStatusCode());
  }

  @Test
  void testConvertCurrencyPostKO() {
    CurrencyConverterRequest converterRequest = new CurrencyConverterRequest(30.0, "USD");
    when(currencyConverterService.currencyConverter(
            converterRequest.amount(), converterRequest.to()))
            .thenReturn(null);
    ResponseEntity<?> result = currencyConverterController.currencyConverter(converterRequest);
    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
  }
}
