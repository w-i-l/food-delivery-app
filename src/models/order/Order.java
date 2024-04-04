package models.order;

import models.customer.Customer;
import models.driver.Driver;
import models.product.Menu;
import models.product.ProductInterface;
import models.product.ProductItem;
import models.product.SpecialProduct;
import models.restaurant.Restaurant;

import java.util.Dictionary;
import java.util.Enumeration;

public class Order {
    private final Integer id;
    private Customer customer;
    private Restaurant restaurant;
    private Driver driver;
    private Double price;
    private OrderStatus status;
    private Dictionary<ProductInterface, Integer> products;

    public Order(
            Integer id,
            Customer customer,
            Restaurant restaurant,
            Driver driver,
            Double price,
            Dictionary<ProductInterface, Integer> products
    ) {
        this.id = id;
        this.customer = customer;
        this.restaurant = restaurant;
        this.driver = driver;
        this.price = 0.0;
        this.status = OrderStatus.PENDING;
        this.products = products;

        Enumeration<ProductInterface> productEnumeration = products.keys();
        while (productEnumeration.hasMoreElements()) {
            ProductInterface product = productEnumeration.nextElement();
            if (product instanceof ProductItem) {
                this.price += ((ProductItem) product).getPrice() * products.get(product);
            } else if (product instanceof Menu) {
                this.price += ((Menu) product).getPrice() * products.get(product);
            } else if (product instanceof SpecialProduct) {
                this.price += ((SpecialProduct) product).getPrice() * products.get(product);
            }
        }
    }

    public Integer getId() {
        return this.id;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public Double getPrice() {
        return this.price;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public Dictionary<ProductInterface, Integer> getProducts() {
        return this.products;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setProducts(Dictionary<ProductInterface, Integer> products) {
        this.products = products;
    }

    public Double getEstimatedDeliveryTime() {
        Double distance = this.restaurant.getAddress().distanceTo(this.customer.getAddress());
        Double estimatedTime = distance / this.driver.getAverageSpeed();
        return estimatedTime;
    }

    public void showOrderDetails() {
        System.out.printf("Order #%d - %.2f\n", this.id, this.price);
    }
}
