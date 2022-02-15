package Database;

import java.util.LinkedList;

public class SellersDB {
    private static final LinkedList<String[]> sellers = new LinkedList<>();

    public static void addToDB(String username, boolean isVerified) {
        String[] sellerInformation = new String[2];
        sellerInformation[0] = username;
        sellerInformation[1] = String.valueOf(isVerified);
        sellers.add(sellerInformation);
    }

    public static void updateDB(String username, boolean isVerified) {
        for (String[] seller : sellers) {
            if (username.equals(seller[0])) {
                seller[1] = String.valueOf(isVerified);
                return;
            }
        }
    }

    public static void removeFromDB(String username) {
        for (int i = 0; i < sellers.size(); i++) {
            if (username.equals(sellers.get(i)[0])) {
                sellers.remove(i);
                return;
            }
        }
    }

    public static boolean isSeller(String username) {
        for (String[] seller : sellers) {
            if (username.equals(seller[0])) {
                return true;
            }
        }
        return false;
    }

    public static LinkedList<String[]> getSellers() {
        return sellers;
    }
}
