package MockedData;

import Models.Address;
import Models.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerGenerator {
    public static List<Customer> generateCustomers() {
        List<Customer> customers = new ArrayList<Customer>();
        Customer aux;
        aux = new Customer(
                "John Doe",
                new Address("1234")
        );
        customers.add(aux);
        aux = new Customer(
                "Jane Doe",
                new Address("5678")
        );
        customers.add(aux);
        aux = new Customer(
                "Alice",
                new Address("1357")
        );
        return customers;
    }
}
