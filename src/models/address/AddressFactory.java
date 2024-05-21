package models.address;

import java.util.Random;
import java.util.Scanner;

public class AddressFactory {

    static private Integer ID = 0;

    public static Address createAddress(String name) {
        Random random = new Random();
        Double latitude = random.nextDouble() * 180 - 90;
        Double longitude = random.nextDouble() * 360 - 180;

        return new Address(ID++, name, latitude, longitude);
    }

    public static Address createAddress(Integer id, String name, Double latitude, Double longitude) {
        ID = Math.max(ID, id + 1);
        return new Address(id, name, latitude, longitude);
    }

    public static Address createAddress(Scanner scanner) {
        String name;

        System.out.print("Enter address name: ");
        name = scanner.next();

        return createAddress(name);
    }

    public static void setID(int id) {
        ID = Math.max(ID, id);
    }
}
