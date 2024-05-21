package models.product;

import util.ScannerHelper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
public class ProductFactory {
    static private Integer ID = 0;

    public static ProductItem createProductItem(String name, Double price) {
        return new ProductItem(ID++, name, price);
    }

    public static ProductItem createProductItem(Integer id, String name, Double price) {
        ID = Math.max(ID, id + 1);
        return new ProductItem(id, name, price);
    }

    public static SpecialProduct createSpecialProduct(String name, Double price, Date availableUntil) {
        return new SpecialProduct(ID++, name, price, availableUntil);
    }

    public static SpecialProduct createSpecialProduct(Integer id, String name, Double price, Date availableUntil) {
        ID = Math.max(ID, id + 1);
        return new SpecialProduct(id, name, price, availableUntil);
    }

    public static Menu createMenu(String name, String descriptiom, List<ProductItem> products, Double discount) {
        return new Menu(ID++, name, descriptiom, products, discount);
    }

    public static Menu createMenu(Integer id, String name, String descriptiom, List<ProductItem> products, Double discount) {
        ID = Math.max(ID, id + 1);
        return new Menu(id, name, descriptiom, products, discount);
    }

    public static ProductItem createProductItem(Scanner scanner) {
        String name;
        Double price;

        name = ScannerHelper.nextLine("Enter product name: ");

        price = ScannerHelper.nextDouble("Enter product price: ");

        return new ProductItem(ID++, name, price);
    }

    public static SpecialProduct createSpecialProduct(Scanner scanner) {
        String name;
        Double price;
        Date availableUntil;

        name = ScannerHelper.nextLine("Enter product name: ");

        price = ScannerHelper.nextDouble("Enter product price: ");

        System.out.print("Enter product available until(dd-MM-yyyy): ");
        String dateString = scanner.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        availableUntil = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return new SpecialProduct(ID++, name, price, availableUntil);
    }

    public static Menu createMenu(Scanner scanner) {
        String name;
        String description;
        Double discount;
        List<ProductItem> products = new ArrayList<>();;

        name = ScannerHelper.nextLine("Enter menu name: ");

        description = ScannerHelper.nextLine("Enter menu description: ");

        discount = ScannerHelper.nextDouble("Enter menu discount: ");

        Integer productCount = ScannerHelper.nextInt("Enter number of products in menu: ");

        for (int i = 0; i < productCount; i++) {
            products.add(ProductFactory.createProductItem(scanner));
        }

        return new Menu(ID++, name, description, products, discount);
    }
}
