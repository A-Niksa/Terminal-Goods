package Database;

import Authentication.UserAuth;
import Models.User;

import java.util.LinkedList;

public class UsersDB {
    private static final LinkedList<String[]> users = new LinkedList<>();

    public static void addToDB(User user) {
        String[] userInformation = new String[7];
        userInformation[0] = user.getRole();
        userInformation[1] = user.getUsername();
        userInformation[2] = user.getPassword();
        userInformation[3] = user.getFirstName();
        userInformation[4] = user.getLastName();
        userInformation[5] = user.getBirthday().toString();
        userInformation[6] = String.valueOf(user.getAge());
        users.add(userInformation);
    }

    public static void updateAge(User user) {
        for (String[] potentialUser : users) {
            if (potentialUser[1].equals(user.getUsername())) {
                potentialUser[6] = String.valueOf(user.getAge());
                return;
            }
        }
    }

    public static boolean isUser(String username) {
        for (String[] user : users) {
            if (username.equals(user[1])) {
                return true;
            }
        }
        return false;
    }

    public static String[] getUserInfo(String username) {
        for (String[] user : users) {
            if (username.equals(user[1])) {
                return user;
            }
        }
        return null;
    }

    public static boolean validLogin(String username, String password) {
        for (String[] user : users) {
            if (username.equals(user[1])) {
                if (password.equals(user[2])) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String findRole(String username) { // it will be checked beforehand to make sure that the user exists
        if (UserAuth.isAdmin(username)) {
            return "Admin";
        } else if (SellersDB.isSeller(username)) {
            return "Seller";
        } else {
            return "Customer";
        }
    }

    public static void removeFromDB(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (username.equals(users.get(i)[1])) {
                users.remove(i);
                return;
            }
        }
    }

    public static LinkedList<String[]> getUsers() {
        return users;
    }
}