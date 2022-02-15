package UI.Customer;

import Database.UsersDB;
import Models.Customer;
import UI.Menu;

import java.util.Scanner;

public class CustomerMenu implements Menu {
    private final String prompt = "Please choose one of the available options:";
    private final String[] options = {"Account information", "Add balance", "List of items",
                                    "Search by tag", "Log out"};
    private final String commandLine = "Main | Login | Customer";
    private final String invalidOption = "ERROR: You have entered an invalid option/command.";
    Scanner scanner;
    Menu mainMenu;
    private final String username;
    Customer customer;

    public CustomerMenu(Scanner scanner, Menu mainMenu, String username) {
        this.scanner = scanner;
        this.mainMenu = mainMenu;
        this.username = username;
        customer = instanitateCustomer();
        runMenu();
    }

    public Customer instanitateCustomer() {
        String[] userInfo = UsersDB.getUserInfo(username);
        String password = userInfo[2];
        String firstName = userInfo[3];
        String lastName = userInfo[4];
        String birthday = userInfo[5];
        UsersDB.removeFromDB(username);
        return new Customer(username, password, firstName, lastName, birthday);
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
        if (userChoice != 5) {
            clearConsole(userChoice, 4);
        }
        switch (userChoice) {
            case 1:
                new AccountInfo(scanner, this, customer);
                break;
            case 2:
                new BalanceAddition(scanner, this, customer);
            case 3:
                new ItemsList(scanner, this, customer);
                break;
            case 4:
                new ItemsSearch(scanner, this, customer);
                break;
            case 5:
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
