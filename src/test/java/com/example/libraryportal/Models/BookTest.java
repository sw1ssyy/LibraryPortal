package com.example.libraryportal.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
class BookTest {
    private Book book;
    Long ISBN = 1L;
    String bookName = "testname";
    String bookAuthor = "testauthor";
    Double bookfee = 20.00;
    @BeforeEach
    void SetUp(){
        book = new Book();
        book.setBookFee(20.00);
        book.setBookISBN(ISBN);
        book.setBookName(bookName);
        book.setBookAuthor(bookAuthor);


    }
    @Test
    void RetriveBook(){
        assertAll(() -> assertEquals(1L, book.getBookISBN()),
                () -> assertEquals("testname", book.getBookName()),
                () -> assertEquals("testauthor", book.getBookAuthor()),
                () -> assertEquals(20.00, book.getBookFee()));
    }

    @Test
    void changeBookName(){
        String newBookName = "Testname2";
        book.setBookName(newBookName);
        assertEquals(newBookName,book.getBookName());
    }
    @Test
    void changeBookAuthor(){
        String newBookAuthor = "testuser2";
        book.setBookAuthor(newBookAuthor);
        assertEquals(newBookAuthor,book.getBookAuthor());
    }
    @Test
    void changeBookFee(){
        Double newBookFee = 20.00;
        book.setBookFee(newBookFee);
        assertEquals(newBookFee,book.getBookFee());
    }
}