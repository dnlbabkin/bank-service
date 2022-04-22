package com.example.bankservice;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external")
@Getter
@Setter
public class ExternalProperties {
    private String cbr;
    private String personaccount;
    private String cardnumber;
}


