package com.invoice.reporting.export.xml;

import jakarta.xml.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement(name = "Invoice")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceXml {

    @XmlElement
    private String invoiceNumber;

    @XmlElement
    private LocalDateTime generatedAt;

    @XmlElement
    private CustomerXml customer;

    @XmlElementWrapper(name = "Items")
    @XmlElement(name = "Item")
    private List<ItemXml> items;

    @XmlElement
    private TotalsXml totals;

    // getters and setters
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }
    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
    public CustomerXml getCustomer() {
        return customer;
    }
    public void setCustomer(CustomerXml customer) {
        this.customer = customer;
    }
    public List<ItemXml> getItems() {
        return items;
    }
    public void setItems(List<ItemXml> items) {
        this.items = items;
    }
    public TotalsXml getTotals() {
        return totals;
    }
    public void setTotals(TotalsXml totals) {
        this.totals = totals;
    }
    
}