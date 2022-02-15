package UI.Admin;

import Authentication.UserAuth;
import Database.UsersDB;
import Log.FileWriting;
import Log.LogGeneration;
import Models.Admin;
import UI.Menu;

import java.util.LinkedList;
import java.util.Scanner;

public class UserRemoval implements Menu {
    private final String prompt = "Please choose one of the available options:";
    private final String[] options = {"Remove a user", "Back"};
    private final String commandLine = "Main | Login | Admin | User Removal";
    private final String invalidOption = "ERROR: You have entered an invalid option/command.";
    private final String invalidUser = "ERROR: User does not exist.";
    private final String noUsers = "ERROR: There are no users that you can remove.";
    private final String cannotRemoveAdmin = "ERROR: You cannot remove yourself (Admin).";
    Scanner scanner;
    Menu previousMenu;
    Admin admin;

    public UserRemoval(Scanner scanner, Menu previousMenu, Admin admin) {
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
        boolean usersExist = usersExist();
        if (usersExist) {
            clearConsole(userChoice, 2);
        }
        switch (userChoice) {
            case 1:
                if (usersExist) {
                    showAllUsers();
                    removeUser();
                    shortSleep();
                    clearConsole();
                } else {
                    showError(noUsers);
                }
                break;
            case 2:
                clearConsole(); // additional
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
        int width = 20;
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

    public String getUsername() {
        System.out.println("Please enter the username of the user you would like to remove:");
        System.out.print(commandLine + " > ");
        String username = scanner.next();
        if (UserAuth.isAdmin(username)) {
            showError(cannotRemoveAdmin);
            return getUsername();
        } else if (UsersDB.isUser(username)) {
            return username;
        } else {
            showError(invalidUser);
            return getUsername();
        }
    }

    public void removeUser() {
        String username = getUsername();
        admin.removeUser(username);
        System.out.println();
        showSuccessMessage(username + " has been successfully removed!");
        FileWriting.appendToFile(LogGeneration.removeUser(username));
    }

    public boolean usersExist() {
        LinkedList<String[]> users = admin.returnAllUsers();
        return users.size() > 1; // there is always the admin, hence should be bigger than 1
    }
}
