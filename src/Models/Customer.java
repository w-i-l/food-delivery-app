package Models;

public class Customer {
    static private Integer ID = 0;

    private final Integer id;
    private String name;
    private Address address;

    public Customer(String name, Address address) {
        this.id = ++ID;
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
