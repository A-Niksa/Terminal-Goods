package Authentication;

import Database.ItemsDB;
import Models.Seller;

import java.math.BigDecimal;
import java.util.LinkedList;

public class ItemAuth {
    public static boolean itemExists(String itemID) {
        LinkedList<String[]> items = ItemsDB.getItems();
        for (String[] item : items) {
            if (itemID.equals(item[0])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSellerItem(String itemID, Seller seller) {
        LinkedList<String[]> sellerItems = seller.returnSellerItems();
        for (String[] item : sellerItems) {
            if (itemID.equals(item[0])) {
                return true;
            }
        }
        return false;
    }

    public static boolean tagNameExists(String tagName) {
        LinkedList<String[]> items = ItemsDB.getItems();
        for (String[] item : items) {
            if (tagName.equals(item[3])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNegative(BigDecimal price) {
        BigDecimal zero = BigDecimal.ZERO;
        return price.compareTo(zero) < 0;
    }
}
