package models.order;

import models.customer.Customer;
import models.customer.CustomerFactory;
import models.driver.Driver;
import models.driver.DriverFactory;
import models.product.*;
import models.restaurant.Restaurant;
import models.restaurant.RestaurantFactory;
import util.ScannerHelper;

import java.util.Map;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrderFactory {
    static private Integer ID = 0;

    public static Order createOrder(Customer customer, Restaurant restaurant, Driver driver,  OrderStatus status, Map<ProductInterface, Integer> products) {
        return new Order(ID++, customer, restaurant, driver, status, products);
    }

    public static Order createOrder(Integer id, Customer customer, Restaurant restaurant, Driver driver, OrderStatus status, Map<ProductInterface, Integer> products) {
        ID = Math.max(ID, id + 1);
        return new Order(id, customer, restaurant, driver, status, products);
    }

    public static Order createOrder(Scanner scanner) {
        Customer customer;
        Restaurant restaurant;
        Driver driver;
        Double price;
        Map<ProductInterface, Integer> products = new HashMap<>();

        System.out.println("Enter customer details");
        customer = CustomerFactory.createCustomer(scanner);

        System.out.println("Enter restaurant details");
        restaurant = RestaurantFactory.createRestaurant(scanner);

        System.out.println("Enter driver details");
        driver = DriverFactory.createDriver(scanner);

        price = ScannerHelper.nextDouble("Enter price: ");

        String statusString = ScannerHelper.nextLine("Enter order status(PENDING, ACCEPTED, REJECTED, DELIVERED): ");
        OrderStatus status = OrderStatus.fromString(statusString);

        int productCount = ScannerHelper.nextInt("Enter number of products: ");

        for (int i = 0; i < productCount; i++) {
            int productType = ScannerHelper.nextInt("Enter product type (1 - ProductItem, 2 - SpecialProduct, 3 - Menu): ");
            switch (productType) {
                case 1:
                    ProductItem productItem = ProductFactory.createProductItem(scanner);
                    Integer quantity = ScannerHelper.nextInt("Enter quantity: ");
                    products.put(productItem, quantity);
                    break;
                case 2:
                    SpecialProduct specialProduct = ProductFactory.createSpecialProduct(scanner);
                    quantity = ScannerHelper.nextInt("Enter quantity: ");
                    products.put(specialProduct, quantity);
                    break;
                case 3:
                    Menu menu = ProductFactory.createMenu(scanner);
                    quantity = ScannerHelper.nextInt("Enter quantity: ");
                    products.put(menu, quantity);
                    break;
                default:
                    System.out.println("Invalid product type");
                    break;
            }
        }

        return new Order(ID++, customer, restaurant, driver, status, products);
    }
}
