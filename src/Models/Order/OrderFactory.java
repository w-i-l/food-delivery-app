package Models.Order;

import Models.Customer.Customer;
import Models.Customer.CustomerFactory;
import Models.Driver.Driver;
import Models.Driver.DriverFactory;
import Models.Product.*;
import Models.Restaurant.Restaurant;
import Models.Restaurant.RestaurantFactory;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

public class OrderFactory {
    static final Integer orderId = 0;

    public static Order createOrder(Customer customer, Restaurant restaurant, Driver driver, Double price, Dictionary<ProductInterface, Integer> products) {
        return new Order(orderId, customer, restaurant, driver, price, products);
    }

    public static Order createOrder(Scanner scanner) {
        Customer customer;
        Restaurant restaurant;
        Driver driver;
        Double price;
        Dictionary<ProductInterface, Integer> products = new Hashtable<ProductInterface, Integer>();

        System.out.println("Enter customer details");
        customer = CustomerFactory.createCustomer(scanner);

        System.out.println("Enter restaurant details");
        restaurant = RestaurantFactory.createRestaurant(scanner);

        System.out.println("Enter driver details");
        driver = DriverFactory.createDriver(scanner);

        System.out.print("Enter price: ");
        price = scanner.nextDouble();

        System.out.print("Enter number of products: ");
        int productCount = scanner.nextInt();

        for (int i = 0; i < productCount; i++) {
            System.out.print("Enter product type (1 - ProductItem, 2 - SpecialProduct, 3 - Menu): ");
            int productType = scanner.nextInt();
            switch (productType) {
                case 1:
                    ProductItem productItem = ProductFactory.createProductItem(scanner);
                    Integer quantity;
                    System.out.print("Enter quantity: ");
                    quantity = scanner.nextInt();
                    products.put(productItem, quantity);
                    break;
                case 2:
                    SpecialProduct specialProduct = ProductFactory.createSpecialProduct(scanner);
                    System.out.print("Enter quantity: ");
                    quantity = scanner.nextInt();
                    products.put(specialProduct, quantity);
                    break;
                case 3:
                    Menu menu = ProductFactory.createMenu(scanner);
                    System.out.print("Enter quantity: ");
                    quantity = scanner.nextInt();
                    products.put(menu, quantity);
                    break;
                default:
                    System.out.println("Invalid product type");
                    break;
            }
        }

        return new Order(orderId, customer, restaurant, driver, price, products);
    }
}
