package database;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import database.Connector;
import models.driver.Driver;
import models.driver.DriverFactory;
import models.driver.DriverType;

public class DriverRepository {
    private static Connection connection = null;

    public static void initConnection() {
        connection = Connector.getConnection();
    }

    public static Set<Driver> getDrivers() {

        Set<Driver> drivers = new HashSet<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Driver");

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                DriverType driverType = DriverType.fromString(type);
                Integer rating = resultSet.getInt("rating");

                Driver driver = DriverFactory.createDriver(id, name, driverType, rating);
                drivers.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return drivers;
    }

    public static void addDriver(Driver driver) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Driver VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, driver.getId());
            preparedStatement.setString(2, driver.getName());
            preparedStatement.setString(3, driver.getType().toString());
            preparedStatement.setInt(4, driver.getRating());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateDriver(Driver driver) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Driver SET name = ?, type = ?, rating = ? WHERE id = ?");
            preparedStatement.setString(1, driver.getName());
            preparedStatement.setString(2, driver.getType().toString());
            preparedStatement.setInt(3, driver.getRating());
            preparedStatement.setInt(4, driver.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDriver(Driver driver) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Driver WHERE id = ?");
            preparedStatement.setInt(1, driver.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
