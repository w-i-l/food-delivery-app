package database;

import models.address.Address;
import models.address.AddressFactory;
import services.audit.AuditDatabaseAction;
import services.audit.AuditService;

import java.sql.*;

public class AddressRepository extends BaseRepository {
    private static Connection connection = null;

    public static void initConnection() {
       printSuccess("AddressRepository: Connection initialized");
        connection = Connector.getConnection();
    }

    public static Address addAddress(Address address) {
        AuditService.log("Address", AuditDatabaseAction.CREATE);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                INSERT INTO Address (id, name, latitude, longitude)
                VALUES (?, ?, ?, ?)
                """);
            preparedStatement.setInt(1, address.getId());
            preparedStatement.setString(2, address.getName());
            preparedStatement.setDouble(3, address.getLatitude());
            preparedStatement.setDouble(4, address.getLongitude());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                String warningMessage = "Address (id:" + address.getId() + ") already exists";
                printWarning(warningMessage);
            } else {
                printException(e);
            }
        }

        return address;
    }

    public static void deleteAddress(Integer addressId) {
        AuditService.log("Address", AuditDatabaseAction.DELETE);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                DELETE FROM Address
                WHERE id = ?
                """);
            preparedStatement.setInt(1, addressId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printException(e);
        }
    }

    public static void updateAddress(Address address) {
        AuditService.log("Address", AuditDatabaseAction.UPDATE);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                UPDATE Address
                SET name = ?, latitude = ?, longitude = ?
                WHERE id = ?
                """);
            preparedStatement.setString(1, address.getName());
            preparedStatement.setDouble(2, address.getLatitude());
            preparedStatement.setDouble(3, address.getLongitude());
            preparedStatement.setInt(4, address.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printException(e);
        }
    }

    public static Address getAddressById(Integer addressId) {
        AuditService.log("Address", AuditDatabaseAction.READ);

        Address address = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                SELECT name, latitude, longitude
                FROM Address
                WHERE id = ?
                """);
            preparedStatement.setInt(1, addressId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Double latitude = resultSet.getDouble("latitude");
                Double longitude = resultSet.getDouble("longitude");

                address = AddressFactory.createAddress(addressId, name, latitude, longitude);
            }
        } catch (SQLException e) {
            printException(e);
        }

        return address;
    }

    public static Integer getMaximumId() {
        AuditService.log("Address", AuditDatabaseAction.READ);

        Integer id = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                SELECT MAX(id) as id
                FROM Address
                """);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            printException(e);
        }

        return id;
    }
}
