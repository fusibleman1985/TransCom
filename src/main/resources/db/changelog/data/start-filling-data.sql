-- Вставляем данные в таблицу компаний
INSERT INTO companies (company_name, company_role, license_id, rating, company_status, created_date, updated_date)
VALUES ('Alpha Logistics', 'BROKER', 'LIC-001', 85, 'ACTIVE', NOW(), NOW()),
       ('Beta Freight', 'CARRIER', 'LIC-002', 92, 'ACTIVE', NOW(), NOW()),
       ('Gamma Transport', 'BROKER', 'LIC-003', 78, 'BLOCKED', NOW(), NOW());


-- Вставляем данные в таблицу заказов
INSERT INTO orders (id, weight, price, description, order_status)
VALUES ('e1e2e3e4-e5f6-7890-abcd-ef1234567890', 1000, 1500.00, 'Order for Alpha Logistics', 'CREATED'),
       ('e1e2e3e4-e5f6-7890-abcd-ef1234567891', 500, 750.00, 'Order for Beta Freight', 'ACCEPTED'),
       ('e1e2e3e4-e5f6-7890-abcd-ef1234567892', 1200, 1800.00, 'Order for Gamma Transport', 'COMPLETED');


-- Вставляем данные в таблицу ролей
INSERT INTO roles (role_name, access_role, description)
VALUES ('SuperAdmin', 'SUPERADMIN', 'Administrator with all permissions in all database'),
       ('Admin', 'ADMIN', 'Administrator with all permissions in company'),
       ('Dispatcher', 'DISPATCHER', 'Responsible for managing orders'),
       ('Driver', 'DRIVER', 'Driver role with limited access'),
       ('Customer', 'CUSTOMER', 'End user accessing the service');

-- Вставляем данные в таблицу типов траков
INSERT INTO truck_types (short_name, full_name, length_meters, capacity_cubic_units, image_truck_type_name)
VALUES ('DRYV', 'Dry Van', 16.0, 50.0, 'dry_van.jpg'),                                        -- id = 1
       ('VAN', 'Standard Van', 14.0, 40.0, 'van.jpg'),                                        -- id = 2
       ('CITYVAN', 'City Van', 6.0, 3.0, 'city_van.jpg'),                                     -- id = 3
       ('CURT', 'Curtain Side Trailer', 18.0, 55.0, 'curtain_side.jpg'),                      -- id = 4
       ('TAIL', 'Tail Lift Trailer', 15.0, 48.0, 'tail_lift.jpg'),                            -- id = 5
       ('FLAT', 'Flatbed', 17.0, 60.0, 'flatbed.jpg'),                                        -- id = 6
       ('MOVE', 'Moving Floor', 15.0, 55.0, 'moving_floor.jpg'),                              -- id = 7
       ('STEP', 'Step Deck Trailer', 18.5, 65.0, 'step_deck.jpg'),                            -- id = 8
       ('LOWB', 'Lowboy Trailer', 13.0, 45.0, 'lowboy.jpg'),                                  -- id = 9
       ('DBLE', 'Double Drop Trailer', 20.0, 70.0, 'double_drop.jpg'),                        -- id = 10
       ('EXTD', 'Extendable Flatbed Trailer', 22.0, 75.0, 'extendable_flatbed.jpg'),          -- id = 11
       ('CONT', 'Container Truck', 12.0, 50.0, 'container_truck.jpg'),                        -- id = 12
       ('SIDE', 'Side Wall Trailer', 14.0, 55.0, 'side_wall.jpg'),                            -- id = 13
       ('FBVS', 'Flat Body with Vertical Sides', 16.0, 50.0, 'flat_body_vertical_sides.jpg'), -- id = 14
       ('REFR', 'Refrigerated Trailer', 16.0, 45.0, 'refrigerated_trailer.jpg'),              -- id = 15
       ('REEF', 'Reefer Trailer', 17.0, 50.0, 'reefer_trailer.jpg'),                          -- id = 16
       ('INSU', 'Insulated Trailer', 16.0, 47.0, 'insulated_trailer.jpg'),                    -- id = 17
       ('MCTR', 'Multicar Trailer', 18.0, 55.0, 'multicar_trailer.jpg'),                      -- id = 18
       ('CARR', 'Car Hauler', 17.0, 50.0, 'car_hauler.jpg'),                                  -- id = 19
       ('DUMP', 'Dump Truck', 12.0, 40.0, 'dump_truck.jpg'),                                  -- id = 110
       ('TANK', 'Tank Trailer', 15.0, 60.0, 'tank_trailer.jpg'),                              -- id = 21
       ('EDIB', 'Tank for Edible Liquids', 14.0, 50.0, 'tank_edible_liquids.jpg'),            -- id = 22
       ('CHEM', 'General Chemical Trailer', 15.0, 55.0, 'general_chemical_trailer.jpg'),      -- id = 23
       ('FUEL', 'Fuel Tanker', 13.0, 45.0, 'fuel_tanker.jpg'),                                -- id = 24
       ('LNGG', 'LNG/LPG Gas Tank Truck', 14.0, 52.0, 'lng_lpg_truck.jpg'),                   -- id = 25
       ('CYLN', 'Gas Cylinder Truck', 12.0, 40.0, 'gas_cylinder_truck.jpg'),                  -- id = 26
       ('BTMN', 'Bitumen Tank Trailer', 16.0, 50.0, 'bitumen_tank_trailer.jpg'),              -- id = 27
       ('FISH', 'Fish Carrier', 14.0, 48.0, 'fish_carrier.jpg'),                              -- id = 28
       ('LFIS', 'Live Fish Carrier', 14.0, 48.0, 'live_fish_carrier.jpg'),                    -- id = 29
       ('POLE', 'Pole Type Trailer', 18.0, 60.0, 'pole_type_trailer.jpg'),                    -- id = 210
       ('LOGG', 'Logging Truck', 17.0, 55.0, 'logging_truck.jpg'),                            -- id = 31
       ('TIMB', 'Timber Lorry', 17.0, 55.0, 'timber_lorry.jpg'),                              -- id = 32
       ('DRBK', 'Dry Bulk Trailer', 16.0, 50.0, 'dry_bulk_trailer.jpg'),                      -- id = 33
       ('CEMT', 'Cement Tank Trailer', 15.0, 48.0, 'cement_tank_trailer.jpg');
-- id = 34

-- Вставляем данные в таблицу траков
INSERT INTO trucks (id, length, weight, capacity, truck_status, location, truck_type_id)
VALUES ('f1f2f3f4-f5f6-7890-abcd-ef1234567890', 6, 2000, 15.0, 'AVAILABLE', 'Location A', 1),    -- Truck for Alpha Logistics
       ('f1f2f3f4-f5f6-7890-abcd-ef1234567891', 8, 3000, 25.0, 'IN_TRANSIT', 'Location B', 2),   -- Truck for Beta Freight
       ('f1f2f3f4-f5f6-7890-abcd-ef1234567892', 10, 4000, 35.0, 'AVAILABLE', 'Location C', 3),   -- Truck for Gamma Transport
       ('f1f2f3f4-f5f6-7890-abcd-ef1234567893', 12, 5000, 45.0, 'UNAVAILABLE', 'Location D', 4), -- Truck for Alpha Logistics
       ('f1f2f3f4-f5f6-7890-abcd-ef1234567894', 14, 6000, 50.0, 'LOADING', 'Location E', 15),    -- Truck for Beta Freight
       ('f1f2f3f4-f5f6-7890-abcd-ef1234567895', 14, 6000, 50.0, 'UNLOADING', 'Location E', 15);

-- Вставляем данные в таблицу пользователей
INSERT INTO users (id, login, password, first_name, last_name, email, created_at, updated_at, user_status, company_id)
VALUES ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'john_doe', 'Password+123', 'John', 'Doe', 'john.doe@alphalogistics.com', NOW(),
        NOW(), 'ACTIVE', NULL),
       ('a1b2c3d4-e5f6-7890-abcd-ef1234567891', 'jane_smith', 'Password+123', 'Jane', 'Smith', 'jane.smith@alphalogistics.com',
        NOW(), NOW(), 'ACTIVE', 1),
       ('a1b2c3d4-e5f6-7890-abcd-ef1234567896', 'ivan_smith', 'Password+123', 'Ivan', 'Smith', 'ivan.smith@alphalogistics.com',
        NOW(), NOW(), 'ACTIVE', 1),
       ('a1b2c3d4-e5f6-7890-abcd-ef1234567892', 'peter_parker', 'Password+123', 'Peter', 'Parker', 'peter.parker@betafreight.com',
        NOW(), NOW(), 'ACTIVE', 2),
       ('a1b2c3d4-e5f6-7890-abcd-ef1234567893', 'mary_jane', 'Password+123', 'Mary', 'Jane', 'mary.jane@betafreight.com', NOW(),
        NOW(), 'BLOCKED', 3),
       ('a1b2c3d4-e5f6-7890-abcd-ef1234567894', 'tony_stark', 'Password+123', 'Tony', 'Stark', 'tony.stark@gammatransport.com',
        NOW(), NOW(), 'ACTIVE', 2),
       ('a1b2c3d4-e5f6-7890-abcd-ef1234567895', 'bruce_wayne', 'Password+123', 'Bruce', 'Wayne', 'bruce.wayne@example.com', NOW(),
        NOW(), 'INACTIVE', NULL);

-- Вставляем данные в таблицу телефонов
INSERT INTO phones (phone_number, user_id)
VALUES ('555-1234', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890'),
       ('555-9999', 'a1b2c3d4-e5f6-7890-abcd-ef1234567891'),
       ('555-1234', 'a1b2c3d4-e5f6-7890-abcd-ef1234567891'),
       ('555-5678', 'a1b2c3d4-e5f6-7890-abcd-ef1234567892'),
       ('555-9876', 'a1b2c3d4-e5f6-7890-abcd-ef1234567893'),
       ('555-6543', 'a1b2c3d4-e5f6-7890-abcd-ef1234567894'),
       ('555-4321', 'a1b2c3d4-e5f6-7890-abcd-ef1234567895'),
       ('555-9999', 'a1b2c3d4-e5f6-7890-abcd-ef1234567896'),
       ('555-8888', 'a1b2c3d4-e5f6-7890-abcd-ef1234567896');

-- Вставляем данные в таблицу пользователей-ордеров
INSERT INTO orders_users (order_id, user_id)
VALUES ('e1e2e3e4-e5f6-7890-abcd-ef1234567890', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890'), -- John Doe
       ('e1e2e3e4-e5f6-7890-abcd-ef1234567891', 'a1b2c3d4-e5f6-7890-abcd-ef1234567891'), -- Jane Smith
       ('e1e2e3e4-e5f6-7890-abcd-ef1234567892', 'a1b2c3d4-e5f6-7890-abcd-ef1234567892');
-- Peter Parker

-- Вставляем данные в таблицу пользователей-ролей
INSERT INTO users_roles (user_id, role_id)
VALUES ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 1), -- John Doe: SUPERADMIN
       ('a1b2c3d4-e5f6-7890-abcd-ef1234567891', 2), -- Jane Smith: DISPATCHER
       ('a1b2c3d4-e5f6-7890-abcd-ef1234567891', 3), -- Jane Smith: ADMIN
       ('a1b2c3d4-e5f6-7890-abcd-ef1234567892', 4), -- Peter Parker: DRIVER
       ('a1b2c3d4-e5f6-7890-abcd-ef1234567893', 4), -- Mary Jane: DRIVER
       ('a1b2c3d4-e5f6-7890-abcd-ef1234567896', 4), -- Ivan Smith: DRIVER
       ('a1b2c3d4-e5f6-7890-abcd-ef1234567895', 5), -- Bruce Wayne: CUSTOMER
       ('a1b2c3d4-e5f6-7890-abcd-ef1234567894', 5);
-- Tony Stark: CUSTOMER

-- Вставляем данные в таблицу связи пользователей и траков
INSERT INTO users_trucks (truck_id, user_id)
VALUES ('f1f2f3f4-f5f6-7890-abcd-ef1234567890', 'a1b2c3d4-e5f6-7890-abcd-ef1234567891'),
       ('f1f2f3f4-f5f6-7890-abcd-ef1234567891', 'a1b2c3d4-e5f6-7890-abcd-ef1234567892'),
       ('f1f2f3f4-f5f6-7890-abcd-ef1234567892', 'a1b2c3d4-e5f6-7890-abcd-ef1234567893'),
       ('f1f2f3f4-f5f6-7890-abcd-ef1234567894', 'a1b2c3d4-e5f6-7890-abcd-ef1234567896'),
       ('f1f2f3f4-f5f6-7890-abcd-ef1234567895', 'a1b2c3d4-e5f6-7890-abcd-ef1234567891');

-- Вставляем данные в таблицу избранного
INSERT INTO favorites (id, user_id, truck_id, order_id)
VALUES (1, 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', NULL, 'e1e2e3e4-e5f6-7890-abcd-ef1234567890'), -- John Doe: Order in favorites
       (2, 'a1b2c3d4-e5f6-7890-abcd-ef1234567892', 'f1f2f3f4-f5f6-7890-abcd-ef1234567892',
        NULL),                                                                                    -- Peter Parker: Truck in favorites
       (3, 'a1b2c3d4-e5f6-7890-abcd-ef1234567893', NULL, 'e1e2e3e4-e5f6-7890-abcd-ef1234567891');
-- Mary Jane: Order in favorites
