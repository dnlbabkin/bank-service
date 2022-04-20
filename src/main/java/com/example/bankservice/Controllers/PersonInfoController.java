package com.example.bankservice.Controllers;

import com.example.bankservice.DTO.PersonInfo;
import com.example.bankservice.Services.PersonAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class PersonInfoController {

    private final PersonAccountService personAccountService;


    @GetMapping("/{id}")
    public PersonInfo getPersonAccount(@PathVariable("id") Integer personId) {
        return personAccountService.getPersonAccount(personId);
    }
}
