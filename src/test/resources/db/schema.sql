  DROP TABLE IF EXISTS companies;
  DROP TABLE IF EXISTS favorites;
  DROP TABLE IF EXISTS orders;
  DROP TABLE IF EXISTS orders_users;
  DROP TABLE IF EXISTS phones;
  DROP TABLE IF EXISTS roles;
  DROP TABLE IF EXISTS truck_types;
  DROP TABLE IF EXISTS trucks;
  DROP TABLE IF EXISTS users;
  DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS users_trucks;

-- Create 'companies' table
CREATE TABLE companies (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         company_name VARCHAR(255) NOT NULL,
                         company_role VARCHAR(255),
                         license_id VARCHAR(255) NOT NULL,
                         rating INTEGER,
                         company_status VARCHAR(255),
                         created_date TIMESTAMP,
                         updated_date TIMESTAMP,
                         CONSTRAINT chk_company_role CHECK (company_role IN ('BROKER', 'CARRIER')),
                         CONSTRAINT chk_company_status CHECK (company_status IN ('ACTIVE', 'BLOCKED', 'DELETED')),
                         CONSTRAINT chk_company_rating CHECK (rating BETWEEN 0 AND 100)
);

-- Create 'favorites' table
CREATE TABLE favorites (
                         id BIGINT PRIMARY KEY,
                         user_id UUID NOT NULL,
                         truck_id UUID,
                         order_id UUID
);

-- Create 'orders' table
CREATE TABLE orders (
                      id UUID PRIMARY KEY,
                      weight INTEGER,
                      price DECIMAL(10, 2),
                      description VARCHAR(255),
                      order_status VARCHAR(50),
                      CONSTRAINT chk_order_status CHECK (order_status IN ('CREATED', 'ACCEPTED', 'CANCELLED', 'COMPLETED'))
);

-- Create 'orders_users' table
CREATE TABLE orders_users (
                            order_id UUID NOT NULL,
                            user_id UUID NOT NULL,
                            PRIMARY KEY (order_id, user_id)
);

-- Create 'phones' table
CREATE TABLE phones (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      phone_number VARCHAR(32) NOT NULL,
                      user_id UUID NOT NULL
);

-- Create 'roles' table
CREATE TABLE roles (
                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                     role_name VARCHAR(255) NOT NULL,
                     access_role VARCHAR(255) NOT NULL,
                     description VARCHAR(255),
                     CONSTRAINT chk_roles_access_role CHECK (access_role IN ('SUPERADMIN', 'ADMIN', 'SUPERVISOR', 'MANAGER', 'DISPATCHER', 'DRIVER', 'CUSTOMER'))
);

-- Sample data for 'roles' table
INSERT INTO roles (role_name, access_role, description)
VALUES
  ('Admin', 'ADMIN', 'System Administrator'),
  ('Driver', 'DRIVER', 'Truck Driver');

-- Create 'truck_types' table
CREATE TABLE truck_types (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           short_name VARCHAR(128) UNIQUE NOT NULL,
                           full_name VARCHAR(128) NOT NULL,
                           length_meters DOUBLE NOT NULL,
                           capacity_cubic_units DOUBLE NOT NULL,
                           image_truck_type_name VARCHAR(255)
);

-- Sample data for 'truck_types' table
INSERT INTO truck_types (short_name, full_name, length_meters, capacity_cubic_units, image_truck_type_name)
VALUES
  ('T1', 'Truck Type 1', 12.5, 30.0, 'truck_type_1.png'),
  ('T2', 'Truck Type 2', 10.0, 20.0, 'truck_type_2.png');

-- Create 'trucks' table
CREATE TABLE trucks (
                      id UUID PRIMARY KEY,
                      length INTEGER NOT NULL,
                      weight INTEGER NOT NULL,
                      capacity DECIMAL(10, 2) NOT NULL,
                      truck_status VARCHAR(50) NOT NULL,
                      location VARCHAR(255),
                      truck_type_id BIGINT NOT NULL,
                      CONSTRAINT chk_truck_status CHECK (truck_status IN ('AVAILABLE', 'IN_TRANSIT', 'LOADING', 'UNLOADING', 'UNAVAILABLE', 'DELETED'))
);

-- Create 'users' table
CREATE TABLE users (
                     id UUID PRIMARY KEY,
                     login VARCHAR(64) UNIQUE NOT NULL,
                     password VARCHAR(255) NOT NULL,
                     first_name VARCHAR(64) NOT NULL,
                     last_name VARCHAR(64) NOT NULL,
                     email VARCHAR(64) UNIQUE NOT NULL,
                     created_at TIMESTAMP NOT NULL,
                     updated_at TIMESTAMP,
                     user_status VARCHAR(32) NOT NULL,
                     company_id BIGINT,
                     FOREIGN KEY (company_id) REFERENCES companies(id)
);

-- Create 'users_roles' table
CREATE TABLE users_roles (
                           user_id UUID NOT NULL,
                           role_id BIGINT NOT NULL,
                           PRIMARY KEY (user_id, role_id)
);

-- Create 'users_trucks' table
CREATE TABLE users_trucks (
                            truck_id UUID NOT NULL,
                            user_id UUID NOT NULL,
                            PRIMARY KEY (truck_id, user_id)
);

-- Create foreign keys and constraints
ALTER TABLE favorites ADD CONSTRAINT fk_favorites_user_id FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE favorites ADD CONSTRAINT fk_favorites_truck_id FOREIGN KEY (truck_id) REFERENCES trucks(id);
ALTER TABLE favorites ADD CONSTRAINT fk_favorites_order_id FOREIGN KEY (order_id) REFERENCES orders(id);
ALTER TABLE orders_users ADD CONSTRAINT fk_orders_users_order_id FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE;
ALTER TABLE orders_users ADD CONSTRAINT fk_orders_users_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE trucks ADD CONSTRAINT fk_trucks_truck_type_id FOREIGN KEY (truck_type_id) REFERENCES truck_types(id) ON DELETE CASCADE;
ALTER TABLE users ADD CONSTRAINT fk_users_companies FOREIGN KEY (company_id) REFERENCES companies(id);
ALTER TABLE phones ADD CONSTRAINT fk_phones_users FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE users_roles ADD CONSTRAINT fk_users_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE users_roles ADD CONSTRAINT fk_users_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE;
ALTER TABLE users_trucks ADD CONSTRAINT fk_users_trucks_truck_id FOREIGN KEY (truck_id) REFERENCES trucks(id) ON DELETE CASCADE;
ALTER TABLE users_trucks ADD CONSTRAINT fk_users_trucks_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

