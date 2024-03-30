package MockedData;

import Models.Address;
import Models.Driver.Driver;
import Models.Driver.DriverType;

import java.util.ArrayList;
import java.util.List;

public class DriverGenerator {
    public static List<Driver> generateDrivers() {
        List<Driver> drivers = new ArrayList<Driver>();
        Driver aux;
        aux = new Driver(
                "John Doe",
                DriverType.BIKE,
                5
        );
        drivers.add(aux);
        aux = new Driver(
                "Jane Doe",
                DriverType.CAR,
                4
        );
        drivers.add(aux);
        aux = new Driver(
                "Alice",
                DriverType.PEDESTRIAN,
                3
        );
        return drivers;
    }
}
