package com.example.libraryportal.Service;

import com.example.libraryportal.Models.Book;
import com.example.libraryportal.Models.BookRepo;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class BookService {
    private BookRepo bookRepo;

    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> SearchBookByName(String title){
        if(title == null || title.equals("")){
            return bookRepo.findAll();
        }
        return bookRepo.findBookByBookName(title);
    }

    public Book findBookByID(Long id){
        return bookRepo.findBookByBookISBN(id);
    }
}
