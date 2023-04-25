package com.example.libraryportal.Models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface receiptRepo extends JpaRepository<Receipt, Long> {
    Receipt findReceiptById(Long id);
}
