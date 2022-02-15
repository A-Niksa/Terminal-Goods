package UI.Customer;

import Authentication.CustomerAuth;
import Authentication.ItemAuth;
import Database.ItemsDB;
import Log.FileWriting;
import Log.LogGeneration;
import Models.Customer;
import UI.Menu;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Scanner;

public class ItemsSearch implements Menu {
    private final String prompt = "Please choose one of the available options:";
    private final String[] options = {"Search by tag name", "Back"};
    private final String commandLine = "Main | Login | Customer | Search";
    private final String invalidOption = "ERROR: You have entered an invalid option/command.";
    private final String noItemsWithTag = "ERROR: No items with this tag name exist.";
    private final String noItems = "ERROR: There are no items to buy.";
    private final String noBalance = "ERROR: You have no balance.";
    private final String itemNotExist = "ERROR: Item does not exist.";
    private final String notEnoughBalance = "ERROR: You do not have enough balance to purchase this item.";
    Scanner scanner;
    Menu previousMenu;
    Customer customer;

    public ItemsSearch(Scanner scanner, Menu previousMenu, Customer customer) {
        this.scanner = scanner;
        this.previousMenu = previousMenu;
        this.customer = customer;
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
        boolean itemsExist = itemsExist();
        boolean hasBalance = hasBalance();
        if (itemsExist && hasBalance) {
            clearConsole(userChoice, 3);
        }
        System.out.println();
        switch (userChoice) {
            case 1:
                if (!itemsExist) {
                    showError(noItems);
                } else {
                    String tagName = getTagName(true);
                    searchByTag(tagName);
                    if (willBuyItem()) {
                        if (!hasBalance) {
                            showError(noBalance);
                        } else {
                            searchByTag(tagName);
                            buyItem();
                            shortSleep();
                            clearConsole();
                        }
                    }
                }
                break;
            case 2:
                clearConsole();
                previousMenu.runMenu();
                break;
            default:
                showError(invalidOption);
        }
        runMenu();
    }

    public String getTagName(boolean firstTime) {
        System.out.println("Please the desired tag name:");
        System.out.print(commandLine + " > ");
        if (firstTime) {
            scanner.nextLine();
        }
        String tagName = scanner.nextLine();
        if (!ItemAuth.tagNameExists(tagName)) {
            showError(noItemsWithTag);
            return getTagName(false);
        } else {
            return tagName;
        }
    }

    public void searchByTag(String tagName) {
        clearConsole();
        showItems(tagName);
    }

    public void showItems(String tagName) {
        LinkedList<String[]> itemsByTag = ItemsDB.getItems(tagName);
        int numberOfFields = 4;
        int width = 24;
        String[] fields = {"ID", "Item Name", "Price (Tooman)", "Tag"};
        String head = formatRow(fields, width);
        String separator = generateSeparator(numberOfFields * width + numberOfFields + 1);
        System.out.println("List of items with tag name \"" + tagName + "\":");
        System.out.println(separator + "\n" + head + "\n" + separator);
        String[] slicedItem;
        for (String[] item : itemsByTag) {
            slicedItem = sliceArray(item, 0, 4);
            System.out.println(formatRow(slicedItem, width));
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
                centerText(array[2], width) + "|" + centerText(array[3], width) + "|");
        return row;
    }

    public boolean willBuyItem() {
        System.out.println("Buy an item:");
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
                return willBuyItem();
        }
    }

    public String getItemID() {
        System.out.println("Please enter the ID of the item you would like to buy:");
        System.out.print(commandLine + " > ");
        String itemID = scanner.next();
        if (!ItemAuth.itemExists(itemID)) {
            showError(itemNotExist);
            return getItemID();
        } else if (!CustomerAuth.hasEnoughBalance(customer.getBalance(), ItemsDB.getItemPrice(itemID))) {
            showError(notEnoughBalance);
            return null;
        } else {
            return itemID;
        }
    }

    public void buyItem() {
        String itemID = getItemID();
        if (itemID == null) {
            return;
        }
        String itemName = ItemsDB.getItemName(itemID);
        BigDecimal price = ItemsDB.getItemPrice(itemID);
        System.out.println();
        customer.buyItem(itemID);
        showSuccessMessage(itemName + " (ID: " + itemID + ") has been successfully purchased!");
        FileWriting.appendToFile(LogGeneration.buyItem(itemID, itemName, price, customer));
    }

    public boolean itemsExist() {
        LinkedList<String[]> items = ItemsDB.getItems();
        return items.size() > 0;
    }


    public boolean hasBalance() {
        BigDecimal balance = customer.getBalance();
        return CustomerAuth.isPositive(balance);
    }
}