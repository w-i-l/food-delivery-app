package services;

import models.address.Address;
import models.driver.Driver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DriverService {
    private Set<Driver> drivers;

    public DriverService(List<Driver> drivers) {
        this.drivers = new HashSet<Driver>();
        for (Driver driver : drivers) {
            this.drivers.add(driver);
        }
    }

    public DriverService() {
        this.drivers = new HashSet<Driver>();
    }

    public void addDriver(Driver driver) {
        this.drivers.add(driver);
    }

    public void removeDriver(Driver driver) {
        this.drivers.remove(driver);
    }

    public Set<Driver> getDrivers() {
        return this.drivers;
    }

    public void listAllDrivers() {
        for (Driver driver : this.drivers) {
            driver.showDriverDetails();
        }
    }

    public Driver getDriverById(Integer id) {
        for (Driver driver : this.drivers) {
            if (driver.getId().equals(id)) {
                return driver;
            }
        }
        return null;
    }

    public void listAllDriversWithETA(Address from, Address to) {
        for (Driver driver : this.drivers) {
            System.out.printf("%d. \"%s\" - %s - rating: %d/5 - ETA: %.2f minutes\n", driver.getId(), driver.getName(), driver.getType().getName(), driver.getRating(), driver.getEstimatedDeliveryTime(from, to));
        }
    }
}
