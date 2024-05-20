package models.driver;

import java.util.Scanner;

public class DriverFactory {
    static private Integer ID = 0;

    public static Driver createDriver(String name, DriverType type, Integer rating) {
        return new Driver(ID++, name, type, rating);
    }

    public static Driver createDriver(Integer id, String name, DriverType type, Integer rating) {
        ID = Math.max(ID, id + 1);
        return new Driver(id, name, type, rating);
    }

    public static Driver createDriver(Scanner scanner) {
        String name;
        DriverType type;
        Integer rating;

        System.out.print("Enter driver name: ");
        name = scanner.next();

        System.out.print("Enter driver type (1 - CAR, 2 - PEDESTRIAN, 3 - BIKE): ");
        switch (scanner.nextInt()) {
            case 1:
                type = DriverType.CAR;
                break;
            case 2:
                type = DriverType.PEDESTRIAN;
                break;
            case 3:
                type = DriverType.BIKE;
                break;
            default:
                type = DriverType.CAR;
                break;
        }

        System.out.print("Enter driver rating: ");
        rating = scanner.nextInt();
        rating = rating < 0 ? 0 : rating > 5 ? 5 : rating;

        return new Driver(++ID, name, type, rating);
    }

    public static void setID(Integer id) {
        ID = id;
    }


}
