package models.order;

public enum OrderStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
    DELIVERED;

    public String toString() {
        return switch (this) {
            case PENDING -> "Pending";
            case ACCEPTED -> "Accepted";
            case REJECTED -> "Rejected";
            case DELIVERED -> "Delivered";
        };
    }

    public static OrderStatus fromString(String status) {
        return switch (status) {
            case "Pending" -> PENDING;
            case "Accepted" -> ACCEPTED;
            case "Rejected" -> REJECTED;
            case "Delivered" -> DELIVERED;
            default -> null;
        };
    }
}
