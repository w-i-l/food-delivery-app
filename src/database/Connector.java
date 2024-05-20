package database;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class Connector {
    private static Connection connection = null;
    public static Connection getConnection() {
        return connection;
    }
    private static void initConnection() {
        String url = "jdbc:mysql://localhost/sys";
        String username = "root";
        String password = "my-secret-pw";

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection(Boolean deleteDatabase) {
        try {
            if (deleteDatabase) {
                deleteDatabase();
            }

            connection.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteDatabase() {
        File file = new File("src/database/sql/database_deletion.sql");

        try (Scanner scanner = new Scanner(file)) {
            scanner.useDelimiter(";"); // Split statements by semicolon

            while (scanner.hasNext()) {
                String query = scanner.next().trim();
                if (!query.isEmpty()) {
                    try {
                        connection.createStatement().execute(query);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("Database deleted");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void createDatabase() {
        File file = new File("src/database/sql/database_creation.sql");

        try (Scanner scanner = new Scanner(file)) {
            scanner.useDelimiter(";"); // Split statements by semicolon

            while (scanner.hasNext()) {
                String query = scanner.next().trim();
                if (!query.isEmpty()) {
                    try {
                        connection.createStatement().execute(query);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("Database created");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void populateDatabase() {
        File file = new File("src/database/sql/database_insertion.sql");

        try (Scanner scanner = new Scanner(file)) {
            scanner.useDelimiter(";"); // Split statements by semicolon

            while (scanner.hasNext()) {
                String query = scanner.next().trim();
                if (!query.isEmpty()) {
                    try {
                        connection.createStatement().execute(query);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("Database populated");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static boolean isTableEmpty(String tableName) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM " + tableName)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt("count(*)");
            return count == 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Assume table is empty if an error occurs
        }
    }

    private static boolean areAnyTablesEmpty() {
        String[] tableNames = {"Address", "Customer", "Driver", "Restaurant", "Product", "MenuItems", "RestaurantProducts", "_Order"};
        for (String tableName : tableNames) {
            if (isTableEmpty(tableName)) {
                return true;
            }
        }
        return false;
    }

    public static void init() {
        initConnection();
        createDatabase();
        if (areAnyTablesEmpty()) {
            populateDatabase();
        }
    }
}
