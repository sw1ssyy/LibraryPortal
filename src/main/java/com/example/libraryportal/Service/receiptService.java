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

    public receiptService(receiptRepo receiptrepo, RestTemplate template, IntergrationService intergrationService) {

        this.receiptrepo = receiptrepo;
        this.template = template;
        this.intergrationService = intergrationService;
    }

    public void addRecipt(Receipt newRecipt) {
        receiptrepo.save(newRecipt);
    }

    public List<Receipt> getAllRecipts(){
        return receiptrepo.findAll();
    }
    public Receipt findReceiptByID(Long ID){
        return receiptrepo.findReceiptById(ID);
    }
    public void deleteReceipt(Receipt receipt){
        receiptrepo.delete(receipt);
        System.out.println("Receipt Deleted!!");
    }
    public Invoice createOverdueInvoice(Receipt receipt, Account account){
        Double Fee = createOverdueFee(receipt.getDateDue(),LocalDate.now());
        Invoice invoice = new Invoice();
        invoice.setAmount(Fee);
        invoice.setDueDate(LocalDate.now().plusYears(1));
        invoice.setType(Invoice.Type.LIBRARY_FINE);
        invoice.setAccount(account);
        return intergrationService.createLibraryInvoice(invoice);
    }



    public Double createOverdueFee(LocalDate dueDate, LocalDate dateReturned) {
        Double DAY_FINE = 5.0;

      Long distance = Math.abs(ChronoUnit.DAYS.between(dateReturned, dueDate));
        return distance * DAY_FINE;
    }
}
