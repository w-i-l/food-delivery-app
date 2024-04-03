package Models.Customer;

import Models.Address.Address;
import Models.Address.AddressFactory;

import java.util.Scanner;

public class CustomerFactory {

    static private Integer ID = 0;

    public static Customer createCustomer(String name, Address address) {
        return new Customer(++ID, name, address);
    }

    public static Customer createCustomer(Scanner scanner) {
        String name;
        Address address;

        System.out.print("Enter customer name: ");
        name = scanner.nextLine();

        address = AddressFactory.createAddress(scanner);

        return new Customer(++ID, name, address);
    }
}
