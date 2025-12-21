package com.invoice.reporting.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "InvoiceRunLogs")
public class InvoiceRunLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RunId")
    private Integer runId;

    @Column(name = "StartedAt")
    private LocalDateTime startedAt;

    @Column(name = "FinishedAt")
    private LocalDateTime finishedAt;

    @Column(name = "InvoicesGenerated")
    private Integer invoicesGenerated;

    @Column(name = "Status")
    private String status;

    @Column(name = "ErrorMessage")
    private String errorMessage;

    // getters and setters
    public Integer getRunId() {
        return runId;
    }
    public void setRunId(Integer runId) {
        this.runId = runId;
    }
    public LocalDateTime getStartedAt() {
        return startedAt;
    }
    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }
    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }
    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }
    public Integer getInvoicesGenerated() {
        return invoicesGenerated;
    }
    public void setInvoicesGenerated(Integer invoicesGenerated) {
        this.invoicesGenerated = invoicesGenerated;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
}