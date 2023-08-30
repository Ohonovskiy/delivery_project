
-- Insert initial data
INSERT INTO delivery.users (
    user_geolocation_x,
    user_geolocation_y,
    user_birthday_date,
    user_contact_info,
    user_email,
    user_first_name,
    user_last_name,
    user_password,
    user_role,
    user_status
) VALUES (
             23.9926927,
             49.4847065,
             TIMESTAMP '1990-05-15 12:30:45.000000',
             '0957117171',
             'user@example.com',
             'Jane',
             'Doe',
             '1234',
             'ROLE_USER',
             'ACTIVE'
         );

INSERT INTO delivery.delivery_men (
    delivery_man_geolocation_x,
    delivery_man_geolocation_y,
    delivery_man_birthday_date,
    delivery_man_contact_info,
    delivery_man_email,
    delivery_man_first_name,
    delivery_man_last_name,
    delivery_man_password,
    delivery_man_role,
    delivery_man_status
) VALUES (
             24.9926927,
             49.5947065,
             TIMESTAMP '1985-08-11 19:11:48.001000',
             'Contact info',
             'deliveryman@example.com',
             'John',
             'Doe',
             '1234',
             'ROLE_DELIVERY_MAN',
             'ACTIVE'
         );

INSERT INTO delivery.manufacturers (
    manufacturer_is_active,
    manufacturer_name
) VALUES (
             TRUE,
             'Apple'
         );

INSERT INTO delivery.manufacturers (
    manufacturer_is_active,
    manufacturer_name
) VALUES (
             TRUE,
             'Samsung'
         );

INSERT INTO delivery.products (
    product_price,
    product_quantity,
    manufacturer_id_fk,
    product_expire_date,
    product_description,
    product_image,
    product_name
) VALUES
      (999.99, 100, 1, NULL, 'iPhone X', 'https://cdn.alloallo.media/catalog/product/apple/iphone/iphone-x/iphone-x-silver.jpg', 'iPhone X'),
      (1199.99, 100, 1, NULL, 'iPhone 11', 'https://content2.rozetka.com.ua/goods/images/big/37357013.jpg', 'iPhone 11'),
      (1299.99, 100, 1, NULL, 'iPhone 12', 'https://content1.rozetka.com.ua/goods/images/big/175435404.jpg', 'iPhone 12'),
      (1399.99, 100, 1, NULL, 'iPhone 13', 'https://content1.rozetka.com.ua/goods/images/big/221214139.jpg', 'iPhone 13');

INSERT INTO delivery.products (
    product_price,
    product_quantity,
    manufacturer_id_fk,
    product_expire_date,
    product_description,
    product_image,
    product_name
) VALUES
      (899.99, 80, 2, NULL, 'Samsung Galaxy S10', 'https://content1.rozetka.com.ua/goods/images/big/266069648.jpg', 'Samsung Galaxy S10'),
      (999.99, 80, 2, NULL, 'Samsung Galaxy S20', 'https://content.rozetka.com.ua/goods/images/big/356173607.jpg', 'Samsung Galaxy S20'),
      (1099.99, 80, 2, NULL, 'Samsung Galaxy S21', 'https://content2.rozetka.com.ua/goods/images/big/245917791.jpg', 'Samsung Galaxy S21'),
      (1199.99, 80, 2, NULL, 'Samsung Galaxy S22', 'https://content2.rozetka.com.ua/goods/images/big/253280106.jpg', 'Samsung Galaxy S22');


INSERT INTO delivery.shops (
    shop_geolocation_x,
    shop_geolocation_y,
    shop_description,
    shop_image,
    shop_wide_image,
    shop_name
) VALUES (
             23.6926927,
             49.3947065,
             'Apple Store description',
             'https://www.icegif.com/wp-content/uploads/2022/11/icegif-1511.gif',
             'https://wallpaperswide.com/download/apple_logo_white-wallpaper-2560x800.jpg',
             'Apple Store'
         );

INSERT INTO delivery.shops (
    shop_geolocation_x,
    shop_geolocation_y,
    shop_description,
    shop_image,
    shop_wide_image,
    shop_name
) VALUES (
             23.9126927,
             49.4447065,
             'Samsung Store description',
             'https://i.pinimg.com/originals/ae/6b/fa/ae6bfa53d1743939f81c4f38388c036d.gif',
             'https://companieslogo.com/img/orig/005930.KS_BIG-0a7bc052.png?t=1647967269',
             'Samsung Store'
         );

INSERT INTO delivery.shop_product(
    product_id_fk,
    shop_id_fk
) VALUES
      (1,1),
      (2,1),
      (3,1),
      (4,1),
      (5,2),
      (6,2),
      (7,2),
      (8,2);

