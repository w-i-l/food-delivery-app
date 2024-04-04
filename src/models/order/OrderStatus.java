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
}
