package com.example.bankservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class PersonAccountRequest {

    @JsonProperty("currency")
    String initialCurrency;

    @JsonProperty("amount")
    BigDecimal initialPayment;
}
