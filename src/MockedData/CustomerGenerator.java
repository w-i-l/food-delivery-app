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
                "Michael Scott",
                AddressFactory.createAddress("Floral Street, no. 12, CANADA")
        );
        customers.add(aux);
        aux = CustomerFactory.createCustomer(
                "Janna Irish",
                AddressFactory.createAddress("Baker Street, no. 221B, UK")
        );
        customers.add(aux);
        aux = CustomerFactory.createCustomer(
                "John Smith",
                AddressFactory.createAddress("Wall Street, no. 1, USA")
        );
        customers.add(aux);
        return customers;
    }
}
