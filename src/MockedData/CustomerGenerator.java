package MockedData;

import Models.Address.Address;
import Models.Address.AddressFactory;
import Models.Customer.*;

import java.util.ArrayList;
import java.util.List;

public class CustomerGenerator {
    public static List<Customer> generateCustomers() {
        List<Customer> customers = new ArrayList<Customer>();
        Customer aux;
        aux = CustomerFactory.createCustomer(
                "John Doe",
                AddressFactory.createAddress("1234")
        );
        customers.add(aux);
        aux = CustomerFactory.createCustomer(
                "Janna Irish",
                AddressFactory.createAddress("5678")
        );
        customers.add(aux);
        aux = CustomerFactory.createCustomer(
                "John Smith",
                AddressFactory.createAddress("91011")
        );
        customers.add(aux);
        return customers;
    }
}
