package UI.Admin;

import Database.SellersDB;
import Log.FileWriting;
import Log.LogGeneration;
import Models.Admin;
import UI.Menu;

import java.util.LinkedList;
import java.util.Scanner;

public class UnverifiedUsers implements Menu {
    private final String prompt = "Please choose one of the available options:";
    private final String[] options = {"Approve a seller", "Back"};
    private final String commandLine = "Main | Login | Admin | Verification";
    private final String invalidOption = "ERROR: You have entered an invalid option/command.";
    private final String invalidSeller = "ERROR: Seller does not exist.";
    private final String noUnverifiedSellers = "ERROR: There are no unverified sellers.";
    Scanner scanner;
    Menu previousMenu;
    Admin admin;

    public UnverifiedUsers(Scanner scanner, Menu previousMenu, Admin admin) {
        this.scanner = scanner;
        this.previousMenu = previousMenu;
        this.admin = admin;
        runMenu();
    }

    @Override
    public void showMenu() {
        showUnverifiedUsers();
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
        boolean unverifiedSellersExist = unverifiedSellersExist();
        if (unverifiedSellersExist) {
            clearConsole(userChoice, 2);
        }
        switch (userChoice) {
            case 1:
                if (unverifiedSellersExist) {
                    showUnverifiedUsers();
                    verifySeller();
                    shortSleep();
                    clearConsole();
                } else {
                    showError(noUnverifiedSellers);
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

    public void showUnverifiedUsers() {
        LinkedList<String[]> users = admin.returnUnverifiedSellers();
        int numberOfFields = 6;
        int width = 20;
        String[] fields = {"Username", "Password", "First Name", "Last Name", "Birthday", "Age"};
        String head = formatRow(fields, width);
        String separator = generateSeparator(numberOfFields * width + numberOfFields + 1);
        System.out.println("List of unverified sellers:");
        System.out.println(separator + "\n" + head + "\n" + separator);
        String[] slicedUser;
        for (String[] user : users) {
            slicedUser = sliceArray(user, 1, 7);
            System.out.println(formatRow(slicedUser, width));
        }
        System.out.println(separator);
    }

    public String[] sliceArray(String[] array, int start, int end) { // [start, end)
        int length = end - start;
        String[] slicedArray = new String[length];
        for (int i = start; i < end; i++) {
            slicedArray[i - start] = array[i];
        }
        return slicedArray;
    }

    @Override
    public String formatRow(String[] array, int width) {
        String row = String.format("|" + centerText(array[0], width) + "|" + centerText(array[1], width) + "|" +
                centerText(array[2], width) + "|" + centerText(array[3], width) + "|" + centerText(array[4], width) +
                "|" + centerText(array[5], width) + "|");
        return row;
    }

    public String getUsername() {
        System.out.println("Please enter the username of the seller you would like to approve:");
        System.out.print(commandLine + " > ");
        String username = scanner.next();
        if (SellersDB.isSeller(username)) {
            return username;
        } else {
            showError(invalidSeller);
            return getUsername();
        }
    }

    public void verifySeller() {
        String username = getUsername();
        admin.verifySeller(username);
        System.out.println();
        showSuccessMessage(username + " has been successfully verified!");
        FileWriting.appendToFile(LogGeneration.verifyUser(username));
    }

    public boolean unverifiedSellersExist() {
        LinkedList<String[]> unverifiedSellers = admin.returnUnverifiedSellers();
        return unverifiedSellers.size() != 0;
    }
}