package com.example.bankservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class PersonAccountRequest {

    @JsonProperty("initialCurrency")
    String initialCurrency;

    @JsonProperty("initialPayment")
    BigDecimal initialPayment;
}
