package database;

import models.address.Address;
import models.address.AddressFactory;
import models.customer.Customer;
import models.customer.CustomerFactory;
import services.audit.AuditDatabaseAction;
import services.audit.AuditService;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerRepository extends BaseRepository {
    private static Connection connection = null;

    public static void initConnection() {
        printSuccess("CustomerRepository: Connection initialized");
        connection = Connector.getConnection();
    }

    public static List<Customer> getAllCustomers() {
        AuditService.log("Customer", AuditDatabaseAction.READ);

        List<Customer> customers = new ArrayList<>();

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
            printException(e);
        }

        return customers;
    }

    public static void addCustomer(Customer customer) {
        AuditService.log("Customer", AuditDatabaseAction.CREATE);

        try {
            AddressRepository.addAddress(customer.getAddress());

            // Insert the customer with the generated address ID
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Customer (id, name, address_id) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setInt(3, customer.getAddress().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                String warningMessage = "Customer (id:" + customer.getId() + ") already exists";
                printWarning(warningMessage);
            } else {
            printException(e);
            }
        }
    }

    public static void updateCustomer(Customer customer) {
        AuditService.log("Customer", AuditDatabaseAction.UPDATE);

        try {
            AddressRepository.addAddress(customer.getAddress());

            PreparedStatement preparedStatement = connection.prepareStatement("""
            UPDATE Customer 
            SET name = ?, address_id = ? 
            WHERE id = ?
            """);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setInt(2, customer.getAddress().getId());
            preparedStatement.setInt(3, customer.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printException(e);
        }
    }

    public static void deleteCustomer(Integer customerId) {
        AuditService.log("Customer", AuditDatabaseAction.DELETE);

        try {
            // Delete the customer
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Customer WHERE id = ?");
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printException(e);
        }
    }

    public static Customer getCustomerById(Integer id) {
        AuditService.log("Customer", AuditDatabaseAction.READ);

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
            printException(e);
        }

        return customer;
    }
}
