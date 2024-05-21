package tests;

import database.*;
import models.order.Order;
import models.order.OrderStatus;
import models.product.ProductInterface;
import models.product.ProductItem;

import java.util.Dictionary;
import java.util.Hashtable;

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
        System.out.println("[Order Test PASSED] All tests passed successfully\n");
    }

    private static void getAllOrders() {
        System.out.println("Getting all orders...");
         for(Order order: OrderRepository.getAllOrders()) {
             order.showOrderDetails();
         }
    }

    private static void addOrder() {
        System.out.println("Adding an order...");
        Dictionary<ProductInterface, Integer> products = new Hashtable<>();
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
        Dictionary<ProductInterface, Integer> products = new Hashtable<>();
        products.put(new ProductItem(1, "Product 1", 10.0), 2);
        products.put(new ProductItem(2, "Product 2", 20.0), 1);
        Order order = new Order(10, CustomerRepository.getCustomerById(1), RestaurantRepository.getRestaurantById(1), DriverRepository.getDriverById(1), OrderStatus.ACCEPTED, products);
        OrderRepository.updateOrder(order);
    }
}
