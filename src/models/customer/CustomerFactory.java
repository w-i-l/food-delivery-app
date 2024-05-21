package models.customer;

import models.address.Address;
import models.address.AddressFactory;
import util.ScannerHelper;

import java.util.Scanner;

public class CustomerFactory {

    static private Integer ID = 0;

    public static Customer createCustomer(String name, Address address) {
        return new Customer(++ID, name, address);
    }

    public static Customer createCustomer(Integer id, String name, Address address) {
        ID = Math.max(ID, id + 1);
        return new Customer(id, name, address);
    }

    public static Customer createCustomer(Scanner scanner) {
        String name;
        Address address;

        name = ScannerHelper.nextLine("Enter customer name: ");

        address = AddressFactory.createAddress(scanner);

        return new Customer(ID++, name, address);
    }
}
