package com.invoice.reporting.controller;

import com.invoice.reporting.service.InvoiceGenerationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceGenerationService invoiceGenerationService;

    public InvoiceController(InvoiceGenerationService invoiceGenerationService) {
        this.invoiceGenerationService = invoiceGenerationService;
    }

    @PostMapping("/run")
    public ResponseEntity<String> runInvoices() {
        invoiceGenerationService.generateInvoicesForShippedOrders();
        return ResponseEntity.ok("Invoice generation triggered");
    }
}