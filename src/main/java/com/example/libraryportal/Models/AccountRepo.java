package com.example.libraryportal.Models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account,Long> {
    Boolean existsAccountByAccountUserNameAndAccountPassword(String username, String Password);
    Account findAccountByAccountUserName(String username);
}
