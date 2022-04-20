package com.example.bankservice.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transfer")
public class PersonAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Long id;

    @JsonProperty("accountNumber")
    @Column(name = "accountnumber")
    String accountNumber;

    @Column(name = "currentcurrency")
    @JsonProperty("currentCurrency")
    String currentCurrency;

    @JsonProperty("currentAmount")
    @Column(name = "currentamount")
    BigDecimal currentAmount;

}
