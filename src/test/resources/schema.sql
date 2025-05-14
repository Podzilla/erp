-- Create couriers table
CREATE TABLE IF NOT EXISTS couriers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    status VARCHAR(50)
);

-- Create orders table
CREATE TABLE IF NOT EXISTS orders (
    order_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_placed_timestamp TIMESTAMP,
    final_status_timestamp TIMESTAMP,
    status VARCHAR(50),
    total_amount DECIMAL(10, 2),
    courier_id BIGINT NOT NULL,
    FOREIGN KEY (courier_id) REFERENCES couriers(id)
);

-- Create products table
CREATE TABLE IF NOT EXISTS products (
    product_id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    category VARCHAR(100)
);

-- Create sales line items table
CREATE TABLE IF NOT EXISTS sales_line_items (
    id BIGINT PRIMARY KEY,
    order_id BIGINT,
    product_id BIGINT,
    quantity INT,
    price_per_unit DECIMAL(10, 2),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
