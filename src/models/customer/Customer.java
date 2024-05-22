package models.customer;

import models.address.Address;

import java.util.Objects;

public class Customer {
    private final Integer id;
    private String name;
    private Address address;

    public Customer(Integer id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void showCustomerDetails() {
        System.out.printf("%d. %s\n", this.id, this.name);
    }

    public void updateCustomer(Customer customer) {
        this.name = customer.getName();
        this.address = customer.getAddress();
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Customer customer = (Customer) obj;
        return Objects.equals(this.id, customer.getId());
    }
}
