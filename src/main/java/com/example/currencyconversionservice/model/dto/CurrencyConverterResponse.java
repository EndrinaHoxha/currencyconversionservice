package com.example.currencyconversionservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;


@NoArgsConstructor
public class CurrencyConverterResponse {

  @JsonProperty private Boolean success;
  @JsonProperty private Integer timestamp;

  @JsonProperty private String base;
  @JsonProperty private Date date;
  @JsonProperty private Map<String, Double> rates;

  private CurrencyConverterRequest info;
  private Double result;

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public Integer getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Integer timestamp) {
    this.timestamp = timestamp;
  }

  public String getBase() {
    return base;
  }

  public void setBase(String base) {
    this.base = base;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Map<String, Double> getRates() {
    return rates;
  }

  public void setRates(Map<String, Double> rates) {
    this.rates = rates;
  }

  public Double getResult() {
    return result;
  }

  public void setResult(Double result) {
    this.result = result;
  }

  public CurrencyConverterRequest getInfo() {
    return info;
  }

  public void setInfo(CurrencyConverterRequest info) {
    this.info = info;
  }
}
