-- SELECT TABLE_NAME
-- FROM INFORMATION_SCHEMA.TABLES
-- WHERE TABLE_TYPE = 'BASE TABLE';


SELECT * FROM Customers;

-- ALTER TABLE Orders
-- ADD CONSTRAINT UQ_Orders_Customer_Date_Status
-- UNIQUE (CustomerId, OrderDate, Status);
SELECT * FROM Orders;

SELECT * FROM OrderItems;
SELECT * FROM Invoices;

-- create database InvoiceReporting;
-- go
