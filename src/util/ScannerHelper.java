package util;

import java.util.Scanner;

public class ScannerHelper {
    private static Scanner scanner = null;

    static protected final String ANSI_RESET = "\u001B[0m";

    final static protected String ANSI_RED = "\u001B[31m";

    public static void init(Scanner scanner) {
        ScannerHelper.scanner = scanner;
    }

    public static Integer nextInt(String message) {
        while(true) {
            if (message != null) {
                System.out.print(message);
            }
            try {
                Integer i = scanner.nextInt();
                scanner.nextLine();
                return i;
            } catch (Exception e) {
                printError("Invalid input. Please enter an integer.");
                scanner.nextLine();
            }
        }
    }

    public static Double nextDouble(String message) {
        while(true) {
            if (message != null) {
                System.out.print(message);
            }
            try {
                Double d = scanner.nextDouble();
                scanner.nextLine();
                return d;
            } catch (Exception e) {
                printError("Invalid input. Please enter a double.");
                scanner.nextLine();
            }
        }
    }

    public static String nextLine(String message) {
        if (message != null) {
            System.out.print(message);
        }
        return scanner.nextLine();
    }

    public static void printError(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }
}
