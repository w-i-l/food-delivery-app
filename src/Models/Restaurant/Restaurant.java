package Models.Restaurant;

import Models.Address.Address;
import Models.Product.ProductInterface;

import java.util.List;

public final class Restaurant {
    private final Integer id;
    private String name;
    private Address address;
    private List<ProductInterface> products;

    public Restaurant(Integer id, String name, Address address, List<ProductInterface> products) {
        this.id = id;
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
        for (ProductInterface product : this.products) {
            System.out.printf("%d. %s - %.2f\n", product.getId(), product.getName(), product.getPrice());
        }
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}