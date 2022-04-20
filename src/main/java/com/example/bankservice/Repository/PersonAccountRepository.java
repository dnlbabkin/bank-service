package com.example.bankservice.Repository;


import com.example.bankservice.Entity.PersonAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonAccountRepository extends JpaRepository<PersonAccount, Long> {

    PersonAccount findByAccountNumberEquals(String accountNumber);

    PersonAccount findAccountById(Long id);
}
