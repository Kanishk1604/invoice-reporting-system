package com.invoice.reporting.repository;

import com.invoice.reporting.entity.InvoiceRunLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRunLogRepository extends JpaRepository<InvoiceRunLog, Integer> {
    boolean existsByIsActiveTrue();
}