package models.product;

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

    public static SpecialProduct createSpecialProduct(String name, Double price, Date availableUntil) {
        return new SpecialProduct(ID++, name, price, availableUntil);
    }

    public static Menu createMenu(String name, String descriptiom, List<ProductItem> products, Double discount) {
        return new Menu(ID++, name, descriptiom, products, discount);
    }

    public static ProductItem createProductItem(Scanner scanner) {
        String name;
        Double price;

        System.out.print("Enter product name: ");
        name = scanner.next();

        System.out.print("Enter product price: ");
        price = scanner.nextDouble();

        return new ProductItem(ID++, name, price);
    }

    public static SpecialProduct createSpecialProduct(Scanner scanner) {
        String name;
        Double price;
        Date availableUntil;

        System.out.print("Enter product name: ");
        name = scanner.next();

        System.out.print("Enter product price: ");
        price = scanner.nextDouble();

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

        System.out.print("Enter menu name: ");
        name = scanner.nextLine();

        System.out.print("Enter menu description: ");
        description = scanner.nextLine();

        System.out.print("Enter menu discount: ");
        discount = scanner.nextDouble();

        System.out.print("Enter number of products in menu: ");
        Integer productCount = scanner.nextInt();

        for (int i = 0; i < productCount; i++) {
            products.add(ProductFactory.createProductItem(scanner));
        }

        return new Menu(ID++, name, description, products, discount);
    }
}
