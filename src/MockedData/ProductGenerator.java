package MockedData;

import Models.Product.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductGenerator {

    private static List<ProductItem> generateProductItems() {
        return List.of(
            new ProductItem("Burger", 10.0),
            new ProductItem("Pizza", 15.0),
            new ProductItem("Pasta", 12.0),
            new ProductItem("Sandwich", 8.0),
            new ProductItem("Salad", 6.0)
        );
    }

    private static List<Menu> generateMenus() {
        return List.of(
            new Menu(
                "Crispy Chicken Burger",
                "Crispy Chicken Burger with Fries",
                List.of(
                    new ProductItem("Crispy Chicken Burger", 8.0),
                    new ProductItem("Fries", 2.0),
                    new ProductItem("Coke", 2.0)
                ),
                0.1
            ),
            new Menu(
                "Veggie Pizza",
                "Veggie Pizza with Coke",
                List.of(
                    new ProductItem("Veggie Pizza", 12.0),
                    new ProductItem("Coke", 2.0)
                ),
                0.15
            ),
            new Menu(
                "Spaghetti Pasta",
                "Spaghetti Pasta with Garlic Bread",
                List.of(
                    new ProductItem("Spaghetti Pasta", 10.0),
                    new ProductItem("Garlic Bread", 2.0),
                    new ProductItem("Coke", 2.0)
                ),
                0.2
            )
        );
    }

    private static List<SpecialProduct> generateSpecialProducts() {
        return List.of(
            new SpecialProduct("Special Burger", 20.0, new Date(2024, 4, 31)),
            new SpecialProduct("Special Pizza", 25.0, new Date(2024, 6, 31)),
            new SpecialProduct("Special Pasta", 22.0, new Date(2024, 7, 31))
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
