package com.example.libraryportal.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="account_fk",referencedColumnName="studentID")
    @ToString.Exclude
    private Account studentID;
    private Long ISBN;
    private LocalDate DateBorrowed;

    private LocalDate DateDue;

}
