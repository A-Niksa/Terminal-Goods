package UI;

import java.util.InputMismatchException;
import java.util.Scanner;
import com.diogonunes.jcolor.Attribute;
import com.diogonunes.jcolor.Ansi;

public interface Menu {
    void showMenu();
    void goToMenu();
    default void runMenu() {
        showMenu();
        goToMenu();
    }

    default int getInput(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException exception) {
            scanner.next(); // resetting scanner since the pointer doesn't seem to move and will cause an infinite loop
            return -1;
        }
    }

    default void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    default void clearConsole(int currentChoice, int end) {
        if (1 <= currentChoice && currentChoice <= end) {
            clearConsole();
        }
    }

    default void shortSleep() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException exception) {
            System.out.println("ERROR: Interrupted.");
        }
    }

    default void shorterSleep() {
        try {
            Thread.sleep(1100);
        } catch (InterruptedException exception) {
            System.out.println("ERROR: Interrupted.");
        }
    }

    default void showError(String message) {
        String coloredMessage = Ansi.colorize(message, Attribute.YELLOW_TEXT());
        System.out.println("\n" + coloredMessage);
        shorterSleep();
        System.out.println();
    }

    default void showLongError(String message) {
        String coloredMessage = Ansi.colorize(message, Attribute.YELLOW_TEXT());
        System.out.println("\n" + coloredMessage);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException exception) {
            System.out.println("ERROR: Interrupted.");
        }
        System.out.println();
    }

    default void showSuccessMessage(String message) {
        String coloredMessage = Ansi.colorize(message, Attribute.GREEN_TEXT());
        System.out.println(coloredMessage);
    }

    default String centerText(String text, int width) {
        int textLength = text.length();
        int preLength = Math.max((width - textLength)/2, 0);
        int postLength = Math.max(width - preLength - textLength, 0);
        String alignedText = String.format("%" + preLength + "s%s%" + postLength + "s", " ", text, " ");
        return alignedText;
    }

    default String formatRow(String[] array, int width) {
        String row = "|" + centerText(array[0], width) + "|" + centerText(array[1], width) + "|" +
                centerText(array[2], width) + "|" + centerText(array[3], width) + "|" + centerText(array[4], width) +
                "|" + centerText(array[5], width) + "|" + centerText(array[6], width) + "|";
        return row;
    }

    default String generateSeparator(int length) {
        String separator = "";
        for (int i = 0; i < length; i++) {
            separator += "_";
        }
        return separator;
    }
}
