package UI.Seller;

import Models.Seller;
import UI.Menu;

import java.util.Scanner;

public class AccountInfo implements Menu {
    private final String prompt = "Please choose one of the available options:";
    private final String[] options = {"Back"};
    private final String commandLine = "Main | Login | Seller | Account";
    private final String invalidOption = "ERROR: You have entered an invalid option/command.";
    Scanner scanner;
    Menu previousMenu;
    Seller seller;

    public AccountInfo(Scanner scanner, Menu previousMenu, Seller seller) {
        this.scanner = scanner;
        this.previousMenu = previousMenu;
        this.seller = seller;
        runMenu();
    }

    @Override
    public void showMenu() {
        showAccountInfo(showPassword());
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

    public void showAccountInfo(boolean showPassword) {
        String[] accountInfo = seller.returnAccountInfo(showPassword);
        int numberOfFields = 8;
        int width = 20;
        String[] fields = {"Role", "Username", "Password", "First Name", "Last Name", "Birthday", "Age", "Verified"};
        String head = formatRow(fields, width);
        String separator = generateSeparator(numberOfFields * width + numberOfFields + 1);
        System.out.println("Account information:");
        System.out.println(separator + "\n" + head + "\n" + separator);
        System.out.println(formatRow(accountInfo, width));
        System.out.println(separator);
    }

    @Override
    public String formatRow(String[] array, int width) {
        String row = String.format("|" + centerText(array[0], width) + "|" + centerText(array[1], width) + "|" +
                centerText(array[2], width) + "|" + centerText(array[3], width) + "|" + centerText(array[4], width) +
                "|" + centerText(array[5], width) + "|" + centerText(array[6], width) + "|" +
                centerText(array[7], width) + "|");
        return row;
    }

    public boolean showPassword() {
        System.out.println("Display my password:");
        String[] options = {"Yes", "No"};
        int counter = 1;
        for (String option : options) {
            System.out.println("\t" + counter + ". " + options[counter - 1]);
            counter++;
        }
        System.out.print(commandLine + " > ");
        int userChoice = getInput(scanner);
        switch (userChoice) {
            case 1:
                clearConsole();
                return true;
            case 2:
                clearConsole();
                return false;
            default:
                showError(invalidOption);
                return showPassword();
        }
    }
}