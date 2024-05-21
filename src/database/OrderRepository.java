package database;

import models.customer.Customer;
import models.driver.Driver;
import models.order.Order;
import models.order.OrderFactory;
import models.order.OrderStatus;
import models.product.ProductInterface;
import models.restaurant.Restaurant;

import java.sql.Connection;

import java.sql.*;
import java.util.*;


public class OrderRepository extends BaseRepository {
    static private Connection connection = null;

    public static void initConnection() {
        printSuccess("OrderRepository: Connection initialized");
        connection = Connector.getConnection();
    }

    public static List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<Order>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT id, status, customer_id, restaurant_id, driver_id
            FROM _Order
            """);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String status = resultSet.getString("status");
                OrderStatus orderStatus = OrderStatus.fromString(status);
                Integer customerId = resultSet.getInt("customer_id");
                Integer restaurantId = resultSet.getInt("restaurant_id");
                Integer driverId = resultSet.getInt("driver_id");

                Customer customer = CustomerRepository.getCustomerById(customerId);
                Restaurant restaurant = RestaurantRepository.getRestaurantById(restaurantId);
                Driver driver = DriverRepository.getDriverById(driverId);

                PreparedStatement preparedStatement1 = connection.prepareStatement("""
                SELECT product_id, quantity
                FROM OrderProducts
                WHERE order_id = ?
                """);
                preparedStatement1.setInt(1, id);
                ResultSet resultSet1 = preparedStatement1.executeQuery();

                Dictionary<ProductInterface, Integer> products = new Hashtable<>();
                while (resultSet1.next()) {
                    Integer productId = resultSet1.getInt("product_id");
                    Integer quantity = resultSet1.getInt("quantity");
                    ProductInterface product = ProductRepository.getProductById(productId);
                    products.put(product, quantity);
                }

                Order order = OrderFactory.createOrder(id, customer, restaurant, driver, orderStatus, products);
                orders.add(order);
            }
        } catch (SQLException e) {
            printException(e);
        }

        return orders;
    }

    public static void addOrder(Order order) {
        try {
            Customer customer = order.getCustomer();
            CustomerRepository.addCustomer(customer);

            Restaurant restaurant = order.getRestaurant();
            RestaurantRepository.addRestaurant(restaurant);

            Driver driver = order.getDriver();
            DriverRepository.addDriver(driver);

            Integer id = order.getId();
            Integer customerId = order.getCustomer().getId();
            Integer restaurantId = order.getRestaurant().getId();
            Integer driverId = order.getDriver().getId();
            String status = order.getStatus().toString();
            PreparedStatement preparedStatement = connection.prepareStatement("""
            INSERT INTO _Order (id, status, customer_id, restaurant_id, driver_id)
            VALUES (?, ?, ?, ?, ?)
            """);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, status);
            preparedStatement.setInt(3, customerId);
            preparedStatement.setInt(4, restaurantId);
            preparedStatement.setInt(5, driverId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                String warningMessage = "Order (id:" + order.getId() + ") already exists";
                System.out.println(warningMessage);
            } else {
                printException(e);
            }
        }

        try {
            Enumeration<ProductInterface> productEnumeration = order.getProducts().keys();
            while (productEnumeration.hasMoreElements()) {
                ProductInterface product = productEnumeration.nextElement();
                Integer quantity = order.getProducts().get(product);

                PreparedStatement preparedStatement1 = connection.prepareStatement("""
                INSERT INTO OrderProducts (order_id, product_id, quantity)
                VALUES (?, ?, ?)
                """);
                preparedStatement1.setInt(1, order.getId());
                preparedStatement1.setInt(2, product.getId());
                preparedStatement1.setInt(3, quantity);

                preparedStatement1.executeUpdate();
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                String warningMessage = "OrderProducts (order_id:" + order.getId() + ") already exists";
                System.out.println(warningMessage);
            } else {
                printException(e);
            }
        }
    }

    public static void deleteOrder(Integer orderId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            DELETE FROM _Order
            WHERE id = ?
            """);
            preparedStatement.setInt(1, orderId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printException(e);
        }
    }

    public static void updateOrder(Order order) {
        try {
            CustomerRepository.addCustomer(order.getCustomer());
            RestaurantRepository.addRestaurant(order.getRestaurant());
            DriverRepository.addDriver(order.getDriver());

            PreparedStatement preparedStatement = connection.prepareStatement("""
            UPDATE _Order
            SET status = ?, customer_id = ?, restaurant_id = ?, driver_id = ?
            WHERE id = ?
            """);
            preparedStatement.setString(1, order.getStatus().toString());
            preparedStatement.setInt(2, order.getCustomer().getId());
            preparedStatement.setInt(3, order.getRestaurant().getId());
            preparedStatement.setInt(4, order.getDriver().getId());
            preparedStatement.setInt(5, order.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printException(e);
        }
    }

    public static Order getOrderById(Integer orderId) {
        Order order = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT id, status, customer_id, restaurant_id, driver_id
            FROM _Order
            WHERE id = ?
            """);
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String status = resultSet.getString("status");
                OrderStatus orderStatus = OrderStatus.fromString(status);
                Integer customerId = resultSet.getInt("customer_id");
                Integer restaurantId = resultSet.getInt("restaurant_id");
                Integer driverId = resultSet.getInt("driver_id");

                Customer customer = CustomerRepository.getCustomerById(customerId);
                Restaurant restaurant = RestaurantRepository.getRestaurantById(restaurantId);
                Driver driver = DriverRepository.getDriverById(driverId);

                PreparedStatement preparedStatement1 = connection.prepareStatement("""
                SELECT product_id, quantity
                FROM OrderProducts
                WHERE order_id = ?
                """);
                preparedStatement1.setInt(1, id);
                ResultSet resultSet1 = preparedStatement1.executeQuery();

                Dictionary<ProductInterface, Integer> products = new Hashtable<>();
                while (resultSet1.next()) {
                    Integer productId = resultSet1.getInt("product_id");
                    Integer quantity = resultSet1.getInt("quantity");
                    ProductInterface product = ProductRepository.getProductById(productId);
                    products.put(product, quantity);
                }

                order = OrderFactory.createOrder(id, customer, restaurant, driver, orderStatus, products);
            }
        } catch (SQLException e) {
            printException(e);
        }

        return order;
    }

    public static void addProductToOrder(Integer productId, Integer orderId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            INSERT INTO OrderProducts (order_id, product_id)
            VALUES (?, ?)
            """);
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, productId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                String warningMessage = "Product (id:" + productId + ") already exists in order (id:" + orderId + ")";
                System.out.println(warningMessage);
            } else {
                printException(e);
            }
        }
    }

    public static void deleteProductFromOrder(Integer productId, Integer orderId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            DELETE FROM OrderProducts
            WHERE order_id = ? AND product_id = ?
            """);
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, productId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printException(e);
        }
    }
}
