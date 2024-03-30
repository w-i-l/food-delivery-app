package Models;

import Models.Product.ProductInterface;

import java.util.List;

public final class Restaurant {
    static private Integer ID = 0;

    private final Integer id;
    private String name;
    private Address address;
    private List<ProductInterface> products;

    public Restaurant(String name, Address address, List<ProductInterface> products) {
        this.id = ++ID;
        this.name = name;
        this.address = address;
        this.products = products;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Address getAddress() {
        return this.address;
    }

    public List<ProductInterface> getProducts() {
        return this.products;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setProducts(List<ProductInterface> products) {
        this.products = products;
    }

    public void showRestaurantDetails() {
        int productCount = this.products.size();
        System.out.printf("%d. \"%s\" - %d products\n", this.id, this.name, productCount);
    }

    public void showMenu() {
        System.out.println("Menu of " + this.name);
        for (int i = 0; i < this.products.size(); i++) {
            ProductInterface product = this.products.get(i);
            System.out.printf("%d. ", i + 1);
            product.showProductDetails();
        }
    }
}