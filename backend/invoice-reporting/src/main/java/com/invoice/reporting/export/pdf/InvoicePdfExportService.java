package com.invoice.reporting.export.pdf;

import com.invoice.reporting.export.xml.InvoiceXml;
import com.invoice.reporting.export.xml.ItemXml;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class InvoicePdfExportService {

    public void exportInvoicePdf(InvoiceXml invoiceXml) {
        try {
            Path outputDir = Paths.get("exports/pdf");
            Files.createDirectories(outputDir);

            String fileName = "invoice-" + invoiceXml.getInvoiceNumber() + ".pdf";
            Path filePath = outputDir.resolve(fileName);

            Document document = new Document(PageSize.A4, 40, 40, 40, 40);
            PdfWriter.getInstance(document, new FileOutputStream(filePath.toFile()));

            document.open();

            addHeader(document, invoiceXml);
            addCustomerSection(document, invoiceXml);
            addItemsTable(document, invoiceXml);
            addTotalsSection(document, invoiceXml);

            document.close();

        } catch (Exception e) {
            throw new RuntimeException(
                "Failed to generate PDF for invoice " + invoiceXml.getInvoiceNumber(),
                e
            );
        }    
    }

    public void addHeader(Document document, InvoiceXml invoice){
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);

        Paragraph title = new Paragraph("INVOICE", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);

        document.add(title);

        document.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNumber()));
        document.add(new Paragraph("Generated At: " + invoice.getGeneratedAt()));
        document.add(Chunk.NEWLINE);
    }

    public void addCustomerSection(Document document, InvoiceXml invoice){
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

        document.add(new Paragraph("Bill To:", headerFont));
        document.add(new Paragraph(invoice.getCustomer().getName()));
        document.add(new Paragraph(invoice.getCustomer().getEmail()));
        document.add(new Paragraph(
            invoice.getCustomer().getCity() + ", " + invoice.getCustomer().getCountry()
        ));
        document.add(Chunk.NEWLINE);
    }

    private void addItemsTable(Document document, InvoiceXml invoice) throws Exception {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{4, 1, 2, 2});

        addTableHeader(table);
        
        for (ItemXml item : invoice.getItems()) {
            table.addCell(item.getProductName());
            table.addCell(String.valueOf(item.getQuantity()));
            table.addCell(item.getUnitPrice().toString());
            table.addCell(item.getLineTotal().toString());
        }

        document.add(table);
    }

    private void addTableHeader(PdfPTable table) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        table.addCell(new PdfPCell(new Phrase("Product", headerFont)));
        table.addCell(new PdfPCell(new Phrase("Qty", headerFont)));
        table.addCell(new PdfPCell(new Phrase("Unit Price", headerFont)));
        table.addCell(new PdfPCell(new Phrase("Line Total", headerFont)));
    }

    private void addTotalsSection(Document document, InvoiceXml invoice) throws Exception {
        document.add(Chunk.NEWLINE);

        PdfPTable totals = new PdfPTable(2);
        totals.setWidthPercentage(40);
        totals.setHorizontalAlignment(Element.ALIGN_RIGHT);

        totals.addCell("Subtotal");
        totals.addCell(invoice.getTotals().getSubtotal().toString());

        totals.addCell("Tax");
        totals.addCell(invoice.getTotals().getTax().toString());

        totals.addCell("Shipping");
        totals.addCell(invoice.getTotals().getShipping().toString());

        totals.addCell("Total");
        totals.addCell(invoice.getTotals().getTotal().toString());

        document.add(totals);
    }

}    