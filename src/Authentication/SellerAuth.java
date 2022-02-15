package Authentication;

import Database.ItemsDB;
import Database.SellersDB;
import Models.Seller;

import java.util.LinkedList;

public class SellerAuth {
    public static boolean sellerHasItem(String itemID, Seller seller) {
        LinkedList<String[]> sellerItems = ItemsDB.getItems(seller);
        for (String[] item : sellerItems) {
            if (itemID.equals(item[0])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDuplicate(String username) {
        username = username.toLowerCase();
        LinkedList<String[]> sellers = SellersDB.getSellers();
        String candidateUsername;
        for (String[] seller : sellers) {
            candidateUsername = seller[0].toLowerCase();
            if (username.equals(candidateUsername)) {
                return true;
            }
        }
        return false;
    }
}
