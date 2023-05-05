package com.example.libraryportal.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
class AccountTest {
    private Account account;
    Long ID = 1L;
    String studentID = "c1234567";
    String password = "testpass";
    @BeforeEach
    void setUp() {
        account = new Account();
        account.setAccountID(ID);
        account.setStudentId(studentID);
        account.setPassword(password);
    }
    @Test
    void RetriveBook(){
        assertAll(() -> assertEquals(1L, account.getAccountID()),
                () -> assertEquals("c1234567", account.getStudentId()),
                () -> assertEquals("testpass", account.getPassword()));

    }
    @Test
    void changeAccountPassword(){
        String newPassword = "testpass2";
        account.setPassword(newPassword);
        assertEquals(newPassword,account.getPassword());
    }
    @Test
    void changeAccountStudentID(){
        String newStudentID = "testuser2";
        account.setStudentId(newStudentID);
        assertEquals(newStudentID,account.getStudentId());
    }

    @Test
    void borrowBook(){
        Book book =new Book();
        book.setBookISBN(1L);
        book.setBookName("testbook");
        book.setBookAuthor("testauthor");
        book.setBookFee(20.00);

        account.borrowBook(book);
        assertEquals(1,account.getBorrowedBooks().size());

    }
    @Test
    void borrowBookwithNull(){
        assertThrows(RuntimeException.class, () -> account.borrowBook(null),
        "Error: Book is Null");
    }

}