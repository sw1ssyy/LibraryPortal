package com.example.libraryportal.Service;

import com.example.libraryportal.Models.Account;
import com.example.libraryportal.Models.AccountRepo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
public class accountService {
    private AccountRepo accountRepo;

    public accountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Boolean checkAccountExists(String username, String Password){
        return accountRepo.existsAccountByAccountUserNameAndAccountPassword(username,Password);
    }
    public List<Account> SearchStudentByName(String name){
        if(name == null || name.equals("")){
            return accountRepo.findAll();
        }
        return accountRepo.findAllByAccountUserName(name);
    }

    public void updateAccount(@PathVariable Long id, Account account){
        Account updatedAccount = accountRepo.findAccountByAccountID(id);
        if(updatedAccount == null){
            throw new RuntimeException("Account: '" + account.getAccountUserName() + "' Does not Exist");
        }
        updatedAccount.setAccountUserName(account.getAccountUserName());
        updatedAccount.setAccountPassword(account.getAccountPassword());
        accountRepo.save(updatedAccount);
    }

    public Account findAccountByUsername(String username){
        return accountRepo.findAccountByAccountUserName(username);
    }
}
