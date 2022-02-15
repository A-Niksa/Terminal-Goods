package Models;

import Authentication.ItemAuth;
import Authentication.UserAuth;
import Database.ItemsDB;
import Database.SellersDB;
import Database.UsersDB;
import Database.CustomersDB;

import java.util.LinkedList;

public class Admin extends User {
    public Admin() { // protected
        super("Admin", "Admin", "Admin", "Super", "Admin", "0000-01-01");
        setDefaultAge();
    }

    public void verifySeller(String username) {
        SellersDB.updateDB(username, true);
    }

    public LinkedList<String[]> returnAllUsers() { // additional security layer is not required
        return UsersDB.getUsers();
    }

    public LinkedList<String[]> returnUnverifiedSellers() {
        LinkedList<String[]> sellers = SellersDB.getSellers();
        LinkedList<String[]> unverifiedSellers = new LinkedList<>();
        String[] unverifiedSeller;
        for (String[] seller : sellers) {
            if (seller[1].equals("false")) { // booleans were converted to String when working with
                unverifiedSeller = findSeller(seller[0]);
                unverifiedSellers.add(unverifiedSeller);
            }
        }
        return unverifiedSellers;
    }

    public String[] findSeller(String username) {
        LinkedList<String[]> users = UsersDB.getUsers();
        for (String[] user : users) {
            if (username.equals(user[1])) {
                return user;
            }
        }
        return null;
    }

    public void removeUser(String username) {
        if (!UserAuth.isAdmin(username)) {
            UsersDB.removeFromDB(username);
            CustomersDB.removeFromDB(username);
            SellersDB.removeFromDB(username);
            ItemsDB.removeSellerItems(username);
        }
    }

    public void removeItem(String itemID) {
        if (ItemAuth.itemExists(itemID)) {
            ItemsDB.removeFromDB(itemID);
        }
    }

    public void setDefaultAge() {
        setAge(0);
        UsersDB.updateAge(this);
    }
}