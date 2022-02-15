// Arsha Niksa
// Student Number: 400108706
// 11/02/2022

import Models.Admin;
import UI.MainMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin();
        new MainMenu(scanner);
    }
}