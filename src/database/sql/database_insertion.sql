-- Insert into Address Table
INSERT INTO Address (name, latitude, longitude) VALUES
                                                    ('123 Main St', 40.712776, -74.005974),
                                                    ('456 Elm St', 34.052235, -118.243683),
                                                    ('789 Oak St', 41.878113, -87.629799);

-- Insert into Customer Table
INSERT INTO Customer (id, name, address_id) VALUES
                                                (0, 'John Doe', 3),
                                                (1, 'Jane Smith', 1),
                                                (2, 'Alice Johnson', 2);

-- Insert into Driver Table
INSERT INTO Driver (id, name, type, rating) VALUES
                                                (0, 'Driver A', 'BIKE', 5),
                                                (1, 'Driver B', 'CAR', 4),
                                                (2, 'Driver C', 'PEDESTRIAN', 3);

-- Insert into Product Table (ProductItem)
INSERT INTO Product (id, name, price, type) VALUES
                            (0, 'Burger', 5.99, 'ITEM'),
                            (1, 'Pizza', 8.99, 'ITEM'),
                            (2, 'Salad', 4.99, 'ITEM');

-- Insert into Product Table (SpecialProduct)
INSERT INTO Product (id, name, price, available_until, type) VALUES
                                                                                        (3, 'Deluxe Burger', 7.99, '2023-12-31', 'SPECIAL'),
                                                                                        (4, 'Supreme Pizza', 10.99, '2023-12-31', 'SPECIAL'),
                                                                                        (5, 'Caesar Salad', 6.99, '2023-12-31', 'SPECIAL');

-- Insert into Product Table (Menu)
INSERT INTO Product (id, name, price, description, discount, type) VALUES
                                                                                        (6, 'Lunch Menu', 15.99, 'Includes burger, fries, and drink', 2.00, 'MENU'),
                                                                                        (7, 'Dinner Menu', 20.99, 'Includes steak, potatoes, and drink', 2.00, 'MENU'),
                                                                                        (8, 'Breakfast Menu', 10.99, 'Includes eggs, bacon, and coffee', 2.00, 'MENU');

-- Insert into MenuItems Table
INSERT INTO MenuItems (menu_id, product_item_id) VALUES
                                                     (6, 0), (6, 1), (6, 2), -- Lunch Menu includes Burger, Pizza, Salad
                                                     (7, 3), (7, 4), (7, 5), -- Dinner Menu includes Deluxe Burger, Supreme Pizza, Caesar Salad
                                                     (8, 0), (8, 3), (8, 1); -- Breakfast Menu includes Burger, Deluxe Burger, Pizza

-- Insert into Restaurant Table
INSERT INTO Restaurant (id, name, address_id) VALUES
                                                  (0, 'Restaurant A', 3),
                                                  (1, 'Restaurant B', 1),
                                                  (2, 'Restaurant C', 2);

-- Insert into RestaurantProducts Table
INSERT INTO RestaurantProducts (restaurant_id, product_id) VALUES
                                                               (0, 0), (0, 3), (0, 6), -- Restaurant A offers Burger, Deluxe Burger, Lunch Menu
                                                               (1, 1), (1, 4), (1, 7), -- Restaurant B offers Pizza, Supreme Pizza, Dinner Menu
                                                               (2, 2), (2, 5), (2, 8); -- Restaurant C offers Salad, Caesar Salad, Breakfast Menu

-- Insert into Order Table
INSERT INTO _Order (id, customer_id, restaurant_id, driver_id, status) VALUES
                                                                           (0, 0, 0, 0, 'PENDING'),
                                                                           (1, 1, 1, 1, 'ACCEPTED'),
                                                                           (2, 2, 2, 2, 'DELIVERED');

INSERT INTO OrderProducts (order_id, product_id, quantity) VALUES
                                                               (0, 0, 2), -- Order 0 contains 2 Burgers
                                                               (0, 3, 1), -- Order 0 contains 1 Deluxe Burger
                                                               (1, 1, 1), -- Order 1 contains 1 Pizza
                                                               (1, 4, 2), -- Order 1 contains 2 Supreme Pizzas
                                                               (2, 2, 3), -- Order 2 contains 3 Salads
                                                               (2, 5, 1); -- Order 2 contains 1 Caesar Salad
