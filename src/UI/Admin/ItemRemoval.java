package UI.Admin;

import Authentication.ItemAuth;
import Database.ItemsDB;
import Log.FileWriting;
import Log.LogGeneration;
import Models.Admin;
import UI.Menu;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Scanner;

public class ItemRemoval implements Menu {
    private final String prompt = "Please choose one of the available options:";
    private final String[] options = {"Remove an item", "Back"};
    private final String commandLine = "Main | Login | Seller | Item Removal";
    private final String invalidOption = "ERROR: You have entered an invalid option/command.";
    private final String noItems = "ERROR: There are no items to remove.";
    private final String itemNotExist = "ERROR: Item does not exist.";
    Scanner scanner;
    Menu previousMenu;
    Admin admin;

    public ItemRemoval(Scanner scanner, Menu previousMenu, Admin admin) {
        this.scanner = scanner;
        this.previousMenu = previousMenu;
        this.admin = admin;
        runMenu();
    }

    @Override
    public void showMenu() {
        showAllItems();
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
        boolean itemsExist = itemsExist();
        if (itemsExist) {
            clearConsole(userChoice, 2);
        }
        switch (userChoice) {
            case 1:
                if (itemsExist) {
                    showAllItems();
                    removeItem();
                    shortSleep();
                    clearConsole();
                } else {
                    showError(noItems);
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

    public void showAllItems() {
        LinkedList<String[]> items = ItemsDB.getItems();
        int numberOfFields = 5;
        int width = 24;
        String[] fields = {"ID", "Item Name", "Price (Tooman)", "Tag", "Seller Name"};
        String head = formatRow(fields, width);
        String separator = generateSeparator(numberOfFields * width + numberOfFields + 1);
        System.out.println("List of all items:");
        System.out.println(separator + "\n" + head + "\n" + separator);
        for (String[] item : items) {
            System.out.println(formatRow(item, width));
        }
        System.out.println(separator);
    }

    @Override
    public String formatRow(String[] array, int width) {
        String row = String.format("|" + centerText(array[0], width) + "|" + centerText(array[1], width) + "|" +
                centerText(array[2], width) + "|" + centerText(array[3], width) + "|" + centerText(array[4], width) +
                "|");
        return row;
    }

    public String getItemID() {
        System.out.println("Please enter the ID of the item you would like to remove:");
        System.out.print(commandLine + " > ");
        String itemID = scanner.next();
        if (!ItemAuth.itemExists(itemID)) {
            showError(itemNotExist);
            return getItemID();
        } else {
            return itemID;
        }
    }

    public void removeItem() {
        String itemID = getItemID();
        String itemName = ItemsDB.getItemName(itemID);
        BigDecimal price = ItemsDB.getItemPrice(itemID);
        admin.removeItem(itemID);
        System.out.println();
        showSuccessMessage(itemName + " (ID: " + itemID + ") has been successfully removed!");
        FileWriting.appendToFile(LogGeneration.removeItem(itemID, itemName, price));
    }

    public boolean itemsExist() {
        LinkedList<String[]> items = ItemsDB.getItems();
        return items.size() > 0;
    }
}
