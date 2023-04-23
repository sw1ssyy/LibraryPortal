package com.example.libraryportal;

import com.example.libraryportal.Models.Account;
import com.example.libraryportal.Models.AccountRepo;
import com.example.libraryportal.Models.Book;
import com.example.libraryportal.Models.BookRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Configuration
public class DatabaseData {
@Bean
    CommandLineRunner initDatabase(AccountRepo accountRepo, BookRepo bookRepo){
    return args ->{


        Book ReadyPlayerOne = new Book();
        ReadyPlayerOne.setBookName("Ready Player One");
        ReadyPlayerOne.setBookAuthor("Ernest Cline");
        ReadyPlayerOne.setBookFee(30.00);

        Book book1984 = new Book();
        book1984.setBookName("1984");
        book1984.setBookAuthor("George Orwell");
        book1984.setBookFee(19.84);

        Account c3538468 = new Account();
        c3538468.setAccountUserName("c3538468");
        c3538468.setAccountPassword("test");
        c3538468.borrowBook(book1984);
        Account c1234567 = new Account();
        c1234567.setAccountUserName("c1234567");
        c1234567.setAccountPassword("test");
        c1234567.borrowBook(ReadyPlayerOne);

        Account admin = new Account();
        admin.setAccountUserName("admin");
        admin.setAccountPassword("admin");

        accountRepo.saveAllAndFlush(Set.of(admin,c3538468,c1234567));

    };

}
    @Bean
    public RestTemplate template(RestTemplateBuilder builder) {
        return builder.build();
    }
}


