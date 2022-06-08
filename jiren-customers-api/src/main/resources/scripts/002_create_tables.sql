--liquibase formatted sql
--changeset antonioduran:create_customers_tables

CREATE TABLE IF NOT EXISTS customers.customer(
	guid VARCHAR(12) NOT NULL PRIMARY KEY,
	business_name VARCHAR(255) NOT NULL,
	trade_name VARCHAR(255) NULL,
	wave SMALLINT NULL,
	segment VARCHAR(3) NULL,
	work_area VARCHAR(50) NULL,
	sub_work_area VARCHAR(50) NULL,
	min_amount NUMERIC(22,10) NULL,
	channel_origin VARCHAR(25) NULL,
	adviser VARCHAR(255) NULL,
	dex_code VARCHAR(50) NULL,
	currency VARCHAR(3) NULL,
	state SMALLINT NULL,
	registry_date TIMESTAMP NULL,
	discharge_date TIMESTAMP NULL,
	allow_electronic_invoice BOOLEAN NULL,
	perfect_customer BOOLEAN NULL,
	image VARCHAR(255) NULL,
	prefix_product_code VARCHAR(6) NULL,
	erp_code INTEGER NULL,
	new_customer BOOLEAN NULL,
	updated_customer BOOLEAN NULL,
	active BOOLEAN NOT NULL,
	user_created VARCHAR(50) NOT NULL,
	date_created TIMESTAMP NOT NULL,
	user_updated VARCHAR(50) NULL,
	date_updated TIMESTAMP NULL
);

CREATE TABLE IF NOT EXISTS customers.ubigeo (
	guid varchar(6) NOT NULL PRIMARY KEY,
	district varchar(64) NOT NULL,
	province varchar(64) NOT NULL,
	department varchar(64) NOT NULL,
	CONSTRAINT uk_dis_pro_dep UNIQUE (district, province, department)
);

CREATE TABLE IF NOT EXISTS customers.customer_credit(
	guid VARCHAR(12) NOT NULL PRIMARY KEY,
	customer_guid VARCHAR(12) NOT NULL REFERENCES customers.customer (guid),
	erp_code INTEGER NOT NULL,
	total_line NUMERIC(22,10) NOT NULL,
	available_line NUMERIC(22,10) NOT NULL,
	reserved_line NUMERIC(22,10) NOT NULL,
	expiration_date TIMESTAMP NOT NULL,
	deadline_days INTEGER NOT NULL,
	active BOOLEAN NOT NULL,
	user_created VARCHAR(50) NOT NULL,
	date_created TIMESTAMP NOT NULL,
	user_updated VARCHAR(50) NULL,
	date_updated TIMESTAMP NULL
);

CREATE TABLE IF NOT EXISTS customers.document(
	guid VARCHAR(12) NOT NULL PRIMARY KEY,
	customer_guid VARCHAR(12) NOT NULL REFERENCES customers.customer (guid),
	type VARCHAR(15) NOT NULL,
	number VARCHAR(16) NOT NULL,
	doc_fiscal BOOLEAN NOT NULL,
	active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS customers.user(
	guid VARCHAR(12) NOT NULL PRIMARY KEY,
	customer_guid VARCHAR(12) NOT NULL REFERENCES customers.customer (guid),
	type_document VARCHAR(15) NOT NULL,
	number_document VARCHAR(16) NOT NULL,
	name VARCHAR(255) NOT NULL,
	first_last_name VARCHAR(255) NOT NULL,
	second_last_name VARCHAR(255) NULL,
	email VARCHAR(100) NULL,
	phone VARCHAR(15) NOT NULL,
	owner BOOLEAN NOT NULL,
	active BOOLEAN NOT NULL,
	user_created VARCHAR(50) NOT NULL,
	date_created TIMESTAMP NOT NULL,
	user_updated VARCHAR(50) NULL,
	date_updated TIMESTAMP NULL
);

CREATE TABLE IF NOT EXISTS customers.business(
	guid VARCHAR(12) NOT NULL PRIMARY KEY,
	customer_guid VARCHAR(12) NOT NULL REFERENCES customers.customer (guid),
	ubigeo_code VARCHAR(6) NOT NULL NOT NULL REFERENCES customers.ubigeo (guid),
	delivery_distance INTEGER NULL,
	delivery_cost NUMERIC(14,2),	
	address VARCHAR(255) NOT NULL,
	postal_code VARCHAR(5) NULL,
	reference VARCHAR(255) NULL,
	latitude NUMERIC(22,10) NOT NULL,
	longitude NUMERIC(22,10) NOT NULL,
	zone SMALLINT NULL,
	annex VARCHAR(255) NULL,
	active BOOLEAN NOT NULL,
	user_created VARCHAR(50) NOT NULL,
	date_created TIMESTAMP NOT NULL,
	user_updated VARCHAR(50) NULL,
	date_updated TIMESTAMP NULL
);

CREATE TABLE IF NOT EXISTS customers.time_window(
	guid VARCHAR(12) NOT NULL PRIMARY KEY,
	business_guid VARCHAR(12) NOT NULL REFERENCES customers.business (guid),	
	start_time VARCHAR(16) NOT NULL,
	end_time VARCHAR(16) NOT NULL,
	active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS customers.schedule(
	guid VARCHAR(12) NOT NULL PRIMARY KEY,
	business_guid VARCHAR(12) NOT NULL REFERENCES customers.business (guid),
	day VARCHAR(16) NOT NULL,
	start_time VARCHAR(16) NOT NULL,
	end_time VARCHAR(16) NOT NULL,
	attention BOOLEAN NOT NULL,
	active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS customers.legal_sections(
	guid VARCHAR(12) NOT NULL PRIMARY KEY,
	user_guid VARCHAR(12) NOT NULL REFERENCES customers.user (guid),
	type INTEGER NOT NULL,
	date TIMESTAMP NOT NULL,
	active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS customers.payment_type(
	guid VARCHAR(15) NOT NULL PRIMARY KEY,
	description VARCHAR(255) NOT NULL,
	name VARCHAR(50) NOT NULL,
	alternative_name VARCHAR(50) NOT NULL,
	sellin BOOLEAN NOT NULL,
	sellout BOOLEAN NOT NULL,
	mode VARCHAR(25) NOT NULL,
	index INTEGER NOT NULL,
	active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS customers.customer_payment_type(
	guid VARCHAR(12) NOT NULL,
	customer_guid VARCHAR(12) NOT NULL REFERENCES customers.customer (guid),
	payment_guid VARCHAR(15) NOT NULL REFERENCES customers.payment_type (guid),
	type SMALLINT NOT NULL,
	active BOOLEAN NOT NULL,
	CONSTRAINT uk_customer_payment_type UNIQUE (customer_guid, payment_guid, type)
);
