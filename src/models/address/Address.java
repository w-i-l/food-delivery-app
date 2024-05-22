package models.address;

import java.util.Random;

public final class Address {

    private final Integer id;
    private final Double longitude;
    private final Double latitude;
    private String name;

    public Address(Integer id, String name, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;

        Random random = new Random();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return this.id;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double distanceTo(Address address) {
        double latitudinalDifference = this.latitude - address.getLatitude();
        double longitudinalDifference = this.longitude - address.getLongitude();

        return Math.sqrt(Math.pow(latitudinalDifference, 2) + Math.pow(longitudinalDifference, 2));
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

        Address address = (Address) obj;
        return this.id.equals(address.getId());
    }
}
