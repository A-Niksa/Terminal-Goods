package UI.Admin;

import Models.Admin;
import UI.Menu;

import java.util.LinkedList;
import java.util.Scanner;

public class AllUsers implements Menu {
    private final String prompt = "Please choose one of the available options:";
    private final String[] options = {"Back"};
    private final String commandLine = "Main | Login | Admin | Users";
    private final String invalidOption = "ERROR: You have entered an invalid option/command.";
    Scanner scanner;
    Menu previousMenu;
    Admin admin;

    public AllUsers(Scanner scanner, Menu previousMenu, Admin admin) {
        this.scanner = scanner;
        this.previousMenu = previousMenu;
        this.admin = admin;
        runMenu();
    }

    @Override
    public void showMenu() {
        showAllUsers();
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

    public void showAllUsers() {
        LinkedList<String[]> users = admin.returnAllUsers();
        int numberOfFields = 7;
        int width = 20; //
        String[] fields = {"Role", "Username", "Password", "First Name", "Last Name", "Birthday", "Age"};
        String head = formatRow(fields, width);
        String separator = generateSeparator(numberOfFields * width + numberOfFields + 1);
        System.out.println("List of all users:");
        System.out.println(separator + "\n" + head + "\n" + separator);
        for (String[] user : users) {
            System.out.println(formatRow(user, width));
        }
        System.out.println(separator);
    }
}
