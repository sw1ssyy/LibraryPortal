package com.example.libraryportal.Service;

import com.example.libraryportal.Models.Invoice;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class IntergrationService {
    private final RestTemplate template;

    public IntergrationService(RestTemplate template) {
        this.template = template;
    }

    /**
     * Used to create an Invoice within the Finance Portal
     * @param invoice - Invoice being sent to the Finance Portal
     * @return - Invoice that has being created.
     */

    public Invoice createLibraryInvoice(Invoice invoice){
        return template.postForObject("http://127.0.0.1:8000/api/invoice/create", invoice , Invoice.class);
    }
}
