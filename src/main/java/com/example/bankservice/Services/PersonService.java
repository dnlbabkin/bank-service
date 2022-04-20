package com.example.bankservice.Services;

import com.example.bankservice.Entity.Person;
import com.example.bankservice.Repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person savePerson(Person person){
        return personRepository.save(person);
    }

    public Person findPersonById(Integer id){
        return personRepository.findPersonById(id);
    }
}


