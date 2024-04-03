package Models.Product;

import java.util.Date;

public class SpecialProduct extends ProductItem {
    private Date availableUntil;

    public SpecialProduct(Integer id, String name, Double price, Date availableUntil) {
        super(id, name, price);
        this.availableUntil = availableUntil;
    }

    public Date getAvailableUntil() {
        return this.availableUntil;
    }

    public void setAvailableUntil(Date availableUntil) {
        this.availableUntil = availableUntil;
    }
}