package com.example.bankservice.Controllers;

import com.example.bankservice.AllData;
import com.example.bankservice.Clients.CBRClient;
import com.example.bankservice.Entity.Account;
import com.example.bankservice.Services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class CBRController {

    private final CBRClient soapClient;
    private final AccountService accountService;


    @PostMapping(value = "/account", produces = {MediaType.APPLICATION_XML_VALUE})
    public AllData.MainIndicatorsVR.Currency invokeSoapClient() throws JAXBException, IOException {
        AllData.MainIndicatorsVR.Currency env = soapClient.getCurrencyData();
        env.getUSD().getCurs();

        return env;
    }

    @GetMapping(value = "/account/USD/", produces = {MediaType.APPLICATION_XML_VALUE})
    public Account getUSD() throws JAXBException, IOException {
        Account account = new Account();

        return accountService.saveUSD(account);
    }
}
