package tests;

import database.ProductRepository;
import database.RestaurantRepository;
import models.address.Address;
import models.product.ProductInterface;
import models.product.ProductItem;
import models.product.SpecialProduct;
import models.restaurant.Restaurant;

import java.util.Date;
import java.util.List;

public class RestaurantDatabaseTest {
    public static void test() {
        System.out.println("\n  ------ Restaurant Test ------");
        RestaurantRepository.initConnection();
        ProductRepository.initConnection();
        getAllRestaurants();
        addRestaurant();
        updateRestaurant();
        addProductToRestaurant();
        deleteProductFromRestaurant();
        deleteRestaurant();
        System.out.println("[Restaurant Test PASSED] All tests passed successfully\n");
    }

    private static void getAllRestaurants() {
        System.out.println("Getting all restaurants...");
        RestaurantRepository.getAllRestaurants();
    }

    private static void addRestaurant() {
        System.out.println("Adding a restaurant...");
        Address address = new Address(4, "Mocked Address", 5.0, 5.0);
        List<ProductInterface> products = List.of(
                new ProductItem(17, "Mocked Product 1", 5.0),
                new SpecialProduct(18, "Mocked Special Product 1", 10.0, new Date())
        );
        Restaurant restaurant = new Restaurant(4, "Mocked Restaurant", address, products);
        RestaurantRepository.addRestaurant(restaurant);
    }

    private static void deleteRestaurant() {
        System.out.println("Deleting a restaurant...");
        Address address = new Address(4, "Mocked Address", 5.0, 5.0);
        List<ProductInterface> products = List.of(
                new ProductItem(17, "Mocked Product 1", 5.0),
                new SpecialProduct(18, "Mocked Special Product 1", 10.0, new Date())
        );
        Restaurant restaurant = new Restaurant(4, "Mocked Restaurant", address, products);
        RestaurantRepository.deleteRestaurant(restaurant.getId());
    }

    private static void updateRestaurant() {
        System.out.println("Updating a restaurant...");
        Address address = new Address(4, "Mocked Address", 5.0, 5.0);
        List<ProductInterface> products = List.of(
                new ProductItem(17, "Mocked Product 1", 5.0),
                new SpecialProduct(18, "Mocked Special Product 1", 10.0, new Date())
        );
        Restaurant restaurant = new Restaurant(4, "Mocked Restaurant", address, products);
        restaurant.setName("Updated Name");
        Address updatedAddress = new Address(4, "Updated Address", 3.0, 3.0);
        restaurant.setAddress(updatedAddress);
        List<ProductInterface> updatedProducts = List.of(
                new ProductItem(19, "Updated Product 1", 5.0),
                new SpecialProduct(20, "Updated Special Product 1", 10.0, new Date())
        );
        restaurant.setProducts(updatedProducts);
        RestaurantRepository.updateRestaurant(restaurant);
    }

    private static void addProductToRestaurant() {
        System.out.println("Adding a product to restaurant...");
        Restaurant restaurant = new Restaurant(1, "Mocked Restaurant", null, null);
        ProductInterface product = new ProductItem(5, "Mocked Product", 5.0);
        RestaurantRepository.addProductToRestaurant(product.getId(), restaurant.getId());
    }

    private static void deleteProductFromRestaurant() {
        System.out.println("Deleting a product from restaurant...");
        Restaurant restaurant = new Restaurant(1, "Mocked Restaurant", null, null);
        ProductInterface product = new ProductItem(5, "Mocked Product", 5.0);
        RestaurantRepository.deleteProductFromRestaurant(product.getId(), restaurant.getId());
    }
}
