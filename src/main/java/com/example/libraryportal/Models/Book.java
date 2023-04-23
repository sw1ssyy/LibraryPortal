package com.example.libraryportal.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookISBN;
    private String bookName;
    private Double bookFee;

    private String bookAuthor;

    @ManyToMany(mappedBy = "borrowedBooks")
    @JsonIgnore
    @ToString.Exclude
    Set<Account> accountsBorrowed;
}
