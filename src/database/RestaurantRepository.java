package database;

import models.address.Address;
import models.product.ProductInterface;
import models.restaurant.Restaurant;
import models.restaurant.RestaurantFactory;
import tests.RestaurantDatabaseTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository extends BaseRepository {

    private static Connection connection = null;
    public static void initConnection() {
        printSuccess("RestaurantRepository: Connection initialized");
        connection = Connector.getConnection();
    }

    public static List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("""
            SELECT id, name, address_id
            FROM Restaurant r
            """);

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Integer addressId = resultSet.getInt("address_id");
                Address address = AddressRepository.getAddressById(addressId);
                List<ProductInterface> products = ProductRepository.getProductsForRestaurant(id);

                Restaurant restaurant = RestaurantFactory.createRestaurant(id, name, address, products);
                restaurants.add(restaurant);
            }
        } catch (SQLException e) {
            printException(e);
        }

        return restaurants;
    }

    public static void addRestaurant(Restaurant restaurant) {
        try {
            AddressRepository.addAddress(restaurant.getAddress());

            PreparedStatement preparedStatement = connection.prepareStatement("""
            INSERT INTO Restaurant (id, name, address_id)
            VALUES (?, ?, ?)
            """);
            preparedStatement.setInt(1, restaurant.getId());
            preparedStatement.setString(2, restaurant.getName());
            preparedStatement.setInt(3, restaurant.getAddress().getId());

            preparedStatement.executeUpdate();

            for (ProductInterface product : restaurant.getProducts()) {
                ProductRepository.addProduct(product);
                RestaurantRepository.addProductToRestaurant(product.getId(), restaurant.getId());
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                String warningMessage = "Restaurant (id:" + restaurant.getId() + ") already exists";
                printWarning(warningMessage);
            } else {
                printException(e);
            }
        }
    }

    public static void deleteRestaurant(Integer restaurantId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            DELETE FROM Restaurant
            WHERE id = ?
            """);
            preparedStatement.setInt(1, restaurantId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printException(e);
        }
    }

    public static void updateRestaurant(Restaurant restaurant) {
        try {
            AddressRepository.addAddress(restaurant.getAddress());

            PreparedStatement preparedStatement = connection.prepareStatement("""
            UPDATE Restaurant
            SET name = ?, address_id = ?
            WHERE id = ?
            """);
            preparedStatement.setString(1, restaurant.getName());
            preparedStatement.setInt(2, restaurant.getAddress().getId());
            preparedStatement.setInt(3, restaurant.getId());

            preparedStatement.executeUpdate();

            for (ProductInterface product : restaurant.getProducts()) {
                ProductRepository.addProduct(product);
                RestaurantRepository.addProductToRestaurant(product.getId(), restaurant.getId());
            }
        } catch (SQLException e) {
            printException(e);
        }
    }

    public static void addProductToRestaurant(Integer productId, Integer restaurantId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            INSERT INTO RestaurantProducts (restaurant_id, product_id)
            VALUES (?, ?)
            """);
            preparedStatement.setInt(1, restaurantId);
            preparedStatement.setInt(2, productId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                String warningMessage = "Product (id:" + productId + ") already exists in restaurant (id:" + restaurantId + ")";
                printWarning(warningMessage);
            } else {
                printException(e);
            }
        }
    }

    public static void deleteProductFromRestaurant(Integer productId, Integer restaurantId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            DELETE FROM RestaurantProducts
            WHERE restaurant_id = ? AND product_id = ?
            """);
            preparedStatement.setInt(1, restaurantId);
            preparedStatement.setInt(2, productId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printException(e);
        }
    }

    public static Restaurant getRestaurantById(Integer id) {
        Restaurant restaurant = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            SELECT id, name, address_id
            FROM Restaurant
            WHERE id = ?
            """);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Integer addressId = resultSet.getInt("address_id");
                Address address = AddressRepository.getAddressById(addressId);
                List<ProductInterface> products = ProductRepository.getProductsForRestaurant(id);

                restaurant = RestaurantFactory.createRestaurant(id, name, address, products);
            }
        } catch (SQLException e) {
            printException(e);
        }

        return restaurant;
    }
}
