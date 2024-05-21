package database;

import models.address.Address;

import java.sql.*;

public class AddressRepository {
    private static Connection connection = null;

    public static void initConnection() {
        connection = Connector.getConnection();
    }

    public static Address addAddress(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                INSERT INTO Address (id, name, latitude, longitude)
                VALUES (?, ?, ?, ?)
                """);
            preparedStatement.setInt(1, address.getId());
            preparedStatement.setString(2, address.getName());
            preparedStatement.setDouble(3, address.getLatitude());
            preparedStatement.setDouble(4, address.getLongitude());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Address already exists");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return address;
    }

    public static void deleteAddress(Integer addressId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                DELETE FROM Address
                WHERE id = ?
                """);
            preparedStatement.setInt(1, addressId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateAddress(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                
                    UPDATE Address
                SET name = ?, latitude = ?, longitude = ?
                WHERE id = ?
                """);
            preparedStatement.setString(1, address.getName());
            preparedStatement.setDouble(2, address.getLatitude());
            preparedStatement.setDouble(3, address.getLongitude());
            preparedStatement.setInt(4, address.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Address getAddressById(Integer addressId) {
        Address address = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                SELECT name, latitude, longitude
                FROM Address
                WHERE id = ?
                """);
            preparedStatement.setInt(1, addressId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Double latitude = resultSet.getDouble("latitude");
                Double longitude = resultSet.getDouble("longitude");

                address = new Address(addressId, name, latitude, longitude);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return address;
    }
}
