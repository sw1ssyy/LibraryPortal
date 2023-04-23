package com.example.libraryportal;

import com.example.libraryportal.Models.Account;
import com.example.libraryportal.Models.AccountRepo;
import com.example.libraryportal.Models.Book;
import com.example.libraryportal.Models.BookRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class DatabaseData {
@Bean
    CommandLineRunner initDatabase(AccountRepo accountRepo, BookRepo bookRepo){
    return args ->{
        Account mister = new Account();
        mister.setAccountUserName("Mister");
        mister.setAccountPassword("test");

        Account testaccount = new Account();
        testaccount.setAccountUserName("testaccount");
        testaccount.setAccountPassword("test");

        Book ReadyPlayerOne = new Book();
        ReadyPlayerOne.setBookName("Ready Player One");
        ReadyPlayerOne.setBookAuthor("Ernest Cline");
        ReadyPlayerOne.setBookFee(30.00);

        Book book1984 = new Book();
        book1984.setBookName("1984");
        book1984.setBookAuthor("George Orwell");
        book1984.setBookFee(19.84);

        accountRepo.saveAllAndFlush(Set.of(mister,testaccount));
        bookRepo.saveAllAndFlush(Set.of(ReadyPlayerOne,book1984));

    };
}
}
