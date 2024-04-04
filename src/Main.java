import MockedData.CustomerGenerator;
import MockedData.DriverGenerator;
import MockedData.RestaurantGenerator;
import Services.*;
import Services.Menu.AdminMenuService;
import Services.Menu.MenuService;
import Services.Menu.UserMenuService;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RestaurantService restaurantService = new RestaurantService(RestaurantGenerator.generateRestaurants());
        DriverService driverService = new DriverService(DriverGenerator.generateDrivers());
        CustomerService customerService = new CustomerService(CustomerGenerator.generateCustomers());
        OrderService orderService = new OrderService();

//        UserMenuService userMenuService = UserMenuService.init(
//                restaurantService,
//                orderService,
//                customerService,
//                driverService,
//                scanner
//        );
//        userMenuService.initMenuItems();
//        userMenuService.mainLoop(scanner);

//        AdminMenuService adminMenuService = AdminMenuService.init(
//                restaurantService,
//                orderService,
//                customerService,
//                driverService,
//                scanner
//        );
//        adminMenuService.initMenuItems();
//        adminMenuService.mainLoop();

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