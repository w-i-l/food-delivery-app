import Models.Driver.Driver;
import Models.Driver.DriverFactory;
import Models.Order.Order;
import Models.Order.OrderFactory;
import Models.Product.ProductFactory;
import Models.Product.ProductItem;
import Models.Product.SpecialProduct;
import Models.Restaurant.Restaurant;
import Models.Restaurant.RestaurantFactory;
import Services.MenuService;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuService menuService = new MenuService();
        menuService.initMenuItems();
        menuService.mainLoop(scanner);
    }
}