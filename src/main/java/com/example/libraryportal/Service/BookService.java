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
   // True if a book already exists, False for not existing in db
    Boolean checkbookExists(String title, String author){
        Book book = bookRepo.findBookByBookNameAndBookAuthor(title,author);
       if(book != null){
           return true;
       }
       else
           return false;
    }


    public Boolean addBook(Book book){
       Boolean bookstatus = checkbookExists(book.getBookName(), book.getBookAuthor());
        if(bookstatus.equals(true)){
            return false;
        }
        else {
            bookRepo.save(book);
            return true;
        }
    }

    public Book findBookByID(Long id){
        return bookRepo.findBookByBookISBN(id);
    }
}
