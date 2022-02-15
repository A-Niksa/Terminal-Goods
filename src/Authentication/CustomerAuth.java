package Authentication;

import Database.CustomersDB;

import java.math.BigDecimal;
import java.util.LinkedList;

public class CustomerAuth {
    public static boolean isDuplicate(String username) {
        username = username.toLowerCase();
        LinkedList<String[]> customers = CustomersDB.getCustomers();
        String candidateUsername;
        for (String[] customer : customers) {
            candidateUsername = customer[0].toLowerCase();
            if (username.equals(candidateUsername)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasEnoughBalance(BigDecimal userBalance, BigDecimal itemPrice) {
        return userBalance.compareTo(itemPrice) >= 0;
    }

    public static boolean isPositive(BigDecimal balance) {
        BigDecimal zero = BigDecimal.ZERO;
        return balance.compareTo(zero) > 0;
    }
}
