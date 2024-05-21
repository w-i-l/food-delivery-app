package database;

import models.address.Address;
import models.product.ProductInterface;
import models.restaurant.Restaurant;
import models.restaurant.RestaurantFactory;
import tests.RestaurantDatabaseTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository {

    private static Connection connection = null;
    public static void initConnection() {
        connection = Connector.getConnection();
    }

    public static List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("""
            SELECT r.id, r.name, a.id, a.name, a.latitude, a.longitude 
            FROM Restaurant r, Address a
            WHERE r.address_id = a.id
            """);

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Address address = new Address(
                        resultSet.getInt("a.id"),
                        resultSet.getString("a.name"),
                        resultSet.getDouble("a.latitude"),
                        resultSet.getDouble("a.longitude")
                );
                List<ProductInterface> products = ProductRepository.getProductsForRestaurant(id);

                Restaurant restaurant = RestaurantFactory.createRestaurant(id, name, address, products);
                restaurants.add(restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurants;
    }

    public static void addRestaurant(Restaurant restaurant) {
        try {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("""
                INSERT INTO Address (id, name, latitude, longitude)
                VALUES (?, ?, ?, ?)
                """);
                preparedStatement.setInt(1, restaurant.getAddress().getId());
                preparedStatement.setString(2, restaurant.getAddress().getName());
                preparedStatement.setDouble(3, restaurant.getAddress().getLatitude());
                preparedStatement.setDouble(4, restaurant.getAddress().getLongitude());

                preparedStatement.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                // Address already exists
                System.out.println("Address already exists");
            }

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
                RestaurantRepository.addProductToRestaurant(product, restaurant);
            }
        } catch(SQLIntegrityConstraintViolationException e) {
            System.out.println("Restaurant already exists");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteRestaurant(Restaurant restaurant) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            DELETE FROM Restaurant
            WHERE id = ?
            """);
            preparedStatement.setInt(1, restaurant.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateRestaurant(Restaurant restaurant) {
        try {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("""
            
                INSERT INTO Address (id, name, latitude, longitude)
                VALUES (?, ?, ?, ?)
                """);
                preparedStatement.setInt(1, restaurant.getAddress().getId());
                preparedStatement.setString(2, restaurant.getAddress().getName());
                preparedStatement.setDouble(3, restaurant.getAddress().getLatitude());
                preparedStatement.setDouble(4, restaurant.getAddress().getLongitude());

                preparedStatement.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                // Address already exists
                System.out.println("Address already exists");
            }

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
                RestaurantRepository.addProductToRestaurant(product, restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addProductToRestaurant(ProductInterface product, Restaurant restaurant) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            INSERT INTO RestaurantProducts (restaurant_id, product_id)
            VALUES (?, ?)
            """);
            preparedStatement.setInt(1, restaurant.getId());
            preparedStatement.setInt(2, product.getId());

            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            // Product already exists
            System.out.println("Product already exists");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProductFromRestaurant(ProductInterface product, Restaurant restaurant) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            DELETE FROM RestaurantProducts
            WHERE restaurant_id = ? AND product_id = ?
            """);
            preparedStatement.setInt(1, restaurant.getId());
            preparedStatement.setInt(2, product.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
