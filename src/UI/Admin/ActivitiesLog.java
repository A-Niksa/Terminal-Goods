package UI.Admin;

import Log.FileReading;
import Models.Admin;
import UI.Menu;

import java.util.LinkedList;
import java.util.Scanner;

public class ActivitiesLog implements Menu {
    private final String prompt = "Please choose one of the available options:";
    private final String[] options = {"Back"};
    private final String commandLine = "Main | Login | Admin | Log";
    private final String invalidOption = "ERROR: You have entered an invalid option/command.";
    Scanner scanner;
    Menu previousMenu;
    Admin admin;

    public ActivitiesLog(Scanner scanner, Menu previousMenu, Admin admin) {
        this.scanner = scanner;
        this.previousMenu = previousMenu;
        this.admin = admin;
        runMenu();
    }

    @Override
    public void showMenu() {
        showLog();
        System.out.println(prompt);
        int counter = 1;
        for (String option : options) {
            System.out.println("\t" + counter + ". " + options[counter - 1]);
            counter++;
        }
        System.out.print(commandLine + " > ");
    }

    @Override
    public void goToMenu() {
        int userChoice = getInput(scanner);
        clearConsole(userChoice, 1);
        switch (userChoice) {
            case 1:
                previousMenu.runMenu();
                break;
            default:
                showError(invalidOption);
        }
        runMenu();
    }

    public void showLog() {
        LinkedList<String> log = FileReading.readLogFromFile();
        int length = 167;
        String separator = generateSeparator(length + 2);
        System.out.println(separator);
        System.out.println("|" + centerText("Log of Activities", length) + "|");
        System.out.println(separator);
        int shortenedLength = length - 3;
        String alignedAction;
        for (String action : log) {
            alignedAction = String.format("|   %-" + shortenedLength + "s|", action);
            System.out.println(alignedAction);
        }
        System.out.println(separator);
    }
}
