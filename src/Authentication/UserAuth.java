package Authentication;

import Database.UsersDB;

import java.util.LinkedList;

public class UserAuth {
    public static boolean isDuplicate(String username) {
        username = username.toLowerCase();
        LinkedList<String[]> users = UsersDB.getUsers();
        String candidateUsername;
        for (String[] user : users) {
            candidateUsername = user[1].toLowerCase();
            if (username.equals(candidateUsername)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAdmin(String username) {
        username = username.toLowerCase();
        return username.equals("admin");
    }

    public static boolean canSignUp(String username) {
        return !(isAdmin(username) || isDuplicate(username));
    }

    public static boolean dateIsValid(String date) {
        // catching wrong day/month error manually -> done
        // however, the exception handling for the case above can be improved by two ways:
        // 1) distinguishing between a wrong month or a wrong year
        // 2) catching the exception when we get the input instead of catching it when we instantiate User
        if (date.length() == 10) {
            if (date.charAt(4) == '-' && date.charAt(7) == '-') {
                return isNumeric(date.substring(0, 4)) && isNumeric(date.substring(5, 7)) && isNumeric(date.substring(8));
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isNumeric(String candidate) {
        try {
            Integer.parseInt(candidate);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }
}
