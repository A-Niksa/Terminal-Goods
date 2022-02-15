package Models;

import Authentication.SellerAuth;
import Authentication.UserAuth;
import Database.ItemsDB;
import Database.SellersDB;

import java.math.BigDecimal;
import java.util.LinkedList;

public class Seller extends User {
    private boolean isVerified;

    public Seller(String username, String password, String firstName, String lastName, String birthday) {
        super("Seller", username, password, firstName, lastName, birthday);
        isVerified = false;
        if (!UserAuth.isAdmin(username) && !SellerAuth.isDuplicate(username)) {
            SellersDB.addToDB(username, isVerified);
        }
    }

    public boolean isVerified() {
        LinkedList<String[]> sellers = SellersDB.getSellers();
        for (String[] seller : sellers) {
            if (getUsername().equals(seller[0])) {
                if (seller[1].equals("true")) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String[] returnAccountInfo(boolean showPassword) {
        String[] accountInfo = new String[9];
        accountInfo[0] = "Seller";
        accountInfo[1] = getUsername();
        accountInfo[3] = getFirstName();
        accountInfo[4] = getLastName();
        accountInfo[5] = getBirthday().toString();
        accountInfo[6] = String.valueOf(getAge());
        accountInfo[7] = isVerified() ? "Yes" : "No";
        if (showPassword) {
            accountInfo[2] = getPassword();
        } else {
            accountInfo[2] = "########";
        }
        return accountInfo;
    }

    public LinkedList<String[]> returnSellerItems() {
        return ItemsDB.getItems(this);
    }

    public void addItem(String itemName, String tagName, BigDecimal price) {
        if (isVerified()) {
            new Item(itemName, tagName, price, this);
        }
    }

    public void removeItem(String itemID) {
        if (SellerAuth.sellerHasItem(itemID, this)) {
            ItemsDB.removeFromDB(itemID);
        }
    }
}