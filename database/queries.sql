-- SELECT TABLE_NAME
-- FROM INFORMATION_SCHEMA.TABLES
-- WHERE TABLE_TYPE = 'BASE TABLE';


-- SELECT * FROM Customers;
-- SELECT * FROM InvoiceRunLogs;

-- ALTER TABLE Orders
-- ADD CONSTRAINT UQ_Orders_Customer_Date_Status
-- UNIQUE (CustomerId, OrderDate, Status);
-- SELECT * FROM Orders;

-- DELETE FROM OrderItems;
-- DELETE FROM Orders;
-- DELETE FROM Customers;

-- SELECT * FROM OrderItems;
-- SELECT OrderId, ProductName, Quantity, UnitPrice, COUNT(*) AS cnt
-- FROM OrderItems
-- GROUP BY OrderId, ProductName, Quantity, UnitPrice
-- HAVING COUNT(*) > 1;

--Deleting dups from OrderItems based on OrderId, ProductName, Quantity, UnitPrice
-- WITH RankedItems AS (
--     SELECT *,
--            ROW_NUMBER() OVER (
--                PARTITION BY OrderId, ProductName, Quantity, UnitPrice
--                ORDER BY OrderItemId
--            ) AS rn
--     FROM OrderItems
-- )
-- DELETE FROM RankedItems
-- WHERE rn > 1;

--Deleting dups from Customers based on Email
-- WITH RankedCustomers AS (
--     SELECT *,
--            ROW_NUMBER() OVER (
--                PARTITION BY Email
--                ORDER BY CustomerId
--            ) AS rn
--     FROM Customers
-- )
-- DELETE FROM RankedCustomers
-- WHERE rn > 1;


-- SELECT * FROM Invoices;

-- SELECT
--     InvoiceId,
--     InvoiceNumber,
--     OrderId,
--     Subtotal,
--     TaxAmount,
--     ShippingAmount,
--     TotalAmount,
--     Status,
--     GeneratedAt
-- FROM Invoices
-- ORDER BY GeneratedAt DESC;
-- SELECT * FROM Orders WHERE Status = 'SHIPPED';
-- SELECT * FROM InvoiceRunLogs ORDER BY StartedAt DESC;
-- SELECT o.OrderId, i.InvoiceId
-- FROM Orders o
-- LEFT JOIN Invoices i ON o.OrderId = i.OrderId
-- WHERE o.Status = 'SHIPPED';

-- INSERT INTO Orders (CustomerId, OrderDate, Status)
-- VALUES (1, GETDATE(), 'SHIPPED');
-- INSERT INTO OrderItems (OrderId, ProductName, Quantity, UnitPrice)
-- VALUES
-- (SCOPE_IDENTITY(), 'Pen', 2, 40.00),
-- (SCOPE_IDENTITY(), 'Notebook', 1, 80.00),
-- (SCOPE_IDENTITY(), 'Sheets', 1, 200.00);

-- SELECT * FROM Invoices ORDER BY GeneratedAt DESC;

-- SELECT OrderId, CustomerId, OrderDate, Status
-- FROM Orders
-- ORDER BY OrderId DESC;
-- 1. Create a new order
-- INSERT INTO Orders (CustomerId, OrderDate, Status)
-- VALUES (1, GETDATE(), 'SHIPPED');

-- INSERT INTO OrderItems (OrderId, ProductName, Quantity, UnitPrice)
-- VALUES
-- (SCOPE_IDENTITY(), 'TV', 1, 400.00);
-- select * from orders;
-- SELECT * FROM Invoices WHERE OrderId = 1006;

-- SELECT
--     OrderId,
--     '[' + Status + ']' AS StatusWithBrackets,
--     LEN(Status) AS StatusLength
-- FROM Orders
-- WHERE OrderId = 1006;

-- SELECT
--     InvoiceId,
--     InvoiceNumber,
--     OrderId,
--     TotalAmount,
--     GeneratedAt
-- FROM Invoices
-- ORDER BY GeneratedAt DESC;

-- SELECT * FROM Orders WHERE Status = 'SHIPPED';

-- SELECT * FROM OrderItems WHERE OrderId = 5;
-- SELECT * FROM Invoices WHERE OrderId = 5;

-- SELECT * FROM Invoices;


-- ALTER TABLE InvoiceRunLogs
-- ADD StartedAt DATETIME2,
--     FinishedAt DATETIME2;
-- ALTER TABLE InvoiceRunLogs
-- ADD IsActive BIT DEFAULT 0;

-- SELECT * FROM InvoiceRunLogs ORDER BY StartedAt DESC;

-- SELECT * FROM Invoices WHERE OrderId = 5; 
-- SELECT * FROM InvoiceRunLogs ORDER BY StartedAt DESC;