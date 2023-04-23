package com.example.libraryportal.Models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Long> {
List<Book> findBookByBookName(String title);
Book findBookByBookISBN(Long ISBN);
}
