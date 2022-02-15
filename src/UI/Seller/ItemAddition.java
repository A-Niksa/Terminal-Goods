package UI.Seller;

import Authentication.ItemAuth;
import Database.ItemsDB;
import Log.FileWriting;
import Log.LogGeneration;
import Models.Item;
import Models.Seller;
import UI.Menu;

import java.math.BigDecimal;
import java.util.Scanner;

public class ItemAddition implements Menu {
    private final String prompt = "Please choose one of the available options:";
    private final String[] options = {"Add an item", "Back"};
    private final String commandLine = "Main | Login | Seller | Item Addition";
    private final String invalidOption = "ERROR: You have entered an invalid option/command.";
    private final String notVerified = "ERROR: You have not been approved as a seller.";
    private final String negativePrice = "ERROR: Item price cannot be negative.";
    private final String wrongNumberFormat = "ERROR: The format of your input does not match a number correctly.";
    Scanner scanner;
    Menu previousMenu;
    Seller seller;

    public ItemAddition(Scanner scanner, Menu previousMenu, Seller seller) {
        this.scanner = scanner;
        this.previousMenu = previousMenu;
        this.seller = seller;
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
        boolean isVerified = seller.isVerified();
        if (isVerified) {
            clearConsole(userChoice, 2);
        }
        switch (userChoice) {
            case 1:
                if (isVerified) {
                    addItem();
                    shortSleep();
                    clearConsole();
                } else {
                    showError(notVerified);
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

    public void addItem() {
        String itemName = getItemName();
        System.out.println();
        BigDecimal price = getPrice();
        System.out.println();
        String tagName = getTagName();
        System.out.println();
        Item item = new Item(itemName, tagName, price, seller);
        String itemID = item.getItemID();
        showSuccessMessage(itemName + " (ID: " + itemID + ") has been successfully added to the store!");
        FileWriting.appendToFile(LogGeneration.addItem(itemID, itemName, price, seller));
    }

    public String getItemName() {
        System.out.println("Please enter the name of the new item:");
        System.out.print(commandLine + " > ");
        scanner.nextLine();
        String itemName = scanner.nextLine();
        return itemName;
    }

    public BigDecimal getPrice() {
        System.out.println("Please enter the price of the new item:");
        System.out.print(commandLine + " > ");
        try {
            BigDecimal price = new BigDecimal(scanner.next());
            if (!ItemAuth.isNegative(price)) {
                return price;
            } else {
                showError(negativePrice);
                return getPrice();
            }
        } catch (NumberFormatException exception) {
            showError(wrongNumberFormat);
            return getPrice();
        }
    }

    public String getTagName() {
        System.out.println("Please enter the tag name of the new item:");
        System.out.print(commandLine + " > ");
        scanner.nextLine();
        String tagName = scanner.nextLine();
        return tagName;
    }
}
