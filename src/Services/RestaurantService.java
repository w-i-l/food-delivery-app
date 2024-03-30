package Services;
import Models.Restaurant;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;


public class RestaurantService {
   private Dictionary<Integer, Restaurant> restaurants;

    public RestaurantService(List<Restaurant> restaurants) {
        this.restaurants = new Hashtable<Integer, Restaurant>();
        for (Restaurant restaurant : restaurants) {
            this.restaurants.put(restaurant.getId(), restaurant);
        }
    }

    public RestaurantService() {
        this.restaurants = new Hashtable<Integer, Restaurant>();
    }

    public void addRestaurant(Restaurant restaurant) {
        this.restaurants.put(restaurant.getId(), restaurant);
    }

    public void removeRestaurant(Integer id) {
        this.restaurants.remove(id);
    }

    public Restaurant getRestaurantById(Integer id) {
        return this.restaurants.get(id);
    }

    public Dictionary<Integer, Restaurant> getRestaurants() {
        return this.restaurants;
    }

    public void listAllRestaurants() {
        Iterator<Restaurant> restaurantIterator = this.restaurants.elements().asIterator();
        while (restaurantIterator.hasNext()) {
            Restaurant restaurant = restaurantIterator.next();
            restaurant.showRestaurantDetails();
        }
    }

    public void viewMenuFromRestaurant(Integer id) {
        Restaurant restaurant = this.restaurants.get(id);
        restaurant.showMenu();
    }
}
