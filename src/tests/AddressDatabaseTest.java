package tests;

import database.AddressRepository;
import models.address.Address;

public class AddressDatabaseTest {
    public static void test() {
        System.out.println("\n  ------ Address Test ------");
        AddressRepository.initConnection();
        showAddress();
        addAddress();
        updateAddress();
        deleteAddress();
        System.out.println("[Address Test PASSED] All tests passed successfully\n");
    }

    private static void showAddress() {
        System.out.println("Showing address...");
        Address address = AddressRepository.getAddressById(1);
        System.out.println(address);
    }

    private static void addAddress() {
        System.out.println("Adding an address...");
        Address address = new Address(10, "Mocked Address", 1.0, 1.0);
        AddressRepository.addAddress(address);
    }

    private static void deleteAddress() {
        System.out.println("Deleting an address...");
        AddressRepository.deleteAddress(10);
    }

    private static void updateAddress() {
        System.out.println("Updating an address...");
        Address address = new Address(10, "UPDTAED Mocked Address", 1.1, 1.1);
        AddressRepository.updateAddress(address);
    }
}
