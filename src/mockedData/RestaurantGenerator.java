package mockedData;
import models.address.AddressFactory;
import models.restaurant.Restaurant;
import models.restaurant.RestaurantFactory;

import java.util.ArrayList;
import java.util.List;

public class RestaurantGenerator {
    public static List<Restaurant> generateRestaurants() {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        Restaurant aux;
        aux = RestaurantFactory.createRestaurant(
                "McDonald's",
                AddressFactory.createAddress("Street 1, Number 10"),
                ProductGenerator.generateProducts()
        );
        restaurants.add(aux);
        aux = RestaurantFactory.createRestaurant(
                "Burger King",
                AddressFactory.createAddress("Street 2, Number 15"),
                ProductGenerator.generateProducts()
        );
        restaurants.add(aux);
        aux = RestaurantFactory.createRestaurant(
                "KFC",
                AddressFactory.createAddress("Street 3, Number 20"),
                ProductGenerator.generateProducts()
        );
        restaurants.add(aux);
        return restaurants;
    }
}
