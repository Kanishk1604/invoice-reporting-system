package com.invoice.reporting.service;


import com.invoice.reporting.entity.Invoice;
import com.invoice.reporting.entity.Order;
import com.invoice.reporting.entity.OrderItem;
import com.invoice.reporting.repository.CustomerRepository;
import com.invoice.reporting.repository.InvoiceRepository;
import com.invoice.reporting.repository.OrderItemRepository;
import com.invoice.reporting.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceGenerationService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final InvoiceRepository invoiceRepository;

    public InvoiceGenerationService(OrderRepository orderRepository,
                                    OrderItemRepository orderItemRepository,
                                    InvoiceRepository invoiceRepository,
                                    CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.invoiceRepository = invoiceRepository;
    }

    public void generateInvoicesForShippedOrders(){
        List<Order> shippedOrders = orderRepository.findByStatus("SHIPPED");

        for(Order order: shippedOrders){

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

            invoiceRepository.save(invoice);
        }
    }

}
