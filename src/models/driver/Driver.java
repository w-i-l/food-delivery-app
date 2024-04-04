package models.driver;

import models.address.Address;

public class Driver {
    private final Integer id;
    private String name;
    private DriverType type;
    private Integer rating;

    public Driver(Integer id, String name, DriverType type, Integer rating) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.rating = rating;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public DriverType getType() {
        return this.type;
    }

    public Integer getRating() {
        return this.rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(DriverType type) {
        this.type = type;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Double getAverageSpeed() {
        return this.type.getAverageSpeed();
    }

    public void showDriverDetails() {
        System.out.printf("%d. \"%s\" - %s - rating: %d/5\n", this.id, this.name, this.type.getName(), this.rating);
    }

    public Double getEstimatedDeliveryTime(Address from, Address to) {
        Double distance = from.distanceTo(to);
        Double estimatedTime = distance / this.getAverageSpeed();
        return estimatedTime;
    }

    public void updateDriver(Driver driver) {
        this.name = driver.getName();
        this.type = driver.getType();
        this.rating = driver.getRating();
    }
}