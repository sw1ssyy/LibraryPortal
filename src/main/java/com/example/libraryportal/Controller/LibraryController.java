package com.example.libraryportal.Controller;

import com.example.libraryportal.Models.Account;
import com.example.libraryportal.Models.Book;
import com.example.libraryportal.Models.Receipt;
import com.example.libraryportal.Service.BookService;
import com.example.libraryportal.Service.accountService;
import com.example.libraryportal.Service.receiptService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class LibraryController {

    private final BookService bookService;
    private final accountService accservice;
    private final RestTemplate restTemplate;
    private final receiptService receiptservice;

    public String currentUser = "";

    public LibraryController(accountService accservice, BookService bookService, RestTemplate restTemplate , receiptService receiptservice) {
        this.accservice = accservice;
        this.bookService = bookService;
        this.restTemplate = restTemplate;
        this.receiptservice = receiptservice;
    }

    @GetMapping(value = "/login")
    public ModelAndView LoginPage() {
        ModelAndView modelAndView = new ModelAndView("Login");
        modelAndView.addObject("account", new Account());
        return modelAndView;
    }

    @PostMapping(value = "/login")
    public ModelAndView checkLogin(Account account) {
        if (accservice.checkAccountExists(account.getAccountUserName(), account.getAccountPassword()) && !account.getAccountUserName().equals("admin")) {
            System.out.println("Account: '" + account.getAccountUserName() + "' Login Success!");
            currentUser = account.getAccountUserName();
            return getLibraryHomePage();
        } else if (account.getAccountUserName().equals("admin") && account.getAccountPassword().equals("admin")) {
            currentUser = account.getAccountUserName();
            return getAdminHomePage();
        } else
            System.out.println("Account: '" + account.getAccountUserName() + "' Is not Found!");
        return new ModelAndView("Login-Failed");
    }

    @GetMapping(value = "/home")
    public ModelAndView getLibraryHomePage() {
        Account currentAccount = accservice.findAccountByUsername(currentUser);
        ModelAndView modelAndView = new ModelAndView("Home-Page");
        modelAndView.addObject("user", currentAccount);
        return modelAndView;
    }
    @GetMapping(value = "home/edit/{id}")
    public String getEditProfilePage(@PathVariable Long id, Model model ){
        Account savedAccount = accservice.findAccountByUsername(currentUser);
        model.addAttribute("user", savedAccount);
        return "Edit-Profile";
    }
    @PostMapping(value = "home/edit/{id}")
    public ModelAndView putEditProfile(@PathVariable Long id,  Account account ){
        accservice.updateAccount(id, account);
        currentUser = account.getAccountUserName();
        return getLibraryHomePage();
    }

    @GetMapping(value = "/home/books")
    public ModelAndView getAllbooks(@Param("keyword") String keyword) {
        List<Book> BookList = bookService.SearchBookByName(keyword);
        ModelAndView modelAndView = new ModelAndView("Books");
        modelAndView.addObject("booklist", BookList);
        modelAndView.addObject("keyword", keyword);
        return modelAndView;
    }

    @GetMapping(value = "/home/books/{id}")
    public ModelAndView viewSingleBook(@PathVariable Long id){
        Book selectedBook = bookService.findBookByID(id);
        ModelAndView modelAndView = new ModelAndView("Book-Details");
        modelAndView.addObject("book", selectedBook);
        return modelAndView;
    }

    @GetMapping(value = "/home/books/{id}/borrow")
    public ModelAndView borrowSingleBook(@PathVariable Long id){
        Receipt newRecipt = new Receipt();
        Book selectedbook = bookService.findBookByID(id);
        newRecipt.setISBN(selectedbook.getBookISBN());
        newRecipt.setDateBorrowed(LocalDate.now());
        newRecipt.setDateDue(newRecipt.getDateBorrowed().plusMonths(1));
        newRecipt.setDateReturned(null);
        receiptservice.addRecipt(newRecipt);
        ModelAndView modelAndView = new ModelAndView("Receipt-Detail");
        modelAndView.addObject("recipt", newRecipt);
        modelAndView.addObject("book", selectedbook);
        return modelAndView;
    }

    @GetMapping(value = "/home/borrowed")
    public ModelAndView getBorrowedBooks(){
        ModelAndView modelAndView = new ModelAndView("BorrowedBooks");
        List<Receipt>receiptList = receiptservice.getAllRecipts();
        if(receiptList.isEmpty()){
            return new ModelAndView("BorrowedBooksEmpty");
        }
        else
            modelAndView.addObject("receiptlist", receiptList);
        return modelAndView;
    }

    @GetMapping(value = "/home/borrowed/return/{id}")
    public ModelAndView returnBorrowedBook(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("ReturnBook");
        return modelAndView;
    }





// ADMIN WEBPAGES

    @GetMapping(value = "/admin/home")
    private ModelAndView getAdminHomePage() {
        Account currentAccount = accservice.findAccountByUsername(currentUser);
        ModelAndView modelAndView = new ModelAndView("Admin-HomePage");
        modelAndView.addObject("user", currentAccount);
        return modelAndView;
    }

    @GetMapping(value = "/admin/books")
    private ModelAndView getAdminAllbooks(@Param("keyword") String keyword) {
        List<Book> BookList = bookService.SearchBookByName(keyword);
        ModelAndView modelAndView = new ModelAndView("Admin-Books");
        modelAndView.addObject("booklist", BookList);
        modelAndView.addObject("keyword", keyword);
        return modelAndView;
    }
    @GetMapping(value = "/admin/students")
    private ModelAndView getAdminAllStudent(@Param("keyword") String keyword) {
        List<Account> accounts = accservice.SearchStudentByName(keyword);
        ModelAndView modelAndView = new ModelAndView("Admin-Students");
        modelAndView.addObject("accounts", accounts);
        modelAndView.addObject("keyword", keyword);
        return modelAndView;
    }
}
