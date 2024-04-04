package models.product;

public class ProductItem implements ProductInterface {

    protected final Integer id;
    protected String name;
    protected Double price;

    public ProductItem(Integer id, String name, Double price) {
        this.id = id;
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
