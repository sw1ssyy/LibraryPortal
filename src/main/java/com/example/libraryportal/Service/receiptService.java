package com.example.libraryportal.Service;

import com.example.libraryportal.Models.Receipt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class receiptService {
    private com.example.libraryportal.Models.receiptRepo receiptRepo;

    public receiptService(com.example.libraryportal.Models.receiptRepo receiptRepo) {
        this.receiptRepo = receiptRepo;
    }

    public void addRecipt(Receipt newRecipt) {
        receiptRepo.save(newRecipt);
    }

    public List<Receipt> getAllRecipts(){
        return receiptRepo.findAll();
    }
}
