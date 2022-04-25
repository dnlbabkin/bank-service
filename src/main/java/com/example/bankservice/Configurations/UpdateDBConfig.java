package com.example.bankservice.Configurations;

import com.example.bankservice.AllData;
import com.example.bankservice.Clients.CBRClient;
import com.example.bankservice.Entity.Account;
import com.example.bankservice.Repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Slf4j
@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class UpdateDBConfig {

    private final AccountRepository accountRepository;
    private final CBRClient soapClient;

    @Scheduled(fixedRateString = "${sample.schedule.string}", initialDelayString = "${initialdelay.string}")
    public void updateDataBase() throws JAXBException, IOException {
        Account account = new Account();
        AllData.MainIndicatorsVR envelope = soapClient.getCurrencyData();

        account.setUsd(envelope.getCurrency().getUSD().getCurs());

        accountRepository.save(account);

        log.info("Finish writing Course: [" + account.getUsd()
                + "] to Database. Entity[ Id = " + account.getId() + ", USD = "
                + account.getUsd() + " ]");

    }
}
