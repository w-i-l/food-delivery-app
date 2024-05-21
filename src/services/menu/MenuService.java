package services.menu;

import services.CustomerService;
import services.DriverService;
import services.OrderService;
import services.RestaurantService;

import java.util.Scanner;

public class MenuService {
    static MenuService instance = null;

    private UserMenuService userMenuService;
    private AdminMenuService adminMenuService;
    private Scanner scanner;
    private final String ADMIN_PASSWORD = "admin";

    static public MenuService getInstance() {
        return instance;
    }

    static public MenuService init(
            RestaurantService restaurantService,
            OrderService orderService,
            CustomerService customerService,
            DriverService driverService,
            Scanner scanner
    ) {
        if (instance == null) {
            instance = new MenuService(
                    restaurantService,
                    orderService,
                    customerService,
                    driverService,
                    scanner
            );
        }
        return instance;
    }

    private MenuService(
            RestaurantService restaurantService,
            OrderService orderService,
            CustomerService customerService,
            DriverService driverService,
            Scanner scanner
    ) {
        this.scanner = scanner;

        userMenuService = UserMenuService.init(
                restaurantService,
                orderService,
                customerService,
                driverService,
                scanner
        );

        adminMenuService = AdminMenuService.init(
                restaurantService,
                orderService,
                customerService,
                driverService,
                scanner
        );
    }

    public void initMenuItems() {
        userMenuService.initMenuItems();
        adminMenuService.initMenuItems();
    }

    private void main () {
        System.out.printf("\n\n");
        System.out.println("Welcome to the Food Delivery App!");
        System.out.println("Please select your role:");
        System.out.println("1. User");
        System.out.println("2. Admin");
        System.out.println("3. Exit");

        System.out.print("Enter your option: ");
        Integer option = scanner.nextInt();

        switch (option) {
            case 1:
                userMenuService.mainLoop();
                break;
            case 2:
                String password;
                System.out.print("Enter admin password: ");
                password = scanner.next();
                if (!password.equals(ADMIN_PASSWORD)) {
                    System.out.println("Invalid password");
                    break;
                }
                adminMenuService.mainLoop();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    public void mainLoop() {
        while (true) {main();}
    }
}
