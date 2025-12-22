package com.invoice.reporting.scheduler;

import com.invoice.reporting.service.InvoiceGenerationService;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InvoiceScheduler {

    private final InvoiceGenerationService invoiceGenerationService;

    public InvoiceScheduler(InvoiceGenerationService invoiceGenerationService) {
        this.invoiceGenerationService = invoiceGenerationService;
    }
//cron = "0 0 2 * * *"
    // Runs every day at 2 AM server time
    @Scheduled(fixedDelay = 60000)
        public void runDailyInvoiceJob() {
            try {
            System.out.println(">>> Scheduler triggered");
            invoiceGenerationService.generateInvoicesForShippedOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}