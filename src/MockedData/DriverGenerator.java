package MockedData;

import Models.Driver.Driver;
import Models.Driver.DriverFactory;
import Models.Driver.DriverType;

import java.util.ArrayList;
import java.util.List;

public class DriverGenerator {
    public static List<Driver> generateDrivers() {
        List<Driver> drivers = new ArrayList<Driver>();
        Driver aux;
        aux = DriverFactory.createDriver(
                "John Doe",
                DriverType.CAR,
                5
        );
        drivers.add(aux);
        aux = DriverFactory.createDriver(
                "Jane Doe",
                DriverType.PEDESTRIAN,
                4
        );
        drivers.add(aux);
        aux = DriverFactory.createDriver(
                "John Smith",
                DriverType.CAR,
                4
        );
        return drivers;
    }
}
