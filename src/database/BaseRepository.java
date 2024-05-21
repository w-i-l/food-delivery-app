package database;

import java.sql.*;

public class BaseRepository {
    static protected final Integer DUPLICATE_ENTRY_ERROR_CODE = 1062;

    static protected final String ANSI_RESET = "\u001B[0m";
    static protected final String ANSI_BLUE = "\u001B[34m";
    final static protected String ANSI_RED = "\u001B[31m";
    static protected  final String ANSI_YELLOW = "\u001B[33m";
    static protected final String ANSI_GREEN = "\u001B[32m";

    static protected void printException(SQLException e) {
        System.out.print(ANSI_RED + " === An error occured ===: " + ANSI_RESET);
        System.out.println(ANSI_BLUE + e.getMessage() + ANSI_RESET);
    }

    static protected void printWarning(String message) {
        System.out.print(ANSI_YELLOW + " === Warning ===: " + ANSI_RESET);
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    static protected void printSuccess(String message) {
        System.out.print(ANSI_GREEN + " === Success ===: " + ANSI_RESET);
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }
}
