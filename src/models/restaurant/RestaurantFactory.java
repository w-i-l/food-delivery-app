package models.restaurant;

import models.address.Address;
import models.address.AddressFactory;
import models.product.ProductInterface;
import models.product.ProductFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RestaurantFactory {

    static private Integer ID = 0;

    public static Restaurant createRestaurant(String name, Address address, List<ProductInterface> products) {
        return new Restaurant(ID++, name, address, products);
    }

    public static Restaurant createRestaurant(Scanner scanner) {
        String name;
        Address address;
        List<ProductInterface> products = new ArrayList<ProductInterface>();

        System.out.print("Enter restaurant name: ");
        name = scanner.next();

        System.out.println("Enter restaurant address");
        address = AddressFactory.createAddress(scanner);

        System.out.print("Enter number of products: ");
        int productCount = scanner.nextInt();

        for (int i = 0; i < productCount; i++) {
            System.out.print("Enter product type (1 - ProductItem, 2 - SpecialProduct, 3 - Menu): ");
            int productType = scanner.nextInt();
            switch (productType) {
                case 1:
                    products.add(ProductFactory.createProductItem(scanner));
                    break;
                case 2:
                    products.add(ProductFactory.createSpecialProduct(scanner));
                    break;
                case 3:
                    products.add(ProductFactory.createMenu(scanner));
                    break;
                default:
                    System.out.println("Invalid product type");
                    break;
            }
        }

        return new Restaurant(ID++, name, address, products);
    }
}
