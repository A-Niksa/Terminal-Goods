package Models;

import Authentication.UserAuth;
import Database.CustomersDB;
import Database.ItemsDB;
import Authentication.ItemAuth;
import Authentication.CustomerAuth;

import java.math.BigDecimal;
import java.util.LinkedList;

public class Customer extends User {
    private BigDecimal balance;

    public Customer(String username, String password, String firstName, String lastName, String birthday) {
        super("Customer", username, password, firstName, lastName, birthday);
        balance = new BigDecimal("0");
        if (!UserAuth.isAdmin(username) && !CustomerAuth.isDuplicate(username)) {
            CustomersDB.addToDB(username, balance);
        }
    }

    public String[] returnAccountInfo(boolean showPassword) {
        String[] accountInfo = new String[8];
        accountInfo[0] = "Customer";
        accountInfo[1] = getUsername();
        accountInfo[3] = getFirstName();
        accountInfo[4] = getLastName();
        accountInfo[5] = getBirthday().toString();
        accountInfo[6] = String.valueOf(getAge());
        accountInfo[7] = String.valueOf(getBalance());
        if (showPassword) {
            accountInfo[2] = getPassword();
        } else {
            accountInfo[2] = "########";
        }
        return accountInfo;
    }

    public BigDecimal getBalance() {
        LinkedList<String[]> customers = CustomersDB.getCustomers();
        for (String[] customer : customers) {
            if (getUsername().equals(customer[0])) {
                return new BigDecimal(customer[1]);
            }
        }
        return balance; // will never happen since it will necessarily be called by the customer object itself
    }

    public void setBalance(BigDecimal balance) {
        CustomersDB.updateDB(getUsername(), balance);
        this.balance = balance;
    }

    public void addBalance(BigDecimal balanceToAdd) {
        BigDecimal newBalance = getBalance().add(balanceToAdd);
        setBalance(newBalance);
    }

    public LinkedList<String[]> returnItems() {
        return ItemsDB.getItems();
    }

    public LinkedList<String[]> returnItems(String tagName) {
        return ItemsDB.getItems(tagName);
    }

    public void buyItem(String itemID) {
        if (ItemAuth.itemExists(itemID)) {
            BigDecimal price = ItemsDB.getItemPrice(itemID);
            if (CustomerAuth.hasEnoughBalance(getBalance(), price)) {
                setBalance(balance.subtract(price));
                ItemsDB.removeFromDB(itemID);
            }
        }
    }
}
