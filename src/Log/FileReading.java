package Log;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class FileReading {
    private static final String path = "src//ActivityLog.txt";
    static File file = new File(path);
    static FileOutputStream fileOutputStream;
    static Scanner scanner;
    static {
        try {
            fileOutputStream = new FileOutputStream(file, true);
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            try {
                file.createNewFile();
                fileOutputStream = new FileOutputStream(file, true);
                scanner = new Scanner(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    static PrintStream printStream = new PrintStream(fileOutputStream);
    public static LinkedList<String> activityLog = new LinkedList<>();

    public static LinkedList<String> readLogFromFile() {
        String action;
        while (scanner.hasNext()) {
            action = scanner.nextLine();
            activityLog.add(action);
        }
        return activityLog;
    }
}
