package models.restaurant;

import models.address.Address;
import models.address.AddressFactory;
import models.product.ProductInterface;
import models.product.ProductFactory;
import util.ScannerHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RestaurantFactory {

    static private Integer ID = 0;

    public static Restaurant createRestaurant(String name, Address address, List<ProductInterface> products) {
        return new Restaurant(ID++, name, address, products);
    }

    public static Restaurant createRestaurant(Integer id, String name, Address address, List<ProductInterface> products) {
        ID = Math.max(ID, id + 1);
        return new Restaurant(id, name, address, products);
    }

    public static Restaurant createRestaurant(Scanner scanner) {
        String name;
        Address address;
        List<ProductInterface> products = new ArrayList<ProductInterface>();

        name = ScannerHelper.nextLine("Enter restaurant name: ");

        System.out.println("Enter restaurant address");
        address = AddressFactory.createAddress(scanner);

        int productCount = ScannerHelper.nextInt("Enter number of products: ");

        for (int i = 0; i < productCount; i++) {
            int productType = ScannerHelper.nextInt("Enter product type (1 - ProductItem, 2 - SpecialProduct, 3 - Menu): ");
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
