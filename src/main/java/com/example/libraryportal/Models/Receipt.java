package com.example.libraryportal.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Receipt {
    @Id
    private Long id;

    private Long ISBN;
    private LocalDate dateBorrowed;
    private LocalDate dueDate;
    private LocalDate dateReturned;

}
