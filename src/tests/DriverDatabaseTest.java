package tests;

import database.DriverRepository;
import models.driver.Driver;
import models.driver.DriverType;

import java.util.List;
import java.util.Set;

public class DriverDatabaseTest {
    public static void test() {
        DriverRepository.initConnection();

        try {
            System.out.println("\n  ------ Driver Test ------");
            getDrivers();
            addDriver();
            deleteDriver();
            updateDriver();
            System.out.println("[Driver Test PASSED] All tests passed successfully\n");
        } catch (Exception e) {
            System.out.println("[Driver Test FAILED] An error occurred: " + e.getMessage());
        }
    }

    private static List<Driver> getDrivers() {
        System.out.println("Getting drivers...");
        return DriverRepository.getAllDrivers();
    }

    private static void addDriver() {
        System.out.println("Adding a driver...");
        Driver driver = new Driver(4, "Mocked Driver", DriverType.CAR, 5);
        DriverRepository.addDriver(driver);
    }

    private static void deleteDriver() {
        System.out.println("Deleting a driver...");
        Driver driver = new Driver(4, "Mocked Driver", DriverType.CAR, 5);
        DriverRepository.deleteDriver(driver.getId());
    }

    private static void updateDriver() {
        System.out.println("Updating a driver...");
        Driver driver = new Driver(4, "Mocked Driver", DriverType.CAR, 5);
        driver.setName("Updated Name");
        driver.setType(DriverType.BIKE);
        driver.setRating(3);
        DriverRepository.updateDriver(driver);
    }
}
