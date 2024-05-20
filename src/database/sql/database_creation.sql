-- Address Table
CREATE TABLE IF NOT EXISTS Address (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL
    );

-- Customer Table
CREATE TABLE IF NOT EXISTS Customer (
                                        id INT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
    address_id INT,
    FOREIGN KEY (address_id) REFERENCES Address(id) ON DELETE CASCADE
    );

-- Driver Table
CREATE TABLE IF NOT EXISTS Driver (
                                      id INT PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL,
    type ENUM('BIKE', 'CAR', 'PEDESTRIAN') NOT NULL,
    rating INT NOT NULL
    );

-- Restaurant Table (created before referencing tables)
CREATE TABLE IF NOT EXISTS Restaurant (
                                          id INT PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL,
    address_id INT,
    FOREIGN KEY (address_id) REFERENCES Address(id) ON DELETE CASCADE
    );

-- Product Table to handle different product types
CREATE TABLE IF NOT EXISTS Product (
                                       id INT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    description TEXT,
    type ENUM('ITEM', 'SPECIAL', 'MENU') NOT NULL
    );

-- MenuItems Table to handle the many-to-many relationship between Menu and ProductItem
CREATE TABLE IF NOT EXISTS MenuItems (
                                         menu_id INT,
                                         product_item_id INT,
                                         PRIMARY KEY (menu_id, product_item_id),
    FOREIGN KEY (menu_id) REFERENCES Product(id) ON DELETE CASCADE,
    FOREIGN KEY (product_item_id) REFERENCES Product(id) ON DELETE CASCADE
    );

-- RestaurantProducts Table to handle the many-to-many relationship between Restaurant and ProductInterface
CREATE TABLE IF NOT EXISTS RestaurantProducts (
                                                  restaurant_id INT,
                                                  product_id INT,
                                                  PRIMARY KEY (restaurant_id, product_id),
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Product(id) ON DELETE CASCADE
    );

-- Order Table (created last to reference all other tables)
CREATE TABLE IF NOT EXISTS _Order (
                                      id INT PRIMARY KEY,
                                      customer_id INT,
                                      restaurant_id INT,
                                      driver_id INT,
                                      status ENUM('PENDING', 'ACCEPTED', 'REJECTED', 'DELIVERED') NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES Customer(id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id) ON DELETE CASCADE,
    FOREIGN KEY (driver_id) REFERENCES Driver(id) ON DELETE CASCADE
    );
