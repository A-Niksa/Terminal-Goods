package Database;

import Models.Seller;

import java.math.BigDecimal;
import java.util.LinkedList;

public class ItemsDB {
    public static LinkedList<String[]> items = new LinkedList<>();

    public static void addToDB(String itemID, String itemName, BigDecimal price, String tagName, String sellerName) {
        String[] itemInformation = new String[5];
        itemInformation[0] = itemID;
        itemInformation[1] = itemName;
        itemInformation[2] = String.valueOf(price);
        itemInformation[3] = tagName;
        itemInformation[4] = sellerName;
        items.add(itemInformation);
    }

    public static void reformatItemIDs(String itemID) { // reformats duplicate numbers in IDs
        String itemNumber = itemID.substring(0, 8);
        int duplicateNumber = Integer.parseInt(itemID.substring(9));
        String potentialItemNumber;
        int potentialDuplicateNumber;
        for (String[] item : items) {
            potentialItemNumber = item[0].substring(0, 8);
            if (itemNumber.equals(potentialItemNumber)) {
                potentialDuplicateNumber = Integer.parseInt(item[0].substring(9));
                if (potentialDuplicateNumber > duplicateNumber) {
                    potentialDuplicateNumber--;
                    item[0] = potentialItemNumber + "-" + potentialDuplicateNumber;
                }
            }
        }
    }

    public static void removeFromDB(String itemID) {
        for (int i = 0; i < items.size(); i++) {
            if (itemID.equals(items.get(i)[0])) {
                items.remove(i);
                reformatItemIDs(itemID);
                return;
            }
        }
    }

    public static void removeSellerItems(String sellerName) {
        String itemID;
        for (int i = 0; i < items.size(); i++) {
            if (sellerName.equals(items.get(i)[4])) {
                itemID = items.get(i)[0];
                items.remove(i);
                reformatItemIDs(itemID);
                i--;
            }
        }
    }

    public static String getItemID(String itemName) {
        for (String[] item : items) {
            if (itemName.equals(item[1])) {
                return item[0];
            }
        }
        return "not found";
    }

    public static String getItemName(String itemID) {
        for (String[] item : items) {
            if (itemID.equals(item[0])) {
                return item[1];
            }
        }
        return "";
    }

    public static int countItems(String itemName) {
        int numberOfItems = 0;
        for (String[] item : items) {
            if (itemName.equals(item[1])) {
                numberOfItems++;
            }
        }
        return numberOfItems;
    }

    public static BigDecimal getItemPrice(String itemID) {
        for (String[] item : items) {
            if (itemID.equals(item[0])) {
                return new BigDecimal(item[2]);
            }
        }
        return new BigDecimal("-1");
    }

    public static LinkedList<String[]> getItems() {
        return items;
    }

    public static LinkedList<String[]> getItems(Seller seller) {
        LinkedList<String[]> sellerItems = new LinkedList<>();
        String sellerName = seller.getUsername();
        for (String[] item : items) {
            if (sellerName.equals(item[4])) {
                sellerItems.add(item);
            }
        }
        return sellerItems;
    }

    public static LinkedList<String[]> getItems(String tagName) {
        LinkedList<String[]> tagItems = new LinkedList<>();
        for (String[] item : items) {
            if (tagName.equals(item[3])) {
                tagItems.add(item);
            }
        }
        return tagItems;
    }
}