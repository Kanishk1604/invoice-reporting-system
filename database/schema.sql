USE InvoiceReporting;
GO

CREATE TABLE Customers (
    CustomerId INT IDENTITY PRIMARY KEY,
    Name NVARCHAR(255) NOT NULL,
    Email NVARCHAR(255) NOT NULL,
    AddressLine1 NVARCHAR(255),
    City NVARCHAR(100),
    State NVARCHAR(100),
    Country NVARCHAR(100),
    CreatedAt DATETIME2 DEFAULT GETDATE()
);

CREATE TABLE Orders (
    OrderId INT IDENTITY PRIMARY KEY,
    CustomerId INT NOT NULL,
    OrderDate DATETIME2 NOT NULL,
    Status NVARCHAR(50) NOT NULL,
    FOREIGN KEY (CustomerId) REFERENCES Customers(CustomerId)
);

CREATE TABLE OrderItems (
    OrderItemId INT IDENTITY PRIMARY KEY,
    OrderId INT NOT NULL,
    ProductName NVARCHAR(255) NOT NULL,
    Quantity INT NOT NULL,
    UnitPrice DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (OrderId) REFERENCES Orders(OrderId)
);

CREATE TABLE Invoices (
    InvoiceId INT IDENTITY PRIMARY KEY,
    OrderId INT NOT NULL,
    InvoiceNumber NVARCHAR(50) NOT NULL,
    Subtotal DECIMAL(10,2) NOT NULL,
    TaxAmount DECIMAL(10,2) NOT NULL,
    ShippingAmount DECIMAL(10,2) NOT NULL,
    TotalAmount DECIMAL(10,2) NOT NULL,
    Status NVARCHAR(50) NOT NULL,
    GeneratedAt DATETIME2 DEFAULT GETDATE(),
    FOREIGN KEY (OrderId) REFERENCES Orders(OrderId)
);

CREATE TABLE InvoiceRunLogs (
    RunId INT IDENTITY PRIMARY KEY,
    RunTimestamp DATETIME2 DEFAULT GETDATE(),
    InvoicesGenerated INT NOT NULL,
    Status NVARCHAR(50) NOT NULL,
    ErrorMessage NVARCHAR(MAX)
);