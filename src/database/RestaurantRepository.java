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
            AddressRepository.updateAddress(restaurant.getAddress());

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
            e.printStackTrace();
        }

        return restaurant;
    }
}
