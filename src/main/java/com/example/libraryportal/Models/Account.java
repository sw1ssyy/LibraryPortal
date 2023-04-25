package com.example.libraryportal.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("studentId")
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
    public void borrowBook(Book book){
        if(borrowedBooks == null){
            borrowedBooks = new HashSet<>();
        }
        borrowedBooks.add(book);
    }
    public Account(){

    }
    public Account(String studentId) {
        this.studentId = studentId;
    }


}
