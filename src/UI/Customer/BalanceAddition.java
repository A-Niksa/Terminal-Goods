package UI.Customer;

import Authentication.CustomerAuth;
import Log.FileWriting;
import Log.LogGeneration;
import Models.Customer;
import UI.Menu;

import java.math.BigDecimal;
import java.util.Scanner;

public class BalanceAddition implements Menu {
    private final String prompt = "Please choose one of the available options:";
    private final String[] options = {"Add balance to your account", "Back"};
    private final String commandLine = "Main | Login | Customer | Balance";
    private final String invalidOption = "ERROR: You have entered an invalid option/command.";
    private final String wrongNumberFormat = "ERROR: The format of your input does not match a number correctly.";
    private final String nonPositiveBalance = "ERROR: The balance you would like to add has to be positive.";
    Scanner scanner;
    Menu previousMenu;
    Customer customer;

    public BalanceAddition(Scanner scanner, Menu previousMenu, Customer customer) {
        this.scanner = scanner;
        this.previousMenu = previousMenu;
        this.customer = customer;
        runMenu();
    }

    @Override
    public void showMenu() {
        showBalance();
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
                addBalance();
                shortSleep();
                clearConsole();
                break;
            case 2:
                previousMenu.runMenu();
                break;
            default:
                showError(invalidOption);
        }
        runMenu();
    }

    public void showBalance() {
        System.out.print("Your current balance: " + customer.getBalance());
        System.out.println();
    }

    public void addBalance() {
        BigDecimal balanceToAdd = getBalanceToAdd();
        System.out.println();
        customer.addBalance(balanceToAdd);
        showSuccessMessage(balanceToAdd + " Tooman has been successfully added to your account (current balance: " +
                            customer.getBalance() + " Tooman)!");
        FileWriting.appendToFile(LogGeneration.addBalance(balanceToAdd, customer));
    }

    public BigDecimal getBalanceToAdd() {
        System.out.println("Please enter the amount of money you would like to add to your account:");
        System.out.print(commandLine + " > ");
        BigDecimal balanceToAdd;
        try {
            balanceToAdd = new BigDecimal(scanner.next());
        } catch (NumberFormatException exception) {
            showError(wrongNumberFormat);
            return getBalanceToAdd();
        }
        if (!CustomerAuth.isPositive(balanceToAdd)) {
            showError(nonPositiveBalance);
            return getBalanceToAdd();
        } else {
            return balanceToAdd;
        }
    }
}
