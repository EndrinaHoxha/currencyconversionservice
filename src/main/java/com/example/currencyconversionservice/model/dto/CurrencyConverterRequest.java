package com.example.currencyconversionservice.model.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
public record CurrencyConverterRequest(
    @NotNull Double amount, @NotBlank @Size(max = 3) String to) {}
