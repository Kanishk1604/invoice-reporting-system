package com.invoice.reporting.export.xml;

import jakarta.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class TotalsXml {

    @XmlElement
    private BigDecimal subtotal;

    @XmlElement
    private BigDecimal tax;

    @XmlElement
    private BigDecimal shipping;

    @XmlElement
    private BigDecimal total;

    // getters and setters
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
    public BigDecimal getTax() {
        return tax;
    }
    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
    public BigDecimal getShipping() {
        return shipping;
    }
    public void setShipping(BigDecimal shipping) {
        this.shipping = shipping;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}