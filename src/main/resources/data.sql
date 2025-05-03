-- Clear existing data
DELETE FROM inventory_snapshots;
DELETE FROM sales_line_items;
DELETE FROM orders;
DELETE FROM products;
DELETE FROM customers;
DELETE FROM couriers;
DELETE FROM regions;

-- Insert Regions
INSERT INTO regions (id, city, state, country, postalCode) VALUES
(1, 'New York', 'NY', 'USA', '10001'),
(2, 'Los Angeles', 'CA', 'USA', '90001');

-- Insert Couriers
INSERT INTO couriers (id, name, status) VALUES
(1, 'Express Delivery', 'ACTIVE'),
(2, 'Quick Ship', 'ACTIVE'),
(3, 'Fast Logistics', 'ACTIVE');

-- Insert Products
INSERT INTO products (id, name, category, cost, lowStockThreshold) VALUES
(1, 'Laptop Pro', 'Electronics', 1200.00, 5),
(2, 'Smartphone X', 'Electronics', 800.00, 10),
(3, 'Office Chair', 'Furniture', 200.00, 3),
(4, 'Desk Lamp', 'Furniture', 50.00, 8),
(5, 'Coffee Maker', 'Appliances', 150.00, 4),
(6, 'Blender', 'Appliances', 80.00, 6),
(7, 'Wireless Mouse', 'Electronics', 30.00, 15),
(8, 'Keyboard', 'Electronics', 60.00, 12);

-- Insert Customers
INSERT INTO customers (id, name) VALUES
(1, 'John Doe'),
(2, 'Jane Smith'),
(3, 'Bob Johnson'),
(4, 'Alice Brown'),
(5, 'Charlie Wilson');

-- Insert Orders
INSERT INTO orders (id, customer_id, courier_id, region_id, totalAmount, orderPlacedTimestamp, status, numberOfItems) VALUES
(1, 1, 1, 1, 2000.00, '2024-01-15 10:00:00', 'COMPLETED', 2),
(2, 1, 1, 1, 800.00, '2024-02-01 14:30:00', 'COMPLETED', 1),
(3, 2, 2, 2, 1500.00, '2024-01-20 09:15:00', 'COMPLETED', 3),
(4, 3, 3, 1, 300.00, '2024-02-10 16:45:00', 'COMPLETED', 2),
(5, 4, 1, 2, 950.00, '2024-02-15 11:20:00', 'COMPLETED', 4),
(6, 5, 2, 1, 1800.00, '2024-02-20 13:10:00', 'COMPLETED', 2),
(7, 2, 3, 2, 1200.00, '2024-02-25 15:30:00', 'COMPLETED', 1),
(8, 3, 1, 1, 600.00, '2024-03-01 10:45:00', 'COMPLETED', 1);

-- Insert Sales Line Items
INSERT INTO sales_line_items (id, order_id, product_id, quantity, pricePerUnit) VALUES
(1, 1, 1, 1, 1200.00),
(2, 1, 2, 1, 800.00),
(3, 2, 2, 1, 800.00),
(4, 3, 1, 1, 1200.00),
(5, 3, 3, 1, 200.00),
(6, 3, 4, 2, 50.00),
(7, 4, 3, 1, 200.00),
(8, 4, 4, 2, 50.00),
(9, 5, 5, 1, 150.00),
(10, 5, 6, 1, 80.00),
(11, 5, 7, 2, 30.00),
(12, 5, 8, 1, 60.00),
(13, 6, 1, 1, 1200.00),
(14, 6, 2, 1, 800.00),
(15, 7, 1, 1, 1200.00),
(16, 8, 2, 1, 800.00);

-- Insert Inventory Snapshots
INSERT INTO inventory_snapshots (id, product_id, quantity, timestamp) VALUES
-- Electronics (Laptop Pro - low stock)
(1, 1, 3, '2024-03-01 10:00:00'),
-- Electronics (Smartphone X - normal stock)
(2, 2, 15, '2024-03-01 10:00:00'),
-- Furniture (Office Chair - low stock)
(3, 3, 2, '2024-03-01 10:00:00'),
-- Furniture (Desk Lamp - normal stock)
(4, 4, 12, '2024-03-01 10:00:00'),
-- Appliances (Coffee Maker - low stock)
(5, 5, 2, '2024-03-01 10:00:00'),
-- Appliances (Blender - normal stock)
(6, 6, 8, '2024-03-01 10:00:00'),
-- Electronics (Wireless Mouse - normal stock)
(7, 7, 20, '2024-03-01 10:00:00'),
-- Electronics (Keyboard - normal stock)
(8, 8, 15, '2024-03-01 10:00:00'); 