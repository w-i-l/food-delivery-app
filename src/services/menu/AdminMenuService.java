package services.menu;

import database.CustomerRepository;
import database.DriverRepository;
import database.RestaurantRepository;
import models.customer.Customer;
import models.customer.CustomerFactory;
import models.driver.Driver;
import models.driver.DriverFactory;
import models.order.Order;
import models.restaurant.Restaurant;
import models.restaurant.RestaurantFactory;
import services.CustomerService;
import services.DriverService;
import services.OrderService;
import services.RestaurantService;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

public class AdminMenuService {
    static AdminMenuService instance = null;

    private Dictionary<Integer, String> menuItems = new Hashtable<Integer, String>();
    private RestaurantService restaurantService;
    private OrderService orderService;
    private CustomerService customerService;
    private DriverService driverService;
    private Scanner scanner;

    static public AdminMenuService init(
            RestaurantService restaurantService,
            OrderService orderService,
            CustomerService customerService,
            DriverService driverService,
            Scanner scanner
    ) {
        if (instance == null) {
            instance = new AdminMenuService(
                    restaurantService,
                    orderService,
                    customerService,
                    driverService,
                    scanner
            );
        }
        return instance;
    }

    private AdminMenuService(
            RestaurantService restaurantService,
            OrderService orderService,
            CustomerService customerService,
            DriverService driverService,
            Scanner scanner
    ) {
        this.restaurantService = restaurantService;
        this.orderService = orderService;
        this.customerService = customerService;
        this.driverService = driverService;
        this.scanner = scanner;
    }

    public void initMenuItems() {
        menuItems.put(1, "Restaurants utils");
        menuItems.put(2, "Drivers utils");
        menuItems.put(3, "Customers utils");
        menuItems.put(4, "Orders utils");
        menuItems.put(5, "Exit");
    }

    private void showRestaurantsUtils() {
        System.out.printf("\n\n");
        System.out.println("Restaurants utils");
        System.out.println("1. List all restaurants");
        System.out.println("2. Add restaurant");
        System.out.println("3. Remove restaurant");
        System.out.println("4. Update restaurant");
        System.out.println("5. Show restaurant details");
        System.out.println("6. Exit");
    }

    private void listAllRestaurants() {
        this.restaurantService.listAllRestaurants();
    }

    private void addRestaurant() {
        Restaurant restaurant = RestaurantFactory.createRestaurant(scanner);
        this.restaurantService.addRestaurant(restaurant);
        RestaurantRepository.addRestaurant(restaurant);
    }

    private void removeRestaurant() {
        listAllRestaurants();
        System.out.println("Enter restaurant id:");
        Integer id = scanner.nextInt();
        Restaurant restaurant = this.restaurantService.getRestaurantById(id);
        this.restaurantService.removeRestaurant(restaurant);
        RestaurantRepository.deleteRestaurant(id);
    }

    private void updateRestaurant() {
        listAllRestaurants();
        System.out.println("Enter restaurant id:");
        Integer id = scanner.nextInt();
        Restaurant restaurant = this.restaurantService.getRestaurantById(id);
        Restaurant updatedRestaurant = RestaurantFactory.createRestaurant(scanner);
        restaurant.updateRestaurant(updatedRestaurant);
        RestaurantRepository.updateRestaurant(restaurant);
    }

    private void showRestaurantDetails() {
        listAllRestaurants();
        System.out.println("Enter restaurant id:");
        Integer id = scanner.nextInt();
        Restaurant restaurant = this.restaurantService.getRestaurantById(id);
        restaurant.showRestaurantDetails();
    }

    private boolean restaurantMain() {
        showRestaurantsUtils();
        System.out.print("Enter option: ");
        Integer option = scanner.nextInt();
        switch (option) {
            case 1:
                listAllRestaurants();
                break;
            case 2:
                addRestaurant();
                break;
            case 3:
                removeRestaurant();
                break;
            case 4:
                updateRestaurant();
                break;
            case 5:
                showRestaurantDetails();
                break;
            case 6:
                return false;
            default:
                System.out.println("Invalid option");
        }
        return true;
    }

    private void restaurantMainLoop() {
        while (restaurantMain()) ;
    }

    private void showDriversUtils() {
        System.out.printf("\n\n");
        System.out.println("Drivers utils");
        System.out.println("1. List all drivers");
        System.out.println("2. Add driver");
        System.out.println("3. Remove driver");
        System.out.println("4. Update driver");
        System.out.println("5. Show driver details");
        System.out.println("6. Exit");
    }

    private void listAllDrivers() {
        this.driverService.listAllDrivers();
    }

    private void addDriver() {
        Driver driver = DriverFactory.createDriver(scanner);
        this.driverService.addDriver(driver);
        DriverRepository.addDriver(driver);
    }

    private void removeDriver() {
        listAllDrivers();
        System.out.println("Enter driver id:");
        Integer id = scanner.nextInt();
        Driver driver = this.driverService.getDriverById(id);
        this.driverService.removeDriver(driver);
        DriverRepository.deleteDriver(id);
    }

    private void updateDriver() {
        listAllDrivers();
        System.out.println("Enter driver id:");
        Integer id = scanner.nextInt();
        Driver driver = this.driverService.getDriverById(id);
        Driver updatedDriver = DriverFactory.createDriver(scanner);
        driver.updateDriver(updatedDriver);
        DriverRepository.updateDriver(driver);
    }

    private void showDriverDetails() {
        listAllDrivers();
        System.out.println("Enter driver id:");
        Integer id = scanner.nextInt();
        Driver driver = this.driverService.getDriverById(id);
        driver.showDriverDetails();
    }

    private boolean driverMain() {
        showDriversUtils();
        System.out.print("Enter option: ");
        Integer option = scanner.nextInt();
        switch (option) {
            case 1:
                listAllDrivers();
                break;
            case 2:
                addDriver();
                break;
            case 3:
                removeDriver();
                break;
            case 4:
                updateDriver();
                break;
            case 5:
                showDriverDetails();
                break;
            case 6:
                return false;
            default:
                System.out.println("Invalid option");
        }
        return true;
    }

    private void driverMainLoop() {
        while (driverMain()) ;
    }

    private void showCustomersUtils() {
        System.out.printf("\n\n");
        System.out.println("Customers utils");
        System.out.println("1. List all customers");
        System.out.println("2. Add customer");
        System.out.println("3. Remove customer");
        System.out.println("4. Update customer");
        System.out.println("5. Show customer details");
        System.out.println("6. Exit");
    }

    private void listAllCustomers() {
        this.customerService.listAllCustomers();
    }

    private void addCustomer() {
        Customer customer = CustomerFactory.createCustomer(scanner);
        this.customerService.addCustomer(customer);
        CustomerRepository.addCustomer(customer);
    }

    private void removeCustomer() {
        listAllCustomers();
        System.out.println("Enter customer id:");
        Integer id = scanner.nextInt();
        Customer customer = this.customerService.getCustomerById(id);
        this.customerService.removeCustomer(customer);
        CustomerRepository.deleteCustomer(id);
    }

    private void updateCustomer() {
        listAllCustomers();
        System.out.println("Enter customer id:");
        Integer id = scanner.nextInt();
        Customer customer = this.customerService.getCustomerById(id);
        Customer updatedCustomer = CustomerFactory.createCustomer(scanner);
        customer.updateCustomer(updatedCustomer);
        CustomerRepository.updateCustomer(customer);
    }

    private void showCustomerDetails() {
        listAllCustomers();
        System.out.println("Enter customer id:");
        Integer id = scanner.nextInt();
        Customer customer = this.customerService.getCustomerById(id);
        customer.showCustomerDetails();
    }

    private boolean customerMain() {
        showCustomersUtils();
        System.out.print("Enter option: ");
        Integer option = scanner.nextInt();
        switch (option) {
            case 1:
                listAllCustomers();
                break;
            case 2:
                addCustomer();
                break;
            case 3:
                removeCustomer();
                break;
            case 4:
                updateCustomer();
                break;
            case 5:
                showCustomerDetails();
                break;
            case 6:
                return false;
            default:
                System.out.println("Invalid option");
        }
        return true;
    }

    private void customerMainLoop() {
        while (customerMain()) ;
    }

    private void showOrdersUtils() {
        System.out.printf("\n\n");
        System.out.println("Orders utils");
        System.out.println("1. List all orders");
        System.out.println("2. Show order details");
        System.out.println("3. Exit");
    }

    private void listAllOrders() {
        this.orderService.listAllOrders();
    }

    private void showOrderDetails() {
        listAllOrders();
        System.out.println("Enter order id:");
        Integer id = scanner.nextInt();
        Order order = this.orderService.getOrderById(id);
        order.showOrderDetails();
    }

    private boolean orderMain() {
        showOrdersUtils();
        System.out.print("Enter option: ");
        Integer option = scanner.nextInt();
        switch (option) {
            case 1:
                listAllOrders();
                break;
            case 2:
                showOrderDetails();
                break;
            case 3:
                return false;
            default:
                System.out.println("Invalid option");
        }
        return true;
    }

    private void orderMainLoop() {
        while (orderMain()) ;
    }

    private void showMenuOptions() {
        for (int i = 1; i <= menuItems.size(); i++) {
            System.out.printf("%d. %s\n", i, menuItems.get(i));
        }
    }

    private boolean main() {
        System.out.printf("\n\n");
        System.out.println("Admin menu");
        showMenuOptions();

        System.out.print("Enter option: ");
        Integer option = scanner.nextInt();
        switch (option) {
            case 1:
                restaurantMainLoop();
                break;
            case 2:
                driverMainLoop();
                break;
            case 3:
                customerMainLoop();
                break;
            case 4:
                orderMainLoop();
                break;
            case 5:
                // exit
                return false;
            default:
                System.out.println("Invalid option");
                break;
        }
        return true;
    }

    public void mainLoop() {
        while (main());
    }
}