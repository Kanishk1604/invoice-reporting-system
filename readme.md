Invoice Reporting System

A backend-driven, automated invoice generation system built with Spring Boot, SQL Server, and Java PDF/XML exports.
The system detects shipped orders, generates immutable invoices, exports XML snapshots, and produces PDF invoices automatically.

This project demonstrates real-world enterprise invoice automation, including idempotency, auditability, and document generation.

Key Features
•	Automated invoice generation for shipped orders
•	Idempotent processing (no duplicate invoices)
•	Snapshot-based invoice data (XML)
•	Deterministic PDF invoice generation (Java, OpenPDF)
•	SQL Server persistence
•	Batch run logging and execution safety
•	Clean separation of concerns (Orders vs Invoices)


High-Level Architecture

Orders (Upstream System) 
        ->
Invoice Automation Service
        ->
Invoices (DB)
        ->
XML Export (Snapshot)
        ->
PDF Generation

This service assumes orders are created by an upstream system and focuses exclusively on invoice automation.


Data Model Overview:

Customers - Stores customer identity and billing details.

Orders - Represents customer orders with lifecycle states.

Relevant status for invoicing - SHIPPED

OrderItems - Immutable line items belonging to an order.

Invoices - Immutable financial documents generated exactly once per order.

InvoiceRunLogs - Tracks execution history of invoice automation runs.

Invoice Generation Rules
•	Only orders with Status = SHIPPED are eligible
•	Exactly one invoice per order
•	Re-running the job does not create duplicates
•	Invoice data is snapshotted at generation time
•	Orders or items updated later do not affect existing invoices

Automation Flow
1.	Fetch all shipped orders
2.	Skip orders that already have invoices
3.	Calculate totals (subtotal, tax, shipping)
4.	Persist invoice
5.	Export XML snapshot
6.	Generate PDF from XML
7.	Log execution results

Technology Stack
•	Java 17+
•	Spring Boot 3
•	Spring Data JPA
•	SQL Server
•	JAXB (XML export)
•	OpenPDF (PDF generation)
•	Maven

Project Structure:
src/main/java/com/invoice/reporting
├── entity          # JPA entities
├── repository      # Spring Data repositories
├── service         # Business logic
├── export
│   ├── xml         # XML DTOs and export service
│   └── pdf         # PDF generation service
└── controller      # REST endpoints

Generated files:
exports/
├── xml/
│   └── invoice-INV-xxxx.xml
└── pdf/
    └── invoice-INV-xxxx.pdf

Running the Project:

Prerequisites
•	Java 17+
•	Maven
•	SQL Server (local or containerized)

Configure Database

Update application.yml:
spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=InvoiceReporting;encrypt=false
    username: sa
    password: your_password

Build and Run
./mvnw spring-boot:run

Trigger Invoice Generation

REST Endpoint
curl -X POST http://localhost:8080/api/invoices/run

This will:
•	Generate invoices for eligible orders
•	Produce XML and PDF files
•	Log the execution

Output Verification:
1) Database - SELECT * FROM Invoices ORDER BY GeneratedAt DESC;
2) XMl Files - exports/xml
3) PDF Files - exports/pdf

Design Principles
•	Idempotent batch processing
•	Immutable financial documents
•	Clear bounded context (Invoices ≠ Orders)
•	No UI dependencies
•	Platform-independent execution

Possible Extensions
•	Order creation API (replace manual DB inserts)
•	Event-driven invoicing (on shipment)
•	Email invoice PDFs automatically
•	Cloud storage (S3 / Blob)
•	Credit notes and adjustments
•	Retry and failure recovery

Status

Core invoice automation is complete and production-grade.
Remaining work is operational enhancement, not business logic.
