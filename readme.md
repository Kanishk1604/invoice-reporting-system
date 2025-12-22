Step 1: setting up docker to connect to MySQL server 
Step 2: Creating tables : Customers, Orders, OrderItems, Invoices, InvoiceRunLogs
Step 3: Inserting into tables and running this inside INvoiceRepository Data
Step 4: Java Backend Foundation: we created a spring boot project to connect java to sql server with the help of Spring Data JPA (Java Persistence API)
Step 5: Mapping SQL tables to Java entities to allow JPA to read and write data
Step 6: Creating java entities: Customer.java, Order.java...
Step 7: Creating their corresponding repository to be able to use functions : findAll(),findById(), save(), delete() 
Step 8: Writing logic to create invoice by fetching amount, tax and shipping amount for each order, then storing it inside invoice table 
Step 8: Creating production-grade system - we remove startup execution using scheduler