DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS phones;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
  id          UUID PRIMARY KEY,
  login       VARCHAR(50),
  password    VARCHAR(255),
  first_name  VARCHAR(50),
  last_name   VARCHAR(50),
  email       VARCHAR(100),
  created_at  TIMESTAMP,
  updated_at  TIMESTAMP,
  user_status VARCHAR(20)
);

-- Creating table for orders
CREATE TABLE orders
(
  id           UUID PRIMARY KEY,
  weight       INT,
  price        DECIMAL(10, 2),
  description  VARCHAR(255),
  order_status VARCHAR(20),
  user_id      UUID,
  FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Creating table for phones
CREATE TABLE phones
(
  id           BIGINT AUTO_INCREMENT PRIMARY KEY,
  phone_number VARCHAR(15) NOT NULL,
  user_id      UUID,
  FOREIGN KEY (user_id) REFERENCES users (id)
);