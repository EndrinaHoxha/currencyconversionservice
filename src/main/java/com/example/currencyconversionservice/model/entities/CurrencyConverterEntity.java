package com.example.currencyconversionservice.model.entities;

import com.example.currencyconversionservice.model.mapper.HashMapConverter;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "currency_converter")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class CurrencyConverterEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "success")
  private Boolean success;

  @Column(name = "timestamp")
  private Integer timestamp;

  @Column(name = "base", length = 3, nullable = false)
  private String base;

  @Column(name = "date", nullable = false)
  private Date date;

  @Column(name = "rates", length = 10000, nullable = false)
  @Convert(converter = HashMapConverter.class)
  private Map<String, Double> rates;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    CurrencyConverterEntity that = (CurrencyConverterEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
