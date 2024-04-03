package Models.Address;

import java.util.Random;
import java.util.Scanner;

public class AddressFactory {

    public static Address createAddress(String name) {
        Random random = new Random();
        Double latitude = random.nextDouble() * 180 - 90;
        Double longitude = random.nextDouble() * 360 - 180;

        return new Address(name, latitude, longitude);
    }

    public static Address createAddress(Scanner scanner) {
        String name;

        System.out.print("Enter address name: ");
        name = scanner.next();

        return createAddress(name);
    }
}
