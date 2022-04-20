package com.example.bankservice.DTO;

import com.example.bankservice.Entity.Person;
import com.example.bankservice.Entity.PersonAccount;
import lombok.Value;

@Value
public class PersonInfo {
    Person person;
    PersonAccount personAccount;
}
