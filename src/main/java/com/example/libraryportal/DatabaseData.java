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
    CommandLineRunner initDatabase(BookRepo bookRepo,AccountRepo accountRepo, receiptRepo receiptrepo){
    return args ->{


        Book AppliedDataScience = new Book();
        AppliedDataScience.setBookName("Applied Data Science");
        AppliedDataScience.setBookAuthor("Martin Branchler");
        AppliedDataScience.setBookFee(30.00);

        Book FPC = new Book();
        FPC.setBookName("Functional Programming with C#:");
        FPC.setBookAuthor("George Orwell");
        FPC.setBookFee(19.84);

        Book SOA = new Book();
        SOA.setBookName("Service-Oriented Architecture: Analysis and Design for Services and Microservices");
        SOA.setBookAuthor("Thomas Erl");
        SOA.setBookFee(30.00);

        Book IntroductiontoAlgorithms = new Book();
        IntroductiontoAlgorithms.setBookName("Introduction to Algorithms");
        IntroductiontoAlgorithms.setBookAuthor("Ronald Rivest");
        IntroductiontoAlgorithms.setBookFee(30.00);

        Book SuperintelligencePathsDangersStrategies = new Book();
        SuperintelligencePathsDangersStrategies.setBookName("Super-intelligence: Paths Dangers & Strategies");
        SuperintelligencePathsDangersStrategies.setBookAuthor("Nick Bostrom");
        SuperintelligencePathsDangersStrategies.setBookFee(14.99);

        Account c3538468 = new Account();
        c3538468.setStudentId("c3538468");
        c3538468.setPassword("test");
        c3538468.borrowBook(FPC);


        Account admin = new Account();
        admin.setStudentId("admin");
        admin.setPassword("admin");

        Receipt testReceipt = new Receipt();
        testReceipt.setStudentID(c3538468);
        testReceipt.setISBN(1L);
        testReceipt.setDateDue(LocalDate.now().minusYears(1));
        testReceipt.setDateBorrowed(LocalDate.now().minusYears(2));


        accountRepo.saveAllAndFlush(Set.of(admin,c3538468));
        receiptrepo.saveAllAndFlush(Set.of(testReceipt));
        bookRepo.saveAllAndFlush(Set.of(AppliedDataScience,SOA,IntroductiontoAlgorithms,SuperintelligencePathsDangersStrategies));

    };

}
    @Bean
    public RestTemplate template(RestTemplateBuilder builder) {
        return builder.build();
    }
}


