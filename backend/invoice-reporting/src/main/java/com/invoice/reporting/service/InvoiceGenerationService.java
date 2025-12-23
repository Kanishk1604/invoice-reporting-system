package com.invoice.reporting.service;


import com.invoice.reporting.entity.Invoice;
import com.invoice.reporting.entity.InvoiceRunLog;
import com.invoice.reporting.entity.Order;
import com.invoice.reporting.entity.OrderItem;
import com.invoice.reporting.export.InvoiceXmlExportService;
import com.invoice.reporting.repository.CustomerRepository;
import com.invoice.reporting.repository.InvoiceRepository;
import com.invoice.reporting.repository.InvoiceRunLogRepository;
import com.invoice.reporting.repository.OrderItemRepository;
import com.invoice.reporting.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceGenerationService {
    private final InvoiceRunLogRepository runLogRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceXmlExportService xmlExportService;

    public InvoiceGenerationService(OrderRepository orderRepository,
                                    OrderItemRepository orderItemRepository,
                                    InvoiceRepository invoiceRepository,
                                    CustomerRepository customerRepository, 
                                    InvoiceRunLogRepository runLogRepository, 
                                    InvoiceXmlExportService xmlExportService){
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.invoiceRepository = invoiceRepository;
        this.runLogRepository = runLogRepository;
        this.xmlExportService = xmlExportService;
    }

    public void generateInvoicesForShippedOrders (){
        
        // System.out.println(">>> Invoice generation STARTED");

        if (runLogRepository.existsByIsActiveTrue()) {
            return; // another run is already in progress
        }


        InvoiceRunLog runLog = new InvoiceRunLog();
        runLog.setStartedAt(LocalDateTime.now());
        runLog.setInvoicesGenerated(0); 
        runLog.setStatus("RUNNING");
        runLog.setIsActive(true);
        runLogRepository.save(runLog);
        int generatedCount = 0;
        
        try{
            
            List<Order> shippedOrders = orderRepository.findByStatus("SHIPPED");

            for(Order order: shippedOrders){

                // System.out.println(">>> OrderId=" + order.getOrderId() + ", status=[" + order.getStatus() + "]");

                if (invoiceRepository.existsByOrder_OrderId(order.getOrderId())) {
                    continue;
                }
                List<OrderItem> items_ordered = orderItemRepository.findByOrder_OrderId(order.getOrderId());

                double sum = items_ordered.stream()
                            .mapToDouble((i -> i.getQuantity() * i.getUnitPrice()))
                            .sum();
                double tax = sum * 0.13;
                double shipping = 25.00;
                double total = sum + tax + shipping;

                Invoice invoice = new Invoice();
                invoice.setOrder(order);
                invoice.setInvoiceNumber("INV-" + UUID.randomUUID());
                invoice.setSubtotal(sum);
                invoice.setTaxAmount(tax);
                invoice.setShippingAmount(shipping);
                invoice.setTotalAmount(total);
                invoice.setStatus("GENERATED");
                invoice.setGeneratedAt(LocalDateTime.now());
                
                // System.out.println(">>> CREATING invoice for OrderId " + order.getOrderId());   
                
                invoiceRepository.save(invoice);

                // System.out.println(">>> SAVED invoice for OrderId " + order.getOrderId());

                xmlExportService.exportInvoice(invoice, items_ordered);
                generatedCount++;
            }
            runLog.setStatus("SUCCESS");
            runLog.setInvoicesGenerated(generatedCount);

        } catch (Exception e) {
            runLog.setStatus("FAILED");
            runLog.setErrorMessage(e.getMessage());
        }finally{
            runLog.setFinishedAt(LocalDateTime.now());
            runLog.setIsActive(false);
            runLogRepository.save(runLog);
        }

    }

}
