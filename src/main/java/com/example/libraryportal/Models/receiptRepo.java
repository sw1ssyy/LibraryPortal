package com.example.libraryportal.Models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface receiptRepo extends JpaRepository<Receipt, Long> {
    Receipt findReceiptById(Long id);
    List<Receipt>getAllByStudentID(Account account);
}
