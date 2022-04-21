package com.example.bankservice.Controllers;

import com.example.bankservice.DTO.PersonAccountRequest;
import com.example.bankservice.Entity.PersonAccount;
import com.example.bankservice.Services.PersonAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import java.util.List;

@RestController
@RequestMapping("/person-account")
@RequiredArgsConstructor
public class PersonAccountController {

    private final PersonAccountService personAccountService;


    @PostMapping(value = "/")
    public PersonAccount setAccount(@RequestBody PersonAccountRequest account) throws JAXBException {
        return personAccountService.savePersonAccount(account);
    }


    @GetMapping("all-accounts")
    public List<PersonAccount> getAllAccounts(){
        return personAccountService.findAll();
    }

    @GetMapping("/{id}")
    public PersonAccount getAccountById(@PathVariable("id") Long id) {
        return personAccountService.getAccountById(id);
    }

    @RequestMapping(value = "/account/{accountNumber}", method = RequestMethod.GET)
    public PersonAccount findAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        return personAccountService.getAccountByAccountNumber(accountNumber);
    }
}
 