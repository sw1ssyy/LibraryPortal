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

    /**
     *  Example of Dependency injection from the repo and restTemplate
     * @param accountRepo Used loose coupling with "Account" Repository
     * @param restTemplate Used loose Coopling with restTemplate for connecting to Student and Finance Portal
     */
    public accountService(AccountRepo accountRepo, RestTemplate restTemplate) {
        this.accountRepo = accountRepo;
        this.restTemplate = restTemplate;
    }

    /**
     * Used to Create a new Account from the Student Portal
     * @param account = the new Account being created from the Student ID entered during the Signing in page within the Student Portal
     * @return an ResponseEntity of ok to back when the account was successfully created.
     */
    public ResponseEntity<Account> createNewAccount(Account account) {
        if(account.getStudentId() == null || account.getStudentId().isEmpty()){
            throw new RuntimeException("ERROR: Student ID is not Valid!");
        }
        account.setPassword("test");
        accountRepo.save(account);
        return ResponseEntity.ok(account);
    }

    /**
     * Used to check when if a specific account is stored within the database
     * @param username - stores the username value
     * @param Password - stores the password value
     * @return Boolean value of the status of the potential existing account
     */
    public Boolean checkAccountExists(String username, String Password){
        return accountRepo.existsAccountByStudentIdAndPassword(username,Password);
    }

    /**
     * Method used for the Search bar within the admin All students page
     * @param name is used as a keyword when trying to search for students by StudentID
     * @return all the accounts stored if the keyword is empty, or only the accounts that have the same studentID as the keyword
     */
    public List<Account> SearchStudentByName(String name){
        if(name == null || name.equals("")){
            return accountRepo.findAll();
        }
        return accountRepo.findAllByStudentId(name);
    }

    /**
     * Method used to update an existing account on the database
     * @param id = path variable used when edting acount
     * @param account - account being edited
     */
    public void updateAccount(@PathVariable Long id, Account account){
        Account updatedAccount = accountRepo.findAccountByAccountID(id);
        if(updatedAccount == null){
            throw new RuntimeException("Account: '" + account.getStudentId() + "' Does not Exist");
        }
        updatedAccount.setStudentId(account.getStudentId());
        updatedAccount.setPassword(account.getPassword());
        accountRepo.save(updatedAccount);
    }

    /**
     *  Used to find an account by their StudentID
     * @param StudentID - StudentID being searched
     * @return the account that has been found.
     */
    public Account findAccountByStudentID(String StudentID){
        return accountRepo.findAccountByStudentId(StudentID);
    }


}
