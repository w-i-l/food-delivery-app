package mockedData;

import models.driver.Driver;
import models.driver.DriverFactory;
import models.driver.DriverType;

import java.util.ArrayList;
import java.util.List;

public class DriverGenerator {
    public static List<Driver> generateDrivers() {
        List<Driver> drivers = new ArrayList<Driver>();
        Driver aux;
        aux = DriverFactory.createDriver(
                "Michael Jordan",
                DriverType.CAR,
                5
        );
        drivers.add(aux);
        aux = DriverFactory.createDriver(
                "Lebron James",
                DriverType.PEDESTRIAN,
                3
        );
        drivers.add(aux);
        aux = DriverFactory.createDriver(
                "John Smith",
                DriverType.CAR,
                4
        );
        drivers.add(aux);
        return drivers;
    }
}
