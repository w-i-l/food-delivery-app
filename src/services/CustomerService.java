package services;

import models.customer.Customer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerService {
    private Set<Customer> customers;

    public CustomerService(List<Customer> customers) {
        this.customers = new HashSet<Customer>();
        for (Customer customer : customers) {
            this.customers.add(customer);
        }
    }

    public CustomerService() {
        this.customers = new HashSet<Customer>();
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    public void removeCustomer(Customer customer) {
        this.customers.remove(customer);
    }

    public Set<Customer> getCustomers() {
        return this.customers;
    }

    public Customer getCustomerById(Integer id) {
        for (Customer customer : this.customers) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }

    public void listAllCustomers() {
        for (Customer customer : this.customers) {
            customer.showCustomerDetails();
        }
    }
}
