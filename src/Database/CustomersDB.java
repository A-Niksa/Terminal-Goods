package Database;

import java.math.BigDecimal;
import java.util.LinkedList;

public class CustomersDB {
    public static LinkedList<String[]> customers = new LinkedList<>();

    public static void addToDB(String username, BigDecimal balance) {
        String[] customerInformation = new String[2];
        customerInformation[0] = username;
        customerInformation[1] = String.valueOf(balance);
        customers.add(customerInformation);
    }

    public static void updateDB(String username, BigDecimal updatedBalance) {
        for (String[] customer : customers) {
            if (username.equals(customer[0])) {
                customer[1] = String.valueOf(updatedBalance);
                return;
            }
        }
    }

    public static void removeFromDB(String username) {
        for (int i = 0; i < customers.size(); i++) {
            if (username.equals(customers.get(i)[0])) {
                customers.remove(i);
                return;
            }
        }
    }

    public static LinkedList<String[]> getCustomers() {
        return customers;
    }
}
