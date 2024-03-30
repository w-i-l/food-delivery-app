import MockedData.DriverGenerator;
import MockedData.RestaurantGenerator;
import Models.Driver.Driver;
import Models.Order.Order;
import Models.Restaurant;
import Services.RestaurantService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Restaurant> restaurants = RestaurantGenerator.generateRestaurants();
        RestaurantService restaurantService = new RestaurantService(restaurants);
        restaurantService.listAllRestaurants();
        restaurantService.viewMenuFromRestaurant(1);

        List<Order> orders = new ArrayList<Order>();
    }
}