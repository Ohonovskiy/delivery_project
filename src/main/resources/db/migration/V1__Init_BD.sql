-- Create tables
CREATE TABLE delivery.delivery_men (
    delivery_man_id BIGSERIAL NOT NULL,
    delivery_man_geolocation_x FLOAT(53),
    delivery_man_geolocation_y FLOAT(53),
    delivery_man_birthday_date TIMESTAMP(6),
    delivery_man_contact_info VARCHAR(255),
    delivery_man_email VARCHAR(255) UNIQUE,
    delivery_man_first_name VARCHAR(255),
    delivery_man_last_name VARCHAR(255),
    delivery_man_password VARCHAR(255),
    delivery_man_role VARCHAR(255) CHECK (delivery_man_role IN ('ROLE_ADMIN', 'ROLE_USER', 'ROLE_DELIVERY_MAN', 'ROLE_SHOP')),
    delivery_man_status VARCHAR(255) CHECK (delivery_man_status IN ('ACTIVE', 'BANNED')),
    PRIMARY KEY (delivery_man_id)
);

CREATE TABLE delivery.manufacturers (
    manufacturer_id BIGSERIAL NOT NULL,
    manufacturer_is_active BOOLEAN,
    manufacturer_name VARCHAR(255),
    PRIMARY KEY (manufacturer_id)
);

CREATE TABLE delivery.order_product (
    order_id_fk BIGINT NOT NULL,
    product_id_fk BIGINT NOT NULL
);
CREATE TABLE delivery.orders (
    order_id BIGSERIAL NOT NULL,
    delivery_man_id_fk BIGINT UNIQUE,
    order_complete_time TIMESTAMP(6),
    order_place_time TIMESTAMP(6),
    user_id_fk BIGINT,
    order_status VARCHAR(255) CHECK (order_status IN ('WAITING', 'TAKEN', 'CANCELED', 'DONE')),
    PRIMARY KEY (order_id)
);

CREATE TABLE delivery.products (
    product_id BIGSERIAL NOT NULL,
    product_price FLOAT(53),
    product_quantity INTEGER,
    manufacturer_id_fk BIGINT,
    product_expire_date TIMESTAMP(6),
    product_description VARCHAR(255),
    product_image VARCHAR(255),
    product_name VARCHAR(255),
    PRIMARY KEY (product_id)
);

CREATE TABLE delivery.shop_product (
    product_id_fk BIGINT NOT NULL,
    shop_id_fk BIGINT NOT NULL
);

CREATE TABLE delivery.shops (
    shop_id BIGSERIAL NOT NULL,
    shop_geolocation_x FLOAT(53),
    shop_geolocation_y FLOAT(53),
    shop_description VARCHAR(255),
    shop_image VARCHAR(255),
    shop_name VARCHAR(255),
    shop_wide_image VARCHAR(255),
    PRIMARY KEY (shop_id)
);

CREATE TABLE delivery.user_product (
    product_id_fk BIGINT NOT NULL,
    user_id_fk BIGINT NOT NULL
);

CREATE TABLE delivery.users (
    user_id BIGSERIAL NOT NULL,
    user_geolocation_x FLOAT(53),
    user_geolocation_y FLOAT(53),
    user_birthday_date TIMESTAMP(6),
    user_contact_info VARCHAR(255),
    user_email VARCHAR(255) UNIQUE,
    user_first_name VARCHAR(255),
    user_last_name VARCHAR(255),
    user_password VARCHAR(255),
    user_role VARCHAR(255) CHECK (user_role IN ('ROLE_ADMIN', 'ROLE_USER', 'ROLE_DELIVERY_MAN', 'ROLE_SHOP')),
    user_status VARCHAR(255) CHECK (user_status IN ('ACTIVE', 'BANNED')),
    PRIMARY KEY (user_id)
);

-- Add foreign key constraints with readable names
ALTER TABLE IF EXISTS delivery.order_product ADD CONSTRAINT FK_order_product_product_id FOREIGN KEY (product_id_fk) REFERENCES delivery.products;
ALTER TABLE IF EXISTS delivery.order_product ADD CONSTRAINT FK_order_product_order_id FOREIGN KEY (order_id_fk) REFERENCES delivery.orders;
ALTER TABLE IF EXISTS delivery.orders ADD CONSTRAINT FK_orders_delivery_man_id FOREIGN KEY (delivery_man_id_fk) REFERENCES delivery.delivery_men;
ALTER TABLE IF EXISTS delivery.orders ADD CONSTRAINT FK_orders_user_id FOREIGN KEY (user_id_fk) REFERENCES delivery.users;
ALTER TABLE IF EXISTS delivery.products ADD CONSTRAINT FK_products_manufacturer_id FOREIGN KEY (manufacturer_id_fk) REFERENCES delivery.manufacturers;
ALTER TABLE IF EXISTS delivery.shop_product ADD CONSTRAINT FK_shop_product_product_id FOREIGN KEY (product_id_fk) REFERENCES delivery.products;
ALTER TABLE IF EXISTS delivery.shop_product ADD CONSTRAINT FK_shop_product_shop_id FOREIGN KEY (shop_id_fk) REFERENCES delivery.shops;
ALTER TABLE IF EXISTS delivery.user_product ADD CONSTRAINT FK_user_product_product_id FOREIGN KEY (product_id_fk) REFERENCES delivery.products;
ALTER TABLE IF EXISTS delivery.user_product ADD CONSTRAINT FK_user_product_user_id FOREIGN KEY (user_id_fk) REFERENCES delivery.users;
