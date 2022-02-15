package UI;

import Log.FileWriting;

import java.util.Scanner;

public class MainMenu implements Menu {
    private final String prompt = "Welcome to the Terminal Goods!\nPlease choose one of the available options:";
    private final String[] options = {"Sign up", "Log in", "Close"};
    private final String commandLine = "Main";
    private final String invalidOption = "ERROR: You have entered an invalid option/command.";
    Scanner scanner;

    public MainMenu(Scanner scanner) {
        this.scanner = scanner;
        clearConsole();
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
        clearConsole(userChoice, 3);
        switch (userChoice) {
            case 1:
                new SignUpMenu(scanner, this);
                break;
            case 2:
                new LoginMenu(scanner, this);
                break;
            case 3:
                System.out.println("Let us know whenever you need anything. Goodbye!");
                FileWriting.closeStream();
                System.exit(0);
                break;
            default:
                showError(invalidOption);
        }
        runMenu();
    }
}