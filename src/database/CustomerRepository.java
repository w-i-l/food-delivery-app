package database;

import models.address.Address;
import models.address.AddressFactory;
import models.customer.Customer;
import models.customer.CustomerFactory;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class CustomerRepository {
    private static Connection connection = null;

    public static void initConnection() {
        connection = Connector.getConnection();
    }

    public static Set<Customer> getCustomers() {
        Set<Customer> customers = new HashSet<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("""
                SELECT c.id id, c.name name, a.name address_name, a.latitude latitude, a.longitude longitude
                FROM Customer c
                JOIN Address a ON c.address_id = a.id
            """);

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String addressName = resultSet.getString("address_name");
                Double latitude = resultSet.getDouble("latitude");
                Double longitude = resultSet.getDouble("longitude");

                Address address = AddressFactory.createAddress(id, addressName, latitude, longitude);
                Customer customer = CustomerFactory.createCustomer(id, name, address);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public static void addCustomer(Customer customer) {
        try {
            // Insert the address first if it doesn't exist
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Address (id, name, latitude, longitude) VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, customer.getAddress().getId());
            preparedStatement.setString(2, customer.getAddress().getName());
            preparedStatement.setDouble(3, customer.getAddress().getLatitude());
            preparedStatement.setDouble(4, customer.getAddress().getLongitude());

            preparedStatement.executeUpdate();

            // Insert the customer with the generated address ID
            preparedStatement = connection.prepareStatement("INSERT INTO Customer (id, name, address_id) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setInt(3, customer.getAddress().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCustomer(Customer customer) {
        try {
            // Update customer information
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Customer SET name = ? WHERE id = ?");
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setInt(2, customer.getId());

            preparedStatement.executeUpdate();

            // Update the address information
            preparedStatement = connection.prepareStatement(
                    "UPDATE Address SET name = ?, latitude = ?, longitude = ? WHERE id = ?");
            preparedStatement.setString(1, customer.getAddress().getName());
            preparedStatement.setDouble(2, customer.getAddress().getLatitude());
            preparedStatement.setDouble(3, customer.getAddress().getLongitude());
            preparedStatement.setInt(4, customer.getAddress().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCustomer(Customer customer) {
        try {
            // Delete the customer
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Customer WHERE id = ?");
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.executeUpdate();

            // Optionally, delete the address if no other customer is using it
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM Address WHERE id = ?");
            preparedStatement.setInt(1, customer.getAddress().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
