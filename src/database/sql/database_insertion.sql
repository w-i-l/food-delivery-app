-- Insert into Address Table
INSERT INTO Address (id, name, latitude, longitude) VALUES
                                                        (0, '123 Main St', 40.712776, -74.005974),
                                                        (1, '456 Elm St', 34.052235, -118.243683),
                                                        (2, '789 Oak St', 41.878113, -87.629799);

-- Insert into Customer Table
INSERT INTO Customer (id, name, address_id) VALUES
                                                (0, 'John Doe', 0),
                                                (1, 'Jane Smith', 1),
                                                (2, 'Alice Johnson', 2);

-- Insert into Driver Table
INSERT INTO Driver (id, name, type, rating) VALUES
                                                (0, 'Driver A', 'BIKE', 5),
                                                (1, 'Driver B', 'CAR', 4),
                                                (2, 'Driver C', 'PEDESTRIAN', 3);

-- Insert into Product Table (ProductItem)
INSERT INTO Product (id, name, price, description, type) VALUES
                                                             (0, 'Burger', 5.99, NULL, 'ITEM'),
                                                             (1, 'Pizza', 8.99, NULL, 'ITEM'),
                                                             (2, 'Salad', 4.99, NULL, 'ITEM');

-- Insert into Product Table (SpecialProduct)
INSERT INTO Product (id, name, price, description, type) VALUES
                                                             (3, 'Deluxe Burger', 7.99, 'A burger with extra toppings', 'SPECIAL'),
                                                             (4, 'Supreme Pizza', 10.99, 'Pizza with all the toppings', 'SPECIAL'),
                                                             (5, 'Caesar Salad', 6.99, 'Salad with Caesar dressing', 'SPECIAL');

-- Insert into Product Table (Menu)
INSERT INTO Product (id, name, price, description, type) VALUES
                                                             (6, 'Lunch Menu', 15.99, 'Includes burger, fries, and drink', 'MENU'),
                                                             (7, 'Dinner Menu', 20.99, 'Includes steak, potatoes, and drink', 'MENU'),
                                                             (8, 'Breakfast Menu', 10.99, 'Includes eggs, bacon, and coffee', 'MENU');

-- Insert into MenuItems Table
INSERT INTO MenuItems (menu_id, product_item_id) VALUES
                                                     (6, 0), (6, 1), (6, 2), -- Lunch Menu includes Burger, Pizza, Salad
                                                     (7, 3), (7, 4), (7, 5), -- Dinner Menu includes Deluxe Burger, Supreme Pizza, Caesar Salad
                                                     (8, 0), (8, 3), (8, 1); -- Breakfast Menu includes Burger, Deluxe Burger, Pizza

-- Insert into Restaurant Table
INSERT INTO Restaurant (id, name, address_id) VALUES
                                                  (0, 'Restaurant A', 0),
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


