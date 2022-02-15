package Log;

import Models.User;

import java.math.BigDecimal;

public class LogGeneration {
    public static String signUp(String username) {
        String logMessage = "REGISTRATION - username: " + username;
        return logMessage;
    }

    public static String removeItem(String itemID, String itemName, BigDecimal price) {
        String logMessage = "ITEM REMOVAL - username: Admin - ID: " + itemID + " - name: " + itemName
                + " - price: " + price;
        return logMessage;
    }

    public static String removeItem(String itemID, String itemName, BigDecimal price, User caller) {
        String callerUsername = caller.getUsername();
        String logMessage = "ITEM REMOVAL - username: " + callerUsername + " - ID: " + itemID + " - name: " + itemName
                + " - price: " + price;
        return logMessage;
    }

    public static String removeUser(String removedUsername) {
        String logMessage = "USER REMOVAL - username: Admin - removed username: " + removedUsername;
        return logMessage;
    }

    public static String verifyUser(String verifiedUserame) {
        String logMessage = "USER VERIFICATION - username: Admin - verified username: " + verifiedUserame;
        return logMessage;
    }

    public static String addBalance(BigDecimal addedBalance, User caller) {
        String callerUsername = caller.getUsername();
        String logMessage = "BALANCE ADDITION - username: " + callerUsername + " - added balance: " + addedBalance;
        return logMessage;
    }

    public static String buyItem(String itemID, String itemName, BigDecimal price, User caller) {
        String callerUsername = caller.getUsername();
        String logMessage = "ITEM PURCHASE - username: " + callerUsername + " - ID: " + itemID + " - name: " + itemName
                + " - price: " + price;
        return logMessage;
    }

    public static String addItem(String itemID, String itemName, BigDecimal price, User caller) {
        String callerUsername = caller.getUsername();
        String logMessage = "ITEM ADDITION - username: " + callerUsername + " - ID: " + itemID + " - name: " + itemName
                + " - price: " + price;
        return logMessage;
    }
}
