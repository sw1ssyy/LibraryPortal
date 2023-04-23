package com.example.libraryportal.Service;

import com.example.libraryportal.Models.Account;
import com.example.libraryportal.Models.AccountRepo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AccountService {
    private AccountRepo accountRepo;

    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Boolean checkAccountExists(String username, String Password){
        return accountRepo.existsAccountByAccountUserNameAndAccountPassword(username,Password);
    }

    public Account findAccountByUsername(String username){
        return accountRepo.findAccountByAccountUserName(username);
    }
}
