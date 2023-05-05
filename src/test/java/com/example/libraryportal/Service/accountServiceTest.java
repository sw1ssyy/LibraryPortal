package com.example.libraryportal.Service;

import com.example.libraryportal.Models.Account;
import com.example.libraryportal.Models.AccountRepo;
import com.example.libraryportal.Models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles
@SpringBootTest
class accountServiceTest {
    private Account account;
    Long id = 1L;
    String studentID = "c1234567";
    String password = "testpass";
    @Autowired
    accountService service;
    @MockBean
    AccountRepo repo;
    @BeforeEach
    void SetUp(){
        account = new Account();
        account.setAccountID(id);
        account.setStudentId(studentID);
        account.setPassword(password);

        Mockito.when(service.checkAccountExists(studentID,password))
                .thenReturn(true);
    }
    @Test
    @DisplayName("Checking account exists with valid data")
    void checkAccountExistsValid(){
        Boolean result = service.checkAccountExists(account.getStudentId(),account.getPassword());
        assertEquals(true,result);
    }
    @Test
    @DisplayName("Checking account exists with Invalid data")
    void checkAccountExistsInvalid(){
        String newPassword = "testpass2";
        Boolean result = service.checkAccountExists(account.getStudentId(),newPassword);
        assertEquals(false,result);
    }

}