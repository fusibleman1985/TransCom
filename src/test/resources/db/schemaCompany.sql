DROP TABLE IF EXISTS companies;

-- Creating table for companies
CREATE TABLE companies
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name   VARCHAR(255),
    company_role   VARCHAR(255),
    license_id     VARCHAR(255),
    rating         INTEGER,
    company_status VARCHAR(255),
    created_date   TIMESTAMP,
    updated_date   TIMESTAMP
);