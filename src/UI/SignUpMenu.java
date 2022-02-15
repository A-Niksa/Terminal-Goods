package UI;

import Authentication.UserAuth;
import Log.FileWriting;
import Log.LogGeneration;
import Models.Customer;
import Models.Seller;

import java.time.DateTimeException;
import java.util.Scanner;

public class SignUpMenu implements Menu {
    private final String prompt = "Please choose one of the available options:";
    private final String[] options = {"Register", "Back"};
    private final String commandLine = "Main | Registration";
    private final String invalidOption = "ERROR: You have entered an invalid option/command.";
    private final String cannotBeAdmin = "ERROR: You cannot register as the admin.";
    private final String usernameTaken = "ERROR: Username is already taken.";
    private final String invalidBirthday = "ERROR: You have entered an invalid date.";
    private final String wasInvalidDate = "ERROR: The date you had entered was invalid.";
    Scanner scanner;
    Menu previousMenu;

    public SignUpMenu(Scanner scanner, Menu previousMenu) {
        this.scanner = scanner;
        this.previousMenu = previousMenu;
        runMenu();
    }

    @Override
    public void showMenu() {
        System.out.println(prompt);
        int counter = 1;
        for (String option : options) {
            System.out.println("\t" + counter + ". " + options[counter - 1]);
            counter++;
        }
        System.out.print(commandLine + " > ");
    }

    @Override
    public void goToMenu() {
        int userChoice = getInput(scanner);
        clearConsole(userChoice, 2);
        switch (userChoice) {
            case 1:
                signUp();
                shortSleep();
                clearConsole();
                break;
            case 2:
                previousMenu.runMenu();
                break;
            default:
                showError(invalidOption);
        }
        runMenu();
    }

    public void signUp() {
        String username = getUsername();
        System.out.println();
        String password = getPassword();
        System.out.println();
        String firstName = getFirstName();
        System.out.println();
        String lastName = getLastName();
        System.out.println();
        String birthday = getBirthday();
        System.out.println();
        String role = getRole();
        System.out.println();
        try {
            if (role.equals("Customer")) {
                Customer customer = new Customer(username, password, firstName, lastName, birthday);
            } else { // is necessarily "Seller"
                Seller seller = new Seller(username, password, firstName, lastName, birthday);
            }
            showSuccessMessage("You have been successfully registered!");
            FileWriting.appendToFile(LogGeneration.signUp(username));
        } catch (DateTimeException exception) {
            showError(wasInvalidDate);
            signUp();
        }
    }

    public String getUsername() {
        System.out.println("Please enter your desired username:");
        System.out.print(commandLine + " > ");
        String username = scanner.next();
        if (UserAuth.isAdmin(username)) {
            showError(cannotBeAdmin);
            return getUsername();
        } else if (UserAuth.isDuplicate(username)) {
            showError(usernameTaken);
            return getUsername();
        } else {
            return username;
        }
    }

    public String getPassword() {
        System.out.println("Please enter your desired password:");
        System.out.print(commandLine + " > ");
        String password = scanner.next();
        return password;
    }

    public String getFirstName() {
        System.out.println("Please enter your first name:");
        System.out.print(commandLine + " > ");
        scanner.nextLine();
        String firstName = scanner.nextLine();
        return firstName;
    }

    public String getLastName() {
        System.out.println("Please enter your last name:");
        System.out.print(commandLine + " > ");
        String lastName = scanner.nextLine();
        return lastName;
    }

    public String getBirthday() {
        System.out.println("Please enter your birthday (format: YYYY-MM-DD):");
        System.out.print(commandLine + " > ");
        String birthday = scanner.next();
        if (UserAuth.dateIsValid(birthday)) {
            return birthday;
        } else {
            showError(invalidBirthday);
            return getBirthday();
        }
    }

    public String getRole() {
        System.out.println("As what role would you like to register?");
        System.out.println("\t1. Customer");
        System.out.println("\t2. Seller");
        System.out.print(commandLine + " > ");
        int userChoice = scanner.nextInt();
        switch (userChoice) {
            case 1:
                return "Customer";
            case 2:
                return "Seller";
            default:
                showError(invalidOption);
                return getRole();
        }
    }
}