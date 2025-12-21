package com.invoice.reporting;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.invoice.reporting.service.InvoiceGenerationService;




@SpringBootApplication
public class InvoiceReportingApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceReportingApplication.class, args);
	}
	@Bean
	CommandLineRunner runOnce(InvoiceGenerationService service) {
		return args -> service.generateInvoicesForShippedOrders();
	}
}
