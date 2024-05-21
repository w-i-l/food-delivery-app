package models.product;

import java.util.List;

public class Menu implements ProductInterface {
    private final Integer id;
    private String name;
    private Double originalPrice;
    private Double discountedPrice;
    private String description;
    private List<ProductItem> items;

    public Menu(Integer id, String name, String description, List<ProductItem> items, Double discount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.items = items;

        this.originalPrice = 0.0;
        this.discountedPrice = 0.0;
        for (ProductItem item : items) {
            this.originalPrice += item.getPrice();
        }
        this.discountedPrice = this.originalPrice - (this.originalPrice * discount);
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Double getPrice() {
        return this.discountedPrice;
    }

    public String getDescription() {
        return this.description;
    }

    public List<ProductItem> getItems() {
        return this.items;
    }

    public Double getDiscount() {
        return this.originalPrice / this.discountedPrice * 100.0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.discountedPrice = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setItems(List<ProductItem> items) {
        this.items = items;
    }

    public void showProductDetails() {
        System.out.printf("\"%s\" - %.2f (%d items)\n", this.name, this.discountedPrice, this.items.size());
    }
}
