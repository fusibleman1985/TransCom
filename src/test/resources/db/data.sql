-- Вставляем данные в таблицу компаний
INSERT INTO companies (id, company_name, company_role, license_id, rating, company_status, created_date, updated_date)
VALUES
  (1, 'Alpha Logistics', 'BROKER', 'LIC-001', 85, 'ACTIVE', NOW(), NOW()),
  (2, 'Beta Freight', 'CARRIER', 'LIC-002', 92, 'ACTIVE', NOW(), NOW()),
  (3, 'Gamma Transport', 'BROKER', 'LIC-003', 78, 'BLOCKED', NOW(), NOW());

-- Вставляем данные в таблицу пользователей
INSERT INTO users (id, login, password, first_name, last_name, email, created_at, updated_at, user_status, company_id)
VALUES
  ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'john_doe', 'password123', 'John', 'Doe', 'john.doe@alphalogistics.com', NOW(), NOW(), 'ACTIVE', 1),
  ('a1b2c3d4-e5f6-7890-abcd-ef1234567891', 'jane_smith', 'password123', 'Jane', 'Smith', 'jane.smith@alphalogistics.com', NOW(), NOW(), 'ACTIVE', 1),
  ('a1b2c3d4-e5f6-7890-abcd-ef1234567892', 'peter_parker', 'password123', 'Peter', 'Parker', 'peter.parker@betafreight.com', NOW(), NOW(), 'ACTIVE', 2),
  ('a1b2c3d4-e5f6-7890-abcd-ef1234567893', 'mary_jane', 'password123', 'Mary', 'Jane', 'mary.jane@betafreight.com', NOW(), NOW(), 'BLOCKED', 2),
  ('a1b2c3d4-e5f6-7890-abcd-ef1234567894', 'tony_stark', 'password123', 'Tony', 'Stark', 'tony.stark@gammatransport.com', NOW(), NOW(), 'ACTIVE', 3),
  ('a1b2c3d4-e5f6-7890-abcd-ef1234567895', 'bruce_wayne', 'password123', 'Bruce', 'Wayne', 'bruce.wayne@gammatransport.com', NOW(), NOW(), 'INACTIVE', NULL);

-- Вставляем данные в таблицу телефонов
INSERT INTO phones (id, phone_number, user_id)
VALUES
  (1, '555-1234', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890'),
  (2, '555-5678', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890'),
  (3, '555-1234', 'a1b2c3d4-e5f6-7890-abcd-ef1234567891'),
  (4, '555-5678', 'a1b2c3d4-e5f6-7890-abcd-ef1234567891'),
  (5, '555-9876', 'a1b2c3d4-e5f6-7890-abcd-ef1234567892'),
  (6, '555-6543', 'a1b2c3d4-e5f6-7890-abcd-ef1234567892'),
  (7, '555-4321', 'a1b2c3d4-e5f6-7890-abcd-ef1234567894');

-- Вставляем данные в таблицу ролей
INSERT INTO roles (id, role_name, access_role, description)
VALUES
  (1, 'Admin', 'SUPERADMIN', 'Administrator with all permissions'),
  (2, 'Dispatcher', 'DISPATCHER', 'Responsible for managing orders'),
  (3, 'Driver', 'DRIVER', 'Driver role with limited access'),
  (4, 'Customer', 'CUSTOMER', 'End user accessing the service');

-- Вставляем данные в таблицу пользователей-ролей
INSERT INTO users_roles (user_id, role_id)
VALUES
  ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 1),  -- John Doe: SUPERADMIN
  ('a1b2c3d4-e5f6-7890-abcd-ef1234567891', 2),  -- Jane Smith: DISPATCHER
  ('a1b2c3d4-e5f6-7890-abcd-ef1234567892', 3),  -- Peter Parker: DRIVER
  ('a1b2c3d4-e5f6-7890-abcd-ef1234567893', 3),  -- Mary Jane: DRIVER
  ('a1b2c3d4-e5f6-7890-abcd-ef1234567894', 4);  -- Tony Stark: CUSTOMER

-- Вставляем данные в таблицу типов траков
INSERT INTO truck_types (id, short_name, full_name, length_meters, capacity_cubic_units, image_truck_type_name)
VALUES
  (1, 'Truck Type A', 'Small Truck', 6.5, 15.0, 'truck_a.png'),
  (2, 'Truck Type B', 'Medium Truck', 8.0, 25.0, 'truck_b.png'),
  (3, 'Truck Type C', 'Large Truck', 10.0, 35.0, 'truck_c.png'),
  (4, 'Truck Type D', 'Extra Large Truck', 12.0, 45.0, 'truck_d.png'),
  (5, 'Truck Type E', 'Heavy Duty Truck', 14.0, 50.0, 'truck_e.png');

-- Вставляем данные в таблицу траков
INSERT INTO trucks (id, length, weight, capacity, truck_status, location, truck_type_id)
VALUES
  ('f1f2f3f4-f5f6-7890-abcd-ef1234567890', 6, 2000, 15.0, 'AVAILABLE', 'Location A', 1),  -- Truck for Alpha Logistics
  ('f1f2f3f4-f5f6-7890-abcd-ef1234567891', 8, 3000, 25.0, 'IN_TRANSIT', 'Location B', 2),  -- Truck for Beta Freight
  ('f1f2f3f4-f5f6-7890-abcd-ef1234567892', 10, 4000, 35.0, 'AVAILABLE', 'Location C', 3),  -- Truck for Gamma Transport
  ('f1f2f3f4-f5f6-7890-abcd-ef1234567893', 12, 5000, 45.0, 'UNAVAILABLE', 'Location D', 4),  -- Truck for Alpha Logistics
  ('f1f2f3f4-f5f6-7890-abcd-ef1234567894', 14, 6000, 50.0, 'LOADING', 'Location E', 5);  -- Truck for Beta Freight

-- Вставляем данные в таблицу заказов
INSERT INTO orders (id, weight, price, description, order_status)
VALUES
  ('e1e2e3e4-e5f6-7890-abcd-ef1234567890', 1000, 1500.00, 'Order for Alpha Logistics', 'CREATED'),
  ('e1e2e3e4-e5f6-7890-abcd-ef1234567891', 500, 750.00, 'Order for Beta Freight', 'ACCEPTED'),
  ('e1e2e3e4-e5f6-7890-abcd-ef1234567892', 1200, 1800.00, 'Order for Gamma Transport', 'COMPLETED');

-- Вставляем данные в таблицу пользователей-ордеров
INSERT INTO orders_users (order_id, user_id)
VALUES
  ('e1e2e3e4-e5f6-7890-abcd-ef1234567890', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890'),  -- John Doe
  ('e1e2e3e4-e5f6-7890-abcd-ef1234567891', 'a1b2c3d4-e5f6-7890-abcd-ef1234567891'),  -- Jane Smith
  ('e1e2e3e4-e5f6-7890-abcd-ef1234567892', 'a1b2c3d4-e5f6-7890-abcd-ef1234567892');  -- Peter Parker

-- Вставляем данные в таблицу избранного
INSERT INTO favorites (id, user_id, truck_id
