package com.example.bankservice.Services;

import com.example.bankservice.AllData;
import com.example.bankservice.Classes.CardNumberGenerator;
import com.example.bankservice.Clients.CBRClient;
import com.example.bankservice.DTO.PersonAccountRequest;
import com.example.bankservice.DTO.PersonInfo;
import com.example.bankservice.Entity.Person;
import com.example.bankservice.Entity.PersonAccount;
import com.example.bankservice.properties.ExternalProperties;
import com.example.bankservice.Repository.PersonAccountRepository;
import com.example.bankservice.Repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonAccountService {

    private final PersonRepository personRepository;
    private final CBRClient soapClient;
    private final PersonAccountRepository personAccountRepository;
    private final RestTemplate template;
    private final ExternalProperties externalProperties;

    public PersonAccount savePersonAccount(PersonAccountRequest personAccountRequest) throws JAXBException, IOException {
        PersonAccount personAccount = new PersonAccount();
        String number = new CardNumberGenerator().generate(externalProperties.getCardnumber());

        AllData.MainIndicatorsVR envelope = soapClient.getCurrencyData();

        BigDecimal usd = envelope.getCurrency().getUSD().getCurs();

        if (personAccountRequest.getInitialCurrency().equals("rub")) {
            personAccount.setAccountNumber(number);
            personAccount.setCurrentCurrency(personAccountRequest.getInitialCurrency());
            personAccount.setCurrentAmount(personAccountRequest.getInitialPayment());

            personAccountRepository.save(personAccount);
        } else if (personAccountRequest.getInitialCurrency().equals("usd")) {
            personAccount.setAccountNumber(number);
            personAccount.setCurrentCurrency(personAccountRequest.getInitialCurrency());
            personAccount.setCurrentAmount(personAccountRequest.getInitialPayment());

            BigDecimal result = personAccountRequest.getInitialPayment().divide(usd, 2, RoundingMode.HALF_UP);
            personAccount.setCurrentAmount(result);
            personAccountRepository.save(personAccount);
        }

        return personAccountRepository.findByAccountNumberEquals(personAccount.getAccountNumber());
    }

    public List<PersonAccount> findAll() {
        return personAccountRepository.findAll();
    }

    public PersonInfo getPersonAccount(Integer personId) {
        Person person = personRepository.findPersonById(personId);

        PersonAccount personAccount = template
                .getForObject(externalProperties.getPersonaccount() + person.getId(), PersonAccount.class);

        return new PersonInfo(person, personAccount);
    }

    public PersonAccount getAccountById(Long id) {
        return personAccountRepository.findAccountById(id);
    }

    public PersonAccount getAccountByAccountNumber(String accountNumber) {
        return personAccountRepository.findByAccountNumber(accountNumber);
    }
}
