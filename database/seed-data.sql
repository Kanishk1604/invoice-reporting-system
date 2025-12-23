USE InvoiceReporting;
GO

INSERT INTO Customers (Name, Email, AddressLine1, City, State, Country)
VALUES (
    'Acme Corporation',
    'billing@acme.com',
    '123 King Street',
    'Toronto',
    'ON',
    'Canada'
);

INSERT INTO Orders (CustomerId, OrderDate, Status)
VALUES (
    1,
    GETDATE(),
    'SHIPPED'
);

INSERT INTO OrderItems (OrderId, ProductName, Quantity, UnitPrice)
VALUES
(1, 'Laptop', 2, 1200.00),
(1, 'USB-C Dock', 1, 250.00);

INSERT INTO Orders (CustomerId, OrderDate, Status)
VALUES (1, GETDATE(), 'SHIPPED');

INSERT INTO OrderItems (OrderId, ProductName, Quantity, UnitPrice)
VALUES
(SCOPE_IDENTITY(), 'Keyboard', 1, 120.00);