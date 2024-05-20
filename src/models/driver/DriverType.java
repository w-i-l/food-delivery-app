package models.driver;

public enum DriverType {
    BIKE,
    CAR,
    PEDESTRIAN;

    Double getAverageSpeed() {
        return switch (this) {
            case BIKE -> 20.0;
            case CAR -> 50.0;
            case PEDESTRIAN -> 4.0;
        };
    }

    public String getName() {
        return switch (this) {
            case BIKE -> "Bike";
            case CAR -> "Car";
            case PEDESTRIAN -> "Pedestrian";
        };
    }

    public static DriverType fromString(String type) {
        return switch (type) {
            case "BIKE" -> BIKE;
            case "CAR" -> CAR;
            case "PEDESTRIAN" -> PEDESTRIAN;
            default -> null;
        };
    }
}
