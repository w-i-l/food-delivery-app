package MockedData;

import Models.Product.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductGenerator {

    private static List<ProductItem> generateProductItems() {
        return List.of(
            ProductFactory.createProductItem("Burger", 10.0),
            ProductFactory.createProductItem("Pizza", 15.0),
            ProductFactory.createProductItem("Pasta", 12.0),
            ProductFactory.createProductItem("Fries", 2.0),
            ProductFactory.createProductItem("Coke", 2.0),
            ProductFactory.createProductItem("Garlic Bread", 2.0)
        );
    }

    private static List<Menu> generateMenus() {
        return List.of(
            ProductFactory.createMenu("Burger Menu", "Burger, Fries, Coke", generateProductItems(), 0.1),
            ProductFactory.createMenu("Pizza Menu", "Pizza, Garlic Bread, Coke", generateProductItems(), 0.15),
            ProductFactory.createMenu("Pasta Menu", "Pasta, Garlic Bread, Coke", generateProductItems(), 0.2)
        );
    }

    private static List<SpecialProduct> generateSpecialProducts() {
        return List.of(
            ProductFactory.createSpecialProduct("Special Burger", 12.0, new Date(2021, 12, 31)),
            ProductFactory.createSpecialProduct("Special Pizza", 18.0, new Date(2021, 12, 31)),
            ProductFactory.createSpecialProduct("Special Pasta", 15.0, new Date(2021, 12, 31))
        );
    }

    public static List<ProductInterface> generateProducts() {
        List<ProductInterface> products = new ArrayList<>();
        products.addAll(generateProductItems());
        products.addAll(generateMenus());
        products.addAll(generateSpecialProducts());
        return products;
    }
}
