package tests;

import database.ProductRepository;
import models.order.Order;
import models.product.ProductInterface;
import models.product.ProductItem;
import models.product.SpecialProduct;
import models.restaurant.Restaurant;

import java.util.Date;
import java.util.List;

public class ProductDatabaseTest {
    public static void test() {
        ProductRepository.initConnection();

        System.out.println("\n  ------ Product Test ------");
        getProductsForRestaurant();
        getProductsForOrder();
        addProduct();
        updateProduct();
        addProductToRestaurant();
        addProductToOrder();
        deleteProduct();
        deleteProductFromRestaurant();
        deleteProductFromOrder();
        System.out.println("[Product Test PASSED] All tests passed successfully\n");
    }

    private static void addProduct() {
        System.out.println("Adding a product...");
        System.out.println("== Adding a product item == ");
        ProductInterface product = new ProductItem(14, "Mocked Product", 5.0);
        ProductRepository.addProduct(product);

        System.out.println("== Adding a special product == ");
        ProductInterface specialProduct = new SpecialProduct(15, "Mocked Special Product", 10.0, new Date());
        ProductRepository.addProduct(specialProduct);

        System.out.println("== Adding a menu == ");
        ProductInterface menu = new models.product.Menu(16, "Mocked Menu", "Mocked Description", List.of(
                new ProductItem(17, "Mocked Product 1", 5.0),
                new SpecialProduct(18, "Mocked Special Product 1", 10.0, new Date())
        ), 0.1);
        ProductRepository.addProduct(menu);
    }

    private  static void deleteProduct() {
        System.out.println("Deleting a product...");
        System.out.println("== Deleting a product item == ");
        ProductInterface product = new ProductItem(14, "Mocked Product", 5.0);
        ProductRepository.deleteProduct(product);

        System.out.println("== Deleting a special product == ");
        ProductInterface specialProduct = new SpecialProduct(15, "Mocked Special Product", 10.0, new Date());
        ProductRepository.deleteProduct(specialProduct);

        System.out.println("== Deleting a menu == ");
        ProductInterface menu = new models.product.Menu(16, "Mocked Menu", "Mocked Description", List.of(
                new ProductItem(17, "Mocked Product 1", 5.0),
                new SpecialProduct(18, "Mocked Special Product 1", 10.0, new Date())
        ), 0.1);
        ProductRepository.deleteProduct(menu);
    }

    private static void updateProduct() {
        System.out.println("Updating a product...");
        System.out.println("== Updating a product item == ");
        ProductInterface product = new ProductItem(14, "NEW Mocked Product", 5.5);
        ProductRepository.updateProduct(product);

        System.out.println("== Updating a special product == ");
        ProductInterface specialProduct = new SpecialProduct(15, "NEW Mocked Special Product", 10.5, new Date());
        ProductRepository.updateProduct(specialProduct);

        System.out.println("== Updating a menu == ");
        ProductInterface menu = new models.product.Menu(16, "NEW Mocked Menu", "NEW Mocked Description", List.of(
                new ProductItem(17, "NEW Mocked Product 1", 5.5),
                new SpecialProduct(18, "NEW Mocked Special Product 1", 10.5, new Date())
        ), 0.2);
        ProductRepository.updateProduct(menu);
    }

    private static void addProductToRestaurant() {
        System.out.println("Adding a product to restaurant...");
        Restaurant restaurant = new Restaurant(1, "Mocked Restaurant", null, null);
        ProductInterface product = new ProductItem(14, "Mocked Product", 5.0);
        ProductRepository.addProductToRestaurant(product, restaurant);
    }

    private static void deleteProductFromRestaurant() {
        System.out.println("Deleting a product from restaurant...");
        Restaurant restaurant = new Restaurant(1, "Mocked Restaurant", null, null);
        ProductInterface product = new ProductItem(14, "Mocked Product", 5.0);
        ProductRepository.deleteProductFromRestaurant(product, restaurant);
    }

    private static void getProductsForRestaurant() {
        System.out.println("Getting products for restaurant...");
        Restaurant restaurant = new Restaurant(1, "Mocked Restaurant", null, null);
        List<ProductInterface> products = ProductRepository.getProductsForRestaurant(restaurant);
        for (ProductInterface product : products) {
            product.showProductDetails();
        }
    }

    private static void getProductsForOrder() {
        System.out.println("Getting products for order...");
        Order order = new Order(1, null, null, null, 0.0, new java.util.Hashtable<>());
        List<ProductInterface> products = ProductRepository.getProductsForOrder(order);
        for (ProductInterface product: products) {
            product.showProductDetails();
        }
    }

    private static void addProductToOrder() {
        System.out.println("Adding a product to order...");
        Order order = new Order(1, null, null, null, 0.0, new java.util.Hashtable<>());
        ProductInterface product = new ProductItem(14, "Mocked Product", 5.0);
        ProductRepository.addProductToOrder(product, order);
    }

    private static void deleteProductFromOrder() {
        System.out.println("Deleting a product from order...");
        Order order = new Order(1, null, null, null, 0.0, new java.util.Hashtable<>());
        ProductInterface product = new ProductItem(14, "Mocked Product", 5.0);
        ProductRepository.deleteProductFromOrder(product, order);
    }
}
