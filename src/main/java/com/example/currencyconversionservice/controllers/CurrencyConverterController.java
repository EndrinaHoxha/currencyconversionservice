package com.example.currencyconversionservice.controllers;

import com.example.currencyconversionservice.model.dto.CurrencyConverterRequest;
import com.example.currencyconversionservice.services.CurrencyConverterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@RestController
@RequestMapping(value = "/api")
@Api(
    value =
        "API to convert an arbitrary amount of a given base currency into a specified target currency.",
    produces = "application/json")
public class CurrencyConverterController {

  @Autowired private CurrencyConverterService currencyConverterService;

  @ApiOperation(
      value = " get rates from http://api.exchangeratesapi.io",
      produces = "application/json")
  @GetMapping(value = "/currencyConverter")
  public ResponseEntity<?> currencyConverter(
      @RequestParam Double amount, @RequestParam @Size(max = 3) String to) {
    try {
      Double result = currencyConverterService.currencyConverter(amount, to);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @ApiOperation(
      value = " get rates from http://api.exchangeratesapi.io",
      produces = "application/json")
  @PostMapping(value = "/currencyConverter")
  public ResponseEntity<?> currencyConverter(
      @RequestBody @Valid CurrencyConverterRequest converterRequest) {
    try {
      Double result =
          currencyConverterService.currencyConverter(
              converterRequest.amount(), converterRequest.to());
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
