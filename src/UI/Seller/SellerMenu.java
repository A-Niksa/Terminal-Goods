package UI.Seller;

import Database.UsersDB;
import Models.Seller;
import UI.Menu;

import java.util.Scanner;

public class SellerMenu implements Menu {
    private final String prompt = "Please choose one of the available options:";
    private final String[] options = {"Account information", "Add item", "Remove item", "Log out"};
    private final String commandLine = "Main | Login | Seller";
    private final String invalidOption = "ERROR: You have entered an invalid option/command.";
    Scanner scanner;
    Menu mainMenu;
    private final String username;
    Seller seller;

    public SellerMenu(Scanner scanner, Menu mainMenu, String username) {
        this.scanner = scanner;
        this.mainMenu = mainMenu;
        this.username = username;
        seller = instanitateSeller();
        runMenu();
    }

    public Seller instanitateSeller() {
        String[] userInfo = UsersDB.getUserInfo(username);
        String password = userInfo[2];
        String firstName = userInfo[3];
        String lastName = userInfo[4];
        String birthday = userInfo[5];
        UsersDB.removeFromDB(username);
        return new Seller(username, password, firstName, lastName, birthday);
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
        if (userChoice != 4) {
            clearConsole(userChoice, 3);
        }
        switch (userChoice) {
            case 1:
                new AccountInfo(scanner, this, seller);
                break;
            case 2:
                new ItemAddition(scanner, this, seller);
                break;
            case 3:
                new ItemRemoval(scanner, this, seller);
                break;
            case 4:
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
