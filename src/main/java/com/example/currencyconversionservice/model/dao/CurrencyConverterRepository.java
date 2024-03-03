package com.example.currencyconversionservice.model.dao;

import com.example.currencyconversionservice.model.entities.CurrencyConverterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyConverterRepository extends JpaRepository<CurrencyConverterEntity, Long> {

}
