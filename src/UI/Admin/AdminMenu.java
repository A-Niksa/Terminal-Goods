package UI.Admin;

import Models.Admin;
import UI.Menu;

import java.util.Scanner;

public class AdminMenu implements Menu {
    private final String prompt = "Please choose one of the available options:";
    private final String[] options = {"List of all users", "List of unverified sellers",
                                        "Remove user", "Remove item", "Log of activities", "Log out"};
    private final String commandLine = "Main | Login | Admin";
    private final String invalidOption = "ERROR: You have entered an invalid option/command.";
    Scanner scanner;
    Menu mainMenu;
    Admin admin = new Admin();

    public AdminMenu(Scanner scanner, Menu mainMenu) {
        this.scanner = scanner;
        this.mainMenu = mainMenu;
        runMenu();
    }

    @Override
    public void showMenu() {
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
        if (userChoice != 6) {
            clearConsole(userChoice, 6);
        }
        switch (userChoice) {
            case 1:
                new AllUsers(scanner, this, admin);
                break;
            case 2:
                new UnverifiedUsers(scanner, this, admin);
                break;
            case 3:
                new UserRemoval(scanner, this, admin);
                break;
            case 4:
                new ItemRemoval(scanner, this, admin);
                break;
            case 5:
                new ActivitiesLog(scanner, this, admin);
                break;
            case 6:
                showSuccessMessage("\nYou have been successfully logged out!");
                shortSleep();
                clearConsole();
                mainMenu.runMenu();
                break;
            default:
                showError(invalidOption);
        }
        runMenu();
    }
}
