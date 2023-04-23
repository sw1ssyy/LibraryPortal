package com.example.libraryportal.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountID;

    private String accountUserName;

    private String accountPassword;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_account",
            joinColumns = @JoinColumn(name = "accountID"),
            inverseJoinColumns = @JoinColumn(name = "BookISBN"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<Book> borrowedBooks;
    public void borrowBook(Book book){
        if(borrowedBooks == null){
            borrowedBooks = new HashSet<>();
        }
        borrowedBooks.add(book);
    }
}
