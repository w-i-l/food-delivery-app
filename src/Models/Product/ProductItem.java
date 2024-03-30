package Models.Product;

import java.util.List;

public class ProductItem implements ProductInterface {
    static private Integer ID = 0;

    private final Integer id;
    private String name;
    private Double price;

    public ProductItem(String name, Double price) {
        this.id = ++ID;
        this.name = name;
        this.price = price;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void showProductDetails() {
        System.out.printf("\"%s\" - %.2f\n", this.name, this.price);
    }
}
