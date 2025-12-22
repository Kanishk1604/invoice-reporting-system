package com.invoice.reporting.repository;

import com.invoice.reporting.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    boolean existsByOrder_OrderId(Integer orderId);
}