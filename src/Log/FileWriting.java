package Log;

import java.io.*;

public class FileWriting {
    private static final String path = "src//ActivityLog.txt";
    static File file = new File(path);
    static FileOutputStream fileOutputStream;
    static {
        try {
            fileOutputStream = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            try {
                file.createNewFile();
                fileOutputStream = new FileOutputStream(file, true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    static PrintStream printStream = new PrintStream(fileOutputStream);

    public static void appendToFile(String logMessage) {
        printStream.println(logMessage);
        printStream.flush();
    }

    public static void closeStream() {
        printStream.close();
    }
}
