package MockedData;
import Models.Address;
import Models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantGenerator {
    public static List<Restaurant> generateRestaurants() {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        Restaurant aux;
        aux = new Restaurant(
                "McDonald's",
                new Address("Street 1, Number 32"),
                ProductGenerator.generateProducts()
        );
        restaurants.add(aux);
        aux = new Restaurant(
                "Pizza Hut",
                new Address("Street 2, Number 12"),
                ProductGenerator.generateProducts()
        );
        restaurants.add(aux);
        aux = new Restaurant(
                "Domino's",
                new Address("Street 3, Number 22"),
                ProductGenerator.generateProducts()
        );
        restaurants.add(aux);
        return restaurants;
    }
}
