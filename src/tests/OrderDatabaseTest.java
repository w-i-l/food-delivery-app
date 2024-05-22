package tests;

import database.*;
import models.order.Order;
import models.order.OrderStatus;
import models.product.ProductInterface;
import models.product.ProductItem;

import java.util.Map;
import java.util.HashMap;

public class OrderDatabaseTest {
    public static void test() {
        System.out.println("\n  ------ Order Test ------");
        OrderRepository.initConnection();
        RestaurantRepository.initConnection();
        CustomerRepository.initConnection();
        ProductRepository.initConnection();
        DriverRepository.initConnection();

        getAllOrders();
        addOrder();
        updateOrder();
        deleteOrder();
        addProductToOrder();
        deleteProductFromOrder();
        System.out.println("[Order Test PASSED] All tests passed successfully\n");
    }

    private static void getAllOrders() {
        System.out.println("Getting all orders...");
        OrderRepository.getAllOrders();
    }

    private static void addOrder() {
        System.out.println("Adding an order...");
        Map<ProductInterface, Integer> products = new HashMap<>();
        products.put(new ProductItem(1, "Product 1", 10.0), 2);
        products.put(new ProductItem(2, "Product 2", 20.0), 1);
        Order order = new Order(10, CustomerRepository.getCustomerById(1), RestaurantRepository.getRestaurantById(1), DriverRepository.getDriverById(1), OrderStatus.PENDING, products);
        OrderRepository.addOrder(order);
    }

    private static void deleteOrder() {
        System.out.println("Deleting an order...");
        OrderRepository.deleteOrder(10);
    }

    private static void updateOrder() {
        System.out.println("Updating an order...");
        Map<ProductInterface, Integer> products = new HashMap<>();
        products.put(new ProductItem(1, "Product 1", 10.0), 2);
        products.put(new ProductItem(2, "Product 2", 20.0), 1);
        Order order = new Order(10, CustomerRepository.getCustomerById(1), RestaurantRepository.getRestaurantById(1), DriverRepository.getDriverById(1), OrderStatus.ACCEPTED, products);
        OrderRepository.updateOrder(order);
    }

    private static void addProductToOrder() {
        System.out.println("Adding a product to order...");
        Order order = new Order(1, null, null, null, OrderStatus.PENDING, new java.util.HashMap<>());
        ProductInterface product = new ProductItem(2, "Mocked Product", 5.0);
        OrderRepository.addProductToOrder(product.getId(), order.getId());
    }

    private static void deleteProductFromOrder() {
        System.out.println("Deleting a product from order...");
        Order order = new Order(1, null, null, null, OrderStatus.PENDING, new java.util.HashMap<>());
        ProductInterface product = new ProductItem(2, "Mocked Product", 5.0);
        OrderRepository.deleteProductFromOrder(product.getId(), order.getId());
    }
}
