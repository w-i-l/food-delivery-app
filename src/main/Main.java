package main;

import database.*;
import models.address.AddressFactory;
import models.customer.Customer;
import models.driver.Driver;
import models.order.Order;
import models.restaurant.Restaurant;
import services.*;
import services.menu.MenuService;
import tests.*;
import util.ScannerHelper;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        initialiseConnection();
        initialiseMenu();
        MenuService menuService = MenuService.getInstance();
        menuService.initMenuItems();
        menuService.mainLoop();
        Connector.closeConnection(false);
    }

    private static void initialiseConnection() {
        Connector.init();
        AddressRepository.initConnection();
        CustomerRepository.initConnection();
        DriverRepository.initConnection();
        OrderRepository.initConnection();
        ProductRepository.initConnection();
        RestaurantRepository.initConnection();
    }

    private static void initialiseMenu() {
        Scanner scanner = new Scanner(System.in);
        ScannerHelper.init(scanner);

        Integer maxId = AddressRepository.getMaximumId();
        AddressFactory.setID(maxId + 1);

        List<Restaurant> restaurants = RestaurantRepository.getAllRestaurants();
        RestaurantService restaurantService = new RestaurantService(restaurants);

        List<Driver> drivers = DriverRepository.getAllDrivers();
        DriverService driverService = new DriverService(drivers);

        List<Customer> customers = CustomerRepository.getAllCustomers();
        CustomerService customerService = new CustomerService(customers);

        List<Order> orders = OrderRepository.getAllOrders();
        OrderService orderService = new OrderService(orders);

        MenuService menuService = MenuService.init(
                restaurantService,
                orderService,
                customerService,
                driverService,
                scanner
        );
    }
}