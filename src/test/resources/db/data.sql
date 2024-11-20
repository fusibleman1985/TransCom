-- Inserting sample users
INSERT INTO users (id, login, password, first_name, last_name, email, created_at, updated_at, user_status)
VALUES ('111e4567-e89b-12d3-a456-426614174000', 'user1', 'Password123!', 'John', 'Doe', 'john.doe@example.com', NOW(), NOW(),
        'ACTIVE'),
       ('222e4567-e89b-12d3-a456-426614174000', 'user2', 'Password321!', 'Jane', 'Smith', 'jane.smith@example.com', NOW(), NOW(),
        'BLOCKED');

-- Inserting sample orders
INSERT INTO orders (id, weight, price, description, order_status, user_id)
VALUES ('333e4567-e89b-12d3-a456-426614174000', 5, 49.99, 'Order for electronics', 'CREATED',
        '111e4567-e89b-12d3-a456-426614174000'),
       ('444e4567-e89b-12d3-a456-426614174000', 10, 89.99, 'Order for furniture', 'IN_TRANSIT',
        '222e4567-e89b-12d3-a456-426614174000');

-- Inserting sample phones
INSERT INTO phones (phone_number, user_id)
VALUES ('+1234567890', '111e4567-e89b-12d3-a456-426614174000'),
       ('+0987654321', '222e4567-e89b-12d3-a456-426614174000');