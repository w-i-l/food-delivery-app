package Models.Product;

public interface ProductInterface {
    public Integer getId();
    public String getName();
    public Double getPrice();
    public void setName(String name);
    public void setPrice(Double price);
    public void showProductDetails();
}
