package com.example.libraryportal.Controller;

import com.example.libraryportal.Models.Account;
import com.example.libraryportal.Models.Book;
import com.example.libraryportal.Service.BookService;
import com.example.libraryportal.Service.accountService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class LibraryController {
    private final accountService accountservice;
    private final BookService bookService;
    private final RestTemplate restTemplate;

    public String currentUser = "";

    public LibraryController(accountService accountservice, BookService bookService, RestTemplate restTemplate) {
        this.accountservice = accountservice;
        this.bookService = bookService;
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/login")
    public ModelAndView LoginPage() {
        ModelAndView modelAndView = new ModelAndView("Login");
        modelAndView.addObject("account", new Account());
        return modelAndView;
    }

    @PostMapping(value = "/login")
    public ModelAndView checkLogin(Account account) {
        if (accountservice.checkAccountExists(account.getAccountUserName(), account.getAccountPassword()) && !account.getAccountUserName().equals("admin")) {
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
    private ModelAndView getLibraryHomePage() {
        Account currentAccount = accountservice.findAccountByUsername(currentUser);
        ModelAndView modelAndView = new ModelAndView("Home-Page");
        modelAndView.addObject("user", currentAccount);
        return modelAndView;
    }
    @GetMapping(value = "home/edit/{id}")
    public String getEditProfilePage(@PathVariable Long id, Model model ){
        Account savedAccount = accountservice.findAccountByUsername(currentUser);
        model.addAttribute("user", savedAccount);
        return "edit-profile";
    }
    @PostMapping(value = "home/edit/{id}")
    public ModelAndView putEditProfile(@PathVariable Long id,  Account account ){
        accountservice.updateAccount(id, account);
        currentUser = account.getAccountUserName();
        return getLibraryHomePage();
    }

    @GetMapping(value = "/home/books")
    private ModelAndView getAllbooks(@Param("keyword") String keyword) {
        List<Book> BookList = bookService.SearchBookByName(keyword);
        ModelAndView modelAndView = new ModelAndView("Books");
        modelAndView.addObject("booklist", BookList);
        modelAndView.addObject("keyword", keyword);
        return modelAndView;
    }

    @GetMapping(value = "/home/books/{id}")
    private ModelAndView viewSingleBook(@PathVariable Long id){
        Book selectedBook = bookService.findBookByID(id);
        ModelAndView modelAndView = new ModelAndView("Book-Details");
        modelAndView.addObject("book", selectedBook);
        return modelAndView;
    }




// ADMIN WEBPAGES

    @GetMapping(value = "/admin/home")
    private ModelAndView getAdminHomePage() {
        Account currentAccount = accountservice.findAccountByUsername(currentUser);
        ModelAndView modelAndView = new ModelAndView("adminHomePage");
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
        List<Account> accounts = accountservice.SearchStudentByName(keyword);
        ModelAndView modelAndView = new ModelAndView("Admin-Students");
        modelAndView.addObject("accounts", accounts);
        modelAndView.addObject("keyword", keyword);
        return modelAndView;
    }
}
