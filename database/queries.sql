-- SELECT TABLE_NAME
-- FROM INFORMATION_SCHEMA.TABLES
-- WHERE TABLE_TYPE = 'BASE TABLE';


-- SELECT * FROM Customers;
-- SELECT * FROM InvoiceRunLogs;

-- ALTER TABLE Orders
-- ADD CONSTRAINT UQ_Orders_Customer_Date_Status
-- UNIQUE (CustomerId, OrderDate, Status);
-- SELECT * FROM Orders;

-- SELECT * FROM OrderItems;
-- SELECT * FROM Invoices;

-- create database InvoiceReporting;
-- go
-- ALTER TABLE InvoiceRunLogs
-- ADD StartedAt DATETIME2,
--     FinishedAt DATETIME2;

SELECT * FROM InvoiceRunLogs ORDER BY StartedAt DESC;