import mockedData.CustomerGenerator;
import mockedData.DriverGenerator;
import mockedData.RestaurantGenerator;
import services.*;
import services.menu.MenuService;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RestaurantService restaurantService = new RestaurantService(RestaurantGenerator.generateRestaurants());
        DriverService driverService = new DriverService(DriverGenerator.generateDrivers());
        CustomerService customerService = new CustomerService(CustomerGenerator.generateCustomers());
        OrderService orderService = new OrderService();


        MenuService menuService = MenuService.init(
                restaurantService,
                orderService,
                customerService,
                driverService,
                scanner
        );
        menuService.initMenuItems();
        menuService.mainLoop();
    }
}