package com.example.libraryportal;

import com.example.libraryportal.Models.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Set;

@Configuration
public class DatabaseData {
@Bean
    CommandLineRunner initDatabase(AccountRepo accountRepo, receiptRepo receiptrepo){
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
        c3538468.setStudentId("c3538468");
        c3538468.setPassword("test");
        c3538468.borrowBook(book1984);
        Account c1234567 = new Account();
        c1234567.setStudentId("c1234567");
        c1234567.setPassword("test");
        c1234567.borrowBook(ReadyPlayerOne);

        Account admin = new Account();
        admin.setStudentId("admin");
        admin.setPassword("admin");

        Receipt testReceipt = new Receipt();
        testReceipt.setStudentID(c3538468);
        testReceipt.setISBN(1L);
        testReceipt.setDateDue(LocalDate.now().minusYears(1));
        testReceipt.setDateBorrowed(LocalDate.now().minusYears(2));


        accountRepo.saveAllAndFlush(Set.of(admin,c3538468,c1234567));
        receiptrepo.saveAllAndFlush(Set.of(testReceipt));

    };

}
    @Bean
    public RestTemplate template(RestTemplateBuilder builder) {
        return builder.build();
    }
}


