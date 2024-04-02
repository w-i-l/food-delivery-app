import MockedData.CustomerGenerator;
import MockedData.DriverGenerator;
import MockedData.RestaurantGenerator;
import Models.Customer;
import Models.Driver.Driver;
import Models.Order.Order;
import Models.Product.ProductInterface;
import Models.Restaurant;
import Services.CustomerService;
import Services.DriverService;
import Services.OrderService;
import Services.RestaurantService;

import java.util.*;

public class Main {
    public static void main(String[] args) {
//        List<Restaurant> restaurants = RestaurantGenerator.generateRestaurants();
//        RestaurantService restaurantService = new RestaurantService(restaurants);
//        restaurantService.listAllRestaurants();
//        restaurantService.viewMenuFromRestaurant(1);
//
//        List<Order> orders = new ArrayList<Order>();
//        Order order = new Order(
//                CustomerGenerator.generateCustomers().get(0),
//                restaurants.get(0),
//                DriverGenerator.generateDrivers().get(0),
//                0.0,
//                new Hashtable<>()
//        );
//        orders.add(order);
//        OrderService orderService = new OrderService(orders);
//        orderService.listAllOrders();

//        List<Driver> drivers = DriverGenerator.generateDrivers();
//        DriverService driverService = new DriverService(drivers);
//        driverService.listAllDrivers();

        List<Customer> customers = CustomerGenerator.generateCustomers();
        CustomerService customerService = new CustomerService(customers);
        for (Customer customer : customers) {
            customerService.addCustomer(customer);
        }
        customerService.removeCustomer(customers.get(0));
        customerService.listAllCustomers();

    }
}