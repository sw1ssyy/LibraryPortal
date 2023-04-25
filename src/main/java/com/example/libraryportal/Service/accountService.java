package com.example.libraryportal.Service;

import com.example.libraryportal.Models.Account;
import com.example.libraryportal.Models.AccountRepo;
import com.example.libraryportal.Models.Invoice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class accountService {
    private AccountRepo accountRepo;
    private final RestTemplate restTemplate;

    public accountService(AccountRepo accountRepo, RestTemplate restTemplate) {
        this.accountRepo = accountRepo;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<Account> createNewAccount(Account account) {
        if(account.getStudentId() == null || account.getStudentId().isEmpty()){
            throw new RuntimeException("ERROR: Student ID is not Valid!");
        }
        account.setPassword("test");
        accountRepo.save(account);
        return ResponseEntity.ok(account);
    }


    public Boolean checkAccountExists(String username, String Password){
        return accountRepo.existsAccountByStudentIdAndPassword(username,Password);
    }
    public List<Account> SearchStudentByName(String name){
        if(name == null || name.equals("")){
            return accountRepo.findAll();
        }
        return accountRepo.findAllByStudentId(name);
    }

    public void updateAccount(@PathVariable Long id, Account account){
        Account updatedAccount = accountRepo.findAccountByAccountID(id);
        if(updatedAccount == null){
            throw new RuntimeException("Account: '" + account.getStudentId() + "' Does not Exist");
        }
        updatedAccount.setStudentId(account.getStudentId());
        updatedAccount.setPassword(account.getPassword());
        accountRepo.save(updatedAccount);
    }

    public Account findAccountByUsername(String username){
        return accountRepo.findAccountByStudentId(username);
    }

    public Invoice createCourseFeeInvoice(Invoice invoice){
        return restTemplate.postForObject("http://localhost:8081/invoices/", invoice , Invoice.class);
    }
}
