package com.invoice.reporting.scheduler;

import com.invoice.reporting.service.InvoiceGenerationService;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InvoiceScheduler {

    private final InvoiceGenerationService invoiceGenerationService;

    public InvoiceScheduler(InvoiceGenerationService invoiceGenerationService) {
        this.invoiceGenerationService = invoiceGenerationService;
    }
//fixedDelay = 60000
    // Runs every day at 2 AM server time
    @Scheduled(cron = "0 0 2 * * *")
        public void runDailyInvoiceJob() {
            try {
            System.out.println(">>> Scheduler triggered");
            invoiceGenerationService.generateInvoicesForShippedOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}