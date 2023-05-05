package com.example.libraryportal.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountID;
    @JsonProperty("studentId")
    @Column(unique = true)
    private String studentId;

    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_account",
            joinColumns = @JoinColumn(name = "accountID"),
            inverseJoinColumns = @JoinColumn(name = "BookISBN"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    Set<Book> borrowedBooks;

    @JsonIgnore
    public void borrowBook(Book book){
        if(borrowedBooks == null){
            borrowedBooks = new HashSet<>();
        }
        if(book == null){
             throw new RuntimeException("Error: Book is Null");
        }
        borrowedBooks.add(book);
    }
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Receipt> receiptList = new ArrayList<>();
    public Account(){

    }
    public Account(String studentId) {
        this.studentId = studentId;
    }


}
