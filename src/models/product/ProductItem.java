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

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        ProductItem product = (ProductItem) obj;
        return this.id.equals(product.getId());
    }
}
