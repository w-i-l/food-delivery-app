package Services;

import MockedData.CustomerGenerator;
import MockedData.DriverGenerator;
import MockedData.RestaurantGenerator;
import Models.Customer.Customer;
import Models.Driver.Driver;
import Models.Order.Order;
import Models.Order.OrderFactory;
import Models.Order.OrderStatus;
import Models.Product.ProductInterface;
import Models.Restaurant.Restaurant;

import java.util.*;

public class MenuService {
    private Dictionary<Integer, String> menuItems = new Hashtable<Integer, String>();
    private RestaurantService restaurantService;
    private OrderService orderService;
    private CustomerService customerService;
    private DriverService driverService;
    private Scanner scanner;

    public MenuService() {
        this.restaurantService = new RestaurantService(RestaurantGenerator.generateRestaurants());
        this.orderService = new OrderService();
        this.customerService = new CustomerService(CustomerGenerator.generateCustomers());
        this.driverService = new DriverService(DriverGenerator.generateDrivers());
        this.scanner = new Scanner(System.in);
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
        System.out.println("Menu:");

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

    public void mainLoop(Scanner scanner) {
        while (main(scanner));
    }

    private boolean main(Scanner scanner) {
        try {
            Runtime.getRuntime().exec("cls");
        } catch (Exception e) {
            System.out.println("\n");
        }
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

    public void viewMenuFromRestaurant() {
        listAllRestaurants();
        System.out.println("Enter restaurant id: ");
        Integer restaurantId = scanner.nextInt();
        restaurantService.viewMenuFromRestaurant(restaurantId);
    }

    private void viewMenuFromRestaurant(Integer restaurantId) {
        restaurantService.viewMenuFromRestaurant(restaurantId);
    }

    public void createOrder() {

        Double price = 0.0;
        Dictionary<ProductInterface, Integer> products = new Hashtable<ProductInterface, Integer>();

        listAllOrders();
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
        Order order = OrderFactory.createOrder(customer, restaurant, driver, price, products);
        orderService.addOrder(order);
    }

    public void listAllOrders() {
        orderService.listAllOrders();
    }

    public void seeOrderDetails() {
        listAllOrders();
        if (orderService.getOrders().size() == 0) {
            return;
        }
        System.out.println("Enter order id: ");
        Integer orderId = scanner.nextInt();
        orderService.getOrderById(orderId).showOrderDetails();
    }

    public void placeOrder() {
        listAllOrders();
        if(orderService.getOrders().size() == 0) {
            return;
        }
        System.out.println("Enter order id: ");
        Integer orderId = scanner.nextInt();
        Order order = orderService.getOrderById(orderId);
        order.setStatus(OrderStatus.ACCEPTED);
    }

    public void seeOrderStatus() {
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
