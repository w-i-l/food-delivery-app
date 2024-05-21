package services.audit;

import java.io.File;
import java.io.FileWriter;

public class AuditService {
    private static final String FILE_PATH = "src/services/audit/audit.csv";

    private static FileWriter fileWriter = null;

    public static void log(String tableName, AuditDatabaseAction action) {
        try {
            if (fileWriter == null) {
                fileWriter = new FileWriter(FILE_PATH, true);
                fileWriter.append("Table, Action, Timestamp");
                fileWriter.append("\n");
                fileWriter.flush();
            }

            fileWriter.append(tableName);
            fileWriter.append(",");
            fileWriter.append(AuditDatabaseAction.toString(action));
            fileWriter.append(",");
            fileWriter.append(java.time.LocalDateTime.now().toString());

            fileWriter.append("\n");
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
