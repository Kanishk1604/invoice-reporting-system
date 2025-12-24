package com.invoice.reporting.export;

import com.invoice.reporting.entity.Invoice;
import com.invoice.reporting.entity.OrderItem;
import com.invoice.reporting.export.xml.*;
import com.invoice.reporting.export.pdf.InvoicePdfExportService;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceXmlExportService {

    public void exportInvoice(Invoice invoice, List<OrderItem> items) throws Exception {

        InvoiceXml xml = map(invoice, items);
        
        JAXBContext context = JAXBContext.newInstance(InvoiceXml.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        Path dir = Paths.get("exports/xml");
        Files.createDirectories(dir);

        Path file = dir.resolve("invoice-" + invoice.getInvoiceNumber() + ".xml");
        marshaller.marshal(xml, file.toFile());

        InvoicePdfExportService pdfService = new InvoicePdfExportService();
        pdfService.exportInvoicePdf(xml);
    }

    private InvoiceXml map(Invoice invoice, List<OrderItem> items) {

        InvoiceXml xml = new InvoiceXml();
        xml.setInvoiceNumber(invoice.getInvoiceNumber());
        xml.setGeneratedAt(invoice.getGeneratedAt());

        // Customer
        CustomerXml customerXml = new CustomerXml();
        customerXml.setName(invoice.getOrder().getCustomer().getName());
        customerXml.setEmail(invoice.getOrder().getCustomer().getEmail());
        customerXml.setCity(invoice.getOrder().getCustomer().getCity());
        customerXml.setCountry(invoice.getOrder().getCustomer().getCountry());
        xml.setCustomer(customerXml);

        // Items
        List<ItemXml> itemXmls = items.stream().map(i -> {
            ItemXml ix = new ItemXml();
            ix.setProductName(i.getProductName());
            ix.setQuantity(i.getQuantity());
            ix.setUnitPrice(BigDecimal.valueOf(i.getUnitPrice()));
            ix.setLineTotal(
                    BigDecimal.valueOf(i.getQuantity())
                            .multiply(BigDecimal.valueOf(i.getUnitPrice()))
            );
            return ix;
        }).collect(Collectors.toList());

        xml.setItems(itemXmls);

        // Totals
        TotalsXml totals = new TotalsXml();
        totals.setSubtotal(BigDecimal.valueOf(invoice.getSubtotal()));
        totals.setTax(BigDecimal.valueOf(invoice.getTaxAmount()));
        totals.setShipping(BigDecimal.valueOf(invoice.getShippingAmount()));
        totals.setTotal(BigDecimal.valueOf(invoice.getTotalAmount()));
        xml.setTotals(totals);

        return xml;
    }
}