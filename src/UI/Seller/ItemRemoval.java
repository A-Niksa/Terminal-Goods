package UI.Seller;

import Authentication.ItemAuth;
import Database.ItemsDB;
import Log.FileWriting;
import Log.LogGeneration;
import Models.Seller;
import UI.Menu;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Scanner;

public class ItemRemoval implements Menu {
    private final String prompt = "Please choose one of the available options:";
    private final String[] options = {"Remove an item", "Back"};
    private final String commandLine = "Main | Login | Seller | Item Removal";
    private final String invalidOption = "ERROR: You have entered an invalid option/command.";
    private final String noItems = "ERROR: You have no items to remove.";
    private final String itemNotExist = "ERROR: Item does not exist.";
    private final String itemNotYours = "ERROR: Item is not yours to remove.";
    Scanner scanner;
    Menu previousMenu;
    Seller seller;

    public ItemRemoval(Scanner scanner, Menu previousMenu, Seller seller) {
        this.scanner = scanner;
        this.previousMenu = previousMenu;
        this.seller = seller;
        runMenu();
    }

    @Override
    public void showMenu() {
        showSellerItems();
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
        boolean sellerHasItems = hasItems();
        if (sellerHasItems) {
            clearConsole(userChoice, 2);
        }
        switch (userChoice) {
            case 1:
                if (sellerHasItems) {
                    showSellerItems();
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

    public void showSellerItems() {
        LinkedList<String[]> sellerItems = seller.returnSellerItems();
        int numberOfFields = 4;
        int width = 24;
        String[] fields = {"ID", "Item Name", "Price (Tooman)", "Tag"};
        String head = formatRow(fields, width);
        String separator = generateSeparator(numberOfFields * width + numberOfFields + 1);
        System.out.println(seller.getUsername() + "'s items:");
        System.out.println(separator + "\n" + head + "\n" + separator);
        String[] slicedItem;
        for (String[] item : sellerItems) {
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

    public String getItemID() {
        System.out.println("Please enter the ID of the item you would like to remove:");
        System.out.print(commandLine + " > ");
        String itemID = scanner.next();
        if (!ItemAuth.itemExists(itemID)) {
            showError(itemNotExist);
            return getItemID();
        } else if (!ItemAuth.isSellerItem(itemID, seller)) {
            showError(itemNotYours);
            return getItemID();
        } else {
            return itemID;
        }
    }

    public void removeItem() {
        String itemID = getItemID();
        String itemName = ItemsDB.getItemName(itemID);
        BigDecimal price = ItemsDB.getItemPrice(itemID);
        seller.removeItem(itemID);
        System.out.println();
        showSuccessMessage(itemName + " (ID: " + itemID + ") has been successfully removed!");
        FileWriting.appendToFile(LogGeneration.removeItem(itemID, itemName, price, seller));
    }

    public boolean hasItems() {
        LinkedList<String[]> sellerItems = seller.returnSellerItems();
        return sellerItems.size() > 0;
    }
}
