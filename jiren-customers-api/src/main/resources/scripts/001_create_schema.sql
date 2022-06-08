--liquibase formatted sql
--changeset antonioduran:create_customers_schema

CREATE SCHEMA IF NOT EXISTS customers;
ALTER DEFAULT PRIVILEGES IN SCHEMA customers GRANT ALL ON TABLES TO thor;
ALTER DEFAULT PRIVILEGES IN SCHEMA customers GRANT ALL PRIVILEGES ON FUNCTIONS TO thor;

GRANT USAGE ON SCHEMA customers TO thor;