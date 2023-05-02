package com.example.libraryportal.Controller;

import com.example.libraryportal.Models.Account;
import com.example.libraryportal.Models.Book;
import com.example.libraryportal.Models.Invoice;
import com.example.libraryportal.Models.Receipt;
import com.example.libraryportal.Service.BookService;
import com.example.libraryportal.Service.accountService;
import com.example.libraryportal.Service.receiptService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class LibraryController {

    private final BookService bookService;
    private final accountService accservice;
    private final receiptService receiptservice;

    public String currentUser = "";

    public LibraryController(accountService accservice, BookService bookService , receiptService receiptservice) {
        this.accservice = accservice;
        this.bookService = bookService;
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
        if (accservice.checkAccountExists(account.getStudentId(), account.getPassword()) && !account.getStudentId().equals("admin")) {
            System.out.println("Account: '" + account.getStudentId() + "' Login Success!");
            currentUser = account.getStudentId();
            return getLibraryHomePage();
        }
        else if (account.getStudentId().equals("admin") && account.getPassword().equals("admin")) {
            currentUser = account.getStudentId();
            return getAdminHomePage();
        }
        else
            System.out.println("Account: '" + account.getStudentId() + "' Is not Found!");
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
        currentUser = account.getStudentId();
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
        Receipt newReceipt = new Receipt();
        Book selectedbook = bookService.findBookByID(id);
        newReceipt.setISBN(selectedbook.getBookISBN());
        newReceipt.setDateBorrowed(LocalDate.now());
        newReceipt.setStudentID(accservice.findAccountByUsername(currentUser));
        newReceipt.setDateDue(newReceipt.getDateBorrowed().plusMonths(1));
        receiptservice.addRecipt(newReceipt);
        ModelAndView modelAndView = new ModelAndView("Receipt-Detail");
        modelAndView.addObject("recipt", newReceipt);
        modelAndView.addObject("book", selectedbook);
        return modelAndView;
    }

    @GetMapping(value = "/home/borrowed")
    public ModelAndView getBorrowedBooks(){
        ModelAndView modelAndView = new ModelAndView("BorrowedBooks");
        List<Receipt>receiptList = receiptservice.getAllReciptsByUser(accservice.findAccountByUsername(currentUser));
        if(receiptList.isEmpty()){
            return new ModelAndView("BorrowedBooks-Empty");
        }
        else
            modelAndView.addObject("receiptlist", receiptList);
        return modelAndView;
    }

    @GetMapping(value = "/home/borrowed/return/{id}")
    public ModelAndView returnBorrowedBook(@PathVariable Long id){
        Receipt returnedbookReceipt = receiptservice.findReceiptByID(id);
        if(LocalDate.now().isBefore(returnedbookReceipt.getDateDue())){
            receiptservice.deleteReceipt(returnedbookReceipt);
            return new ModelAndView("ReturnedBookNoFine");
        }
        else{
            System.out.println(currentUser + " has been fined!");
           Invoice invoice =  receiptservice.createOverdueInvoice(returnedbookReceipt,accservice.findAccountByUsername(currentUser));
           receiptservice.deleteReceipt(returnedbookReceipt);
           ModelAndView modelAndView = new  ModelAndView("ReturnBookFine");
            modelAndView.addObject("invoice", invoice);
            return modelAndView;
        }

    }



// API for Creating New Library Account
@PostMapping("/api/account")
ResponseEntity<Account> newAccount(@RequestBody Account account) {
        return accservice.createNewAccount(account);
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
    @GetMapping(value = "/admin/books/add")
    private ModelAndView getAdminAddBook(){
        ModelAndView modelAndView = new ModelAndView("Admin-AddBook");
        modelAndView.addObject("book" , new Book());
        return modelAndView;
    }

    @PostMapping(value = "/admin/books/add")
    private ModelAndView postAdminAddBook(Book book){
       Boolean check = bookService.addBook(book);
        if(check = true){
            ModelAndView modelAndView = new ModelAndView("Admin-AddBook-Success");
            modelAndView.addObject("book", book);
            return modelAndView;
        }
        else {
            return new ModelAndView("Admin-AddBook-Failed");
        }
        }


    @GetMapping(value = "/admin/students")
    private ModelAndView getAdminAllStudent(@Param("keyword") String keyword) {
        List<Account> accounts = accservice.SearchStudentByName(keyword);
        ModelAndView modelAndView = new ModelAndView("Admin-Students");
        modelAndView.addObject("accounts", accounts);
        modelAndView.addObject("keyword", keyword);
        return modelAndView;
    }
    @GetMapping(value = "/admin/loans")
    private ModelAndView getAdminAllLoans(@Param("keyword") String keyword){
        List<Receipt> receiptList = receiptservice.getAllRecipts();
        ModelAndView modelAndView =new ModelAndView("Admin-AllLoans");
        modelAndView.addObject("receipts", receiptList);
        return modelAndView;
    }

}
