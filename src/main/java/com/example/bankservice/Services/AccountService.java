package com.example.bankservice.Services;

import com.example.bankservice.AllData;
import com.example.bankservice.Clients.CBRClient;
import com.example.bankservice.Entity.Account;
import com.example.bankservice.Repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CBRClient soapClient;

    public Account saveUSD(Account account) throws JAXBException {
        AllData.MainIndicatorsVR envelope = soapClient.getCurrencyData();

        account.setUsd(envelope.getCurrency().getUSD().getCurs());

        return accountRepository.save(account);
    }
}
