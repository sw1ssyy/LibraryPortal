package com.example.libraryportal.Service;

import com.example.libraryportal.Models.Account;
import com.example.libraryportal.Models.Invoice;
import com.example.libraryportal.Models.Receipt;
import com.example.libraryportal.Models.receiptRepo;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class receiptService {
    private final receiptRepo receiptrepo;
    private final RestTemplate template;
    private IntergrationService intergrationService;

    /**
     *  Example of Dependency Injection
     * @param receiptrepo Instance of the receipt repository
     * @param template Instance of the rest template
     * @param intergrationService Instance of the Intergration service
     */
    public receiptService(receiptRepo receiptrepo, RestTemplate template, IntergrationService intergrationService) {

        this.receiptrepo = receiptrepo;
        this.template = template;
        this.intergrationService = intergrationService;
    }

    /**
     * Method used to create a new Receipt
     * @param newRecipt - object of the new Receipt
     */
    public void addRecipt(Receipt newRecipt) {
        receiptrepo.save(newRecipt);
    }

    /**
     * Method used to retrieve all the reciepts stored in the database
     * @return List of receipts
     */
    public List<Receipt> getAllRecipts(){
        return receiptrepo.findAll();
    }

    /**
     * Method used to find Receipt by its ID
     * @param ID - Receipt ID
     * @return Recipt Found in database
     */
    public Receipt findReceiptByID(Long ID){
        return receiptrepo.findReceiptById(ID);
    }

    /**
     * Method used to delete existing receipt in the database
     * @param receipt - Receipt being deleted
     */
    public void deleteReceipt(Receipt receipt){
        receiptrepo.delete(receipt);
        System.out.println("Receipt Deleted!!");
    }

    /**
     * Method used to create an Invoice that will being created on the FinancePortal
     * @param receipt - Object of Receipt used to calculate the overdue fee
     * @param account - Account for the Invoice
     * @return Invoice object that is sent to the FinancePortal via intergrationService
     */
    public Invoice createOverdueInvoice(Receipt receipt, Account account){
        Double Fee = createOverdueFee(receipt.getDateDue(),LocalDate.now());
        Invoice invoice = new Invoice();
        invoice.setAmount(Fee);
        invoice.setDueDate(LocalDate.now().plusYears(1));
        invoice.setType(Invoice.Type.LIBRARY_FINE);
        invoice.setAccount(account);
        return intergrationService.createLibraryInvoice(invoice);
    }


    /**
     * Method used to create the payment amount for books overdue
     * @param dueDate - duedate created on the receipt
     * @param dateReturned - date the book was returned
     * @return overdue amount
     */
    public Double createOverdueFee(LocalDate dueDate, LocalDate dateReturned) {
        Double DAY_FINE = 5.0;

      Long distance = Math.abs(ChronoUnit.DAYS.between(dateReturned, dueDate));
        return distance * DAY_FINE;
    }

    /**
     * Method used to collect a list of Receipts owned by a specific account
     * @param account - account being searched for.
     * @return List of receipts
     */
    public List<Receipt> getAllReciptsByUser(Account account) {
        return receiptrepo.getAllByStudentID(account);
    }
}
