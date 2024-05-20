package tests;

import database.CustomerRepository;
import models.address.Address;
import models.customer.Customer;

import java.util.Set;

public class CustomerDatabaseTest {

    public static void test() {
        CustomerRepository.initConnection();

        try {
            System.out.println("\n  ------ Customer Test ------");
            getCustomers();
            addCustomer();
            updateCustomer();
            deleteCustomer();
            System.out.println("[Customer Test PASSED] All tests passed successfully\n");
        } catch (Exception e) {
            System.out.println("[Customer Test FAILED] An error occurred: " + e.getMessage());
        }
    }

    private static void getCustomers() {
        System.out.println("Getting customers...");
        Set<Customer> customers = CustomerRepository.getCustomers();
    }

    private static void addCustomer() {
        System.out.println("Adding a customer...");
        Address address = new Address(4,"Mocked Address", 5.0, 5.0);
        Customer customer = new Customer(4, "Mocked Customer", address);
        CustomerRepository.addCustomer(customer);
    }

    private static void deleteCustomer() {
        System.out.println("Deleting a customer...");
        Address address = new Address(4, "Mocked Address", 5.0, 5.0);
        Customer customer = new Customer(4, "Mocked Customer", address);
        CustomerRepository.deleteCustomer(customer);
    }

    private static void updateCustomer() {
        System.out.println("Updating a customer...");
        Address address = new Address(4,"Mocked Address", 5.0, 5.0);
        Customer customer = new Customer(4, "Mocked Customer", address);
        customer.setName("Updated Name");
        Address updatedAddress = new Address(4,"Updated Address", 3.0, 3.0);
        customer.setAddress(updatedAddress);
        CustomerRepository.updateCustomer(customer);
    }
}
