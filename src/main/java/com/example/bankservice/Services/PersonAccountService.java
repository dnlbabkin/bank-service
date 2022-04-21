package com.example.bankservice.Services;

import com.example.bankservice.AllData;
import com.example.bankservice.Classes.CardNumberGenerator;
import com.example.bankservice.Clients.CBRClient;
import com.example.bankservice.DTO.PersonAccountRequest;
import com.example.bankservice.DTO.PersonInfo;
import com.example.bankservice.Entity.Person;
import com.example.bankservice.Entity.PersonAccount;
import com.example.bankservice.Repository.PersonAccountRepository;
import com.example.bankservice.Repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;
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

    @Value("${personaccount.url}")
    private String url;

    @Value("${card.number.id}")
    private String cardNumberId;

    public PersonAccount savePersonAccount(PersonAccountRequest personAccountRequest) throws JAXBException {
        PersonAccount personAccount = new PersonAccount();
        String number = new CardNumberGenerator().generate(cardNumberId);

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
                .getForObject(url + person.getId(), PersonAccount.class);

        return new PersonInfo(person, personAccount);
    }

    public PersonAccount getAccountById(Long id) {
        return personAccountRepository.findAccountById(id);
    }

    public PersonAccount getAccountByAccountNumber(String accountNumber) {
        return personAccountRepository.findByAccountNumber(accountNumber);
    }
}
