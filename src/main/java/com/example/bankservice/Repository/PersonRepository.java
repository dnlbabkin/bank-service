package com.example.bankservice.Repository;

import com.example.bankservice.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findPersonById(Integer id);
}
