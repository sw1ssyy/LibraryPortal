package com.example.libraryportal.Models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepo extends JpaRepository<Account,Long> {
    Boolean existsAccountByAccountUserNameAndAccountPassword(String username, String Password);
    Account findAccountByAccountUserName(String username);
    Account findAccountByAccountID(Long ID);

    List<Account>findAllByAccountUserName(String name);

}
