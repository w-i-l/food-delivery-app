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
                SELECT id id, name, address_id
                FROM Customer c
            """);

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Integer addressId = resultSet.getInt("address_id");
                Address address = AddressRepository.getAddressById(addressId);
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
            AddressRepository.addAddress(customer.getAddress());

            // Insert the customer with the generated address ID
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Customer (id, name, address_id) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setInt(3, customer.getAddress().getId());

            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Customer already exists");
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
            AddressRepository.updateAddress(customer.getAddress());
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
            AddressRepository.deleteAddress(customer.getAddress().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Customer getCustomerById(Integer id) {
        Customer customer = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                SELECT id, name, address_id
                FROM Customer c
                WHERE c.id = ?
            """);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Integer addressId = resultSet.getInt("address_id");
                Address address = AddressRepository.getAddressById(addressId);
                customer = CustomerFactory.createCustomer(id, name, address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }
}
