package Models.Customer;

import Models.Address.Address;

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
        System.out.print("Name: " + this.name);
        System.out.println(" - Address: " + this.address.getName());
    }
}
