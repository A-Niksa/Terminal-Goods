package UI;

import Database.UsersDB;
import UI.Admin.AdminMenu;
import UI.Customer.CustomerMenu;
import UI.Seller.SellerMenu;

import java.util.Scanner;

public class LoginMenu implements Menu {
    private final String prompt = "Please choose one of the available options:";
    private final String[] options = {"Log in", "Back"};
    private final String commandLine = "Main | Login";
    private final String invalidOption = "ERROR: You have entered an invalid option/command.";
    private final String invalidLogin = "ERROR: The username you have entered is wrong or the password does not" +
                                        " match the username.";
    Scanner scanner;
    Menu previousMenu;
    private String username;

    public LoginMenu(Scanner scanner, Menu previousMenu) {
        this.scanner = scanner;
        this.previousMenu = previousMenu;
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
        clearConsole(userChoice, 2);
        switch (userChoice) {
            case 1:
                String role = login();
                clearConsole();
                if (role.equals("Admin")) {
                    new AdminMenu(scanner, previousMenu);
                } else if (role.equals("Seller")) {
                    new SellerMenu(scanner, previousMenu, username);
                } else { // would necessarily be "Customer"
                    new CustomerMenu(scanner, previousMenu, username);
                }
                break;
            case 2:
                previousMenu.runMenu();
                break;
            default:
                showError(invalidOption);
        }
        runMenu();
    }

    public String login() {
        username = getUsername();
        System.out.println();
        String password = getPassword();
        if (UsersDB.validLogin(username, password)) {
            String role = UsersDB.findRole(username);
            showSuccessMessage("\nYou have been successfully logged in as " + username + " (" + role + ").");
            shortSleep();
            return role;
        } else {
            showLongError(invalidLogin);
            return login();
        }
    }

    public String getUsername() {
        System.out.println("Please enter your username:");
        System.out.print(commandLine + " > ");
        return scanner.next();
    }

    public String getPassword() {
        System.out.println("Please enter your password:");
        System.out.print(commandLine + " > ");
        return scanner.next();
    }
}
