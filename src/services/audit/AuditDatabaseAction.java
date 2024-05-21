package services.audit;

public enum AuditDatabaseAction {
    CREATE,
    READ,
    UPDATE,
    DELETE;

    static public String toString(AuditDatabaseAction action) {
        switch (action) {
            case CREATE:
                return "CREATE";
            case READ:
                return "READ";
            case UPDATE:
                return "UPDATE";
            case DELETE:
                return "DELETE";
            default:
                return "UNKNOWN";
        }
    }

    static public AuditDatabaseAction fromString(String action) {
        switch (action) {
            case "CREATE":
                return CREATE;
            case "READ":
                return READ;
            case "UPDATE":
                return UPDATE;
            case "DELETE":
                return DELETE;
            default:
                return null;
        }
    }
}
