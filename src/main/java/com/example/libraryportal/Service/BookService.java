package com.example.libraryportal.Service;

import com.example.libraryportal.Models.Book;
import com.example.libraryportal.Models.BookRepo;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class BookService {
    private BookRepo bookRepo;

    /**
     * An example of Dependency Injection
     * @param bookRepo - Instance of book Repository created by spring
     */
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    /**
     * Method used to search book by book name
     * @param name - name of the book
     * @return book with that name
     */

    public List<Book> SearchBookByName(String name){
        if(name == null || name.equals("")){
            return bookRepo.findAll();
        }
        return bookRepo.findBookByBookName(name);
    }
   // True if a book already exists, False for not existing in db

    /**
     * Method used to check if a book exists within the database
     * @param name - name of the book
     * @param author - author of the book
     * @return boolean status of the book being found (True = book exists, False = book does not exist)
     */
    Boolean checkbookExists(String name, String author){
        Book book = bookRepo.findBookByBookNameAndBookAuthor(name,author);
       if(book != null){
           return true;
       }
       else
           return false;
    }

    /**
     * Method used by the admin to add book to database
     * @param book book object being added
     * @return boolean status on the book addition
     */
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

    /**
     * Method used to find book by ISBN
     * @param ISBN - ISBN of the book
     * @return Book found
     */
    public Book findBookByID(Long ISBN){
        return bookRepo.findBookByBookISBN(ISBN);
    }
}
