package services.menu;

import models.customer.Customer;
import models.driver.Driver;
import models.order.Order;
import models.order.OrderFactory;
import models.order.OrderStatus;
import models.product.ProductInterface;
import models.restaurant.Restaurant;
import services.CustomerService;
import services.DriverService;
import services.OrderService;
import services.RestaurantService;

import java.util.*;
public class UserMenuService {

    static UserMenuService instance = null;

    private Dictionary<Integer, String> menuItems = new Hashtable<Integer, String>();
    private RestaurantService restaurantService;
    private OrderService orderService;
    private CustomerService customerService;
    private DriverService driverService;
    private Scanner scanner;

    static public UserMenuService init(
            RestaurantService restaurantService,
            OrderService orderService,
            CustomerService customerService,
            DriverService driverService,
            Scanner scanner
    ) {
        if (instance == null) {
            instance = new UserMenuService(
                    restaurantService,
                    orderService,
                    customerService,
                    driverService,
                    scanner
            );
        }
        return instance;
    }

    private UserMenuService(
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
        menuItems.put(1, "List all restaurants");
        menuItems.put(2, "List menu from restaurant");
        menuItems.put(3, "Create order");
        menuItems.put(4, "List all orders");
        menuItems.put(5, "See order details");
        menuItems.put(6, "Place order");
        menuItems.put(7, "See order status");
        menuItems.put(8, "Exit");
    }

    private void showMenuOptions() {
        System.out.println("User menu:");

        ArrayList<Integer> keys = new ArrayList<Integer>();
        Iterator<Integer> keyIterator = menuItems.keys().asIterator();
        while (keyIterator.hasNext()) {
            keys.add(keyIterator.next());
        }
        keys.sort(null);

        for (Integer key : keys) {
            System.out.println(key + ". " + menuItems.get(key));
        }
    }

    public void mainLoop() {
        while (main(scanner));
    }

    private boolean main(Scanner scanner) {
        System.out.printf("\n\n");
        showMenuOptions();

        System.out.print("Enter option: ");
        Integer option = scanner.nextInt();
        switch (option) {
            case 1:
                listAllRestaurants();
                break;
            case 2:
                viewMenuFromRestaurant();
                break;
            case 3:
                createOrder();
                break;
            case 4:
                listAllOrders();
                break;
            case 5:
                seeOrderDetails();
                break;
            case 6:
                placeOrder();
                break;
            case 7:
                seeOrderStatus();
                break;
            case 8:
                // exit
                return false;
            default:
                System.out.println("Invalid option");
                break;
        }
        return true;
    }

    private void listAllRestaurants() {
        restaurantService.listAllRestaurants();
    }

    private void viewMenuFromRestaurant() {
        listAllRestaurants();
        System.out.println("Enter restaurant id: ");
        Integer restaurantId = scanner.nextInt();
        restaurantService.viewMenuFromRestaurant(restaurantId);
    }

    private void viewMenuFromRestaurant(Integer restaurantId) {
        restaurantService.viewMenuFromRestaurant(restaurantId);
    }

    private void createOrder() {

        Double price = 0.0;
        Dictionary<ProductInterface, Integer> products = new Hashtable<ProductInterface, Integer>();

        listAllRestaurants();
        System.out.println("Choose a restaurant: ");
        Integer restaurantId = scanner.nextInt();

        restaurantService.viewMenuFromRestaurant(restaurantId);
        while (true) {
            System.out.println("Enter product id: ");
            Integer productId = scanner.nextInt();
            System.out.println("Enter quantity: ");
            Integer quantity = scanner.nextInt();

            ProductInterface product = restaurantService.getProductFromRestaurant(restaurantId, productId);
            products.put(product, quantity);
            price += product.getPrice() * quantity;

            System.out.println("Add another product? (y/n)");
            String answer = scanner.next();
            if (answer.equals("n")) {
                break;
            }
        }

        customerService.listAllCustomers();
        System.out.println("Choose a customer: ");
        Integer customerId = scanner.nextInt();

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Customer customer = customerService.getCustomerById(customerId);
        driverService.listAllDriversWithETA(restaurant.getAddress(), customer.getAddress());
        System.out.println("Choose a driver: ");
        Integer driverId = scanner.nextInt();
        Driver driver = driverService.getDriverById(driverId);

        Double discount = Math.random() * 0.5;
        price = price - price * discount;
        Order order = OrderFactory.createOrder(customer, restaurant, driver, OrderStatus.PENDING, products);
        orderService.addOrder(order);
    }

    private void listAllOrders() {
        orderService.listAllOrders();
    }

    private void seeOrderDetails() {
        listAllOrders();
        if (orderService.getOrders().size() == 0) {
            return;
        }
        System.out.println("Enter order id: ");
        Integer orderId = scanner.nextInt();
        orderService.getOrderById(orderId).showOrderDetails();
    }

    private void placeOrder() {
        listAllOrders();
        if(orderService.getOrders().size() == 0) {
            return;
        }
        System.out.println("Enter order id: ");
        Integer orderId = scanner.nextInt();
        Order order = orderService.getOrderById(orderId);
        order.setStatus(OrderStatus.ACCEPTED);
    }

    private void seeOrderStatus() {
        listAllOrders();
        if(orderService.getOrders().size() == 0) {
            return;
        }

        System.out.println("Enter order id: ");
        Integer orderId = scanner.nextInt();
        Order order = orderService.getOrderById(orderId);
        System.out.printf("Order %d is %s, ETA: %f\n", orderId, order.getStatus(), order.getEstimatedDeliveryTime());
    }
}
