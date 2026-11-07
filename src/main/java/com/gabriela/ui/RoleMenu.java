package com.gabriela.ui;

import com.gabriela.model.User;
import java.util.Scanner;

public class RoleMenu {

    public static void showMenu(User user, Scanner scanner) {

        int choice;

        do {
            System.out.println("\n===== " + user.role() + " MENU =====");

            switch (user.role()) {

                case "ADMINISTRATOR":
                    System.out.println("1. Manage Staff Accounts");
                    System.out.println("2. Manage Customers");
                    System.out.println("3. Manage Flower Types");
                    System.out.println("4. Manage Bouquet Styles");
                    System.out.println("5. Manage Bouquet Orders");
                    System.out.println("6. View Reports");
                    System.out.println("7. View Activity Logs");
                    System.out.println("8. Logout");
                    break;

                case "FLORIST":
                    System.out.println("1. View Bouquet Orders");
                    System.out.println("2. Update Preparation Status");
                    System.out.println("3. Logout");
                    break;

                case "ORDER_CLERK":
                    System.out.println("1. Register Customers");
                    System.out.println("2. Create Bouquet Orders");
                    System.out.println("3. Search and Update Orders");
                    System.out.println("4. Manage Pickup and Delivery Details");
                    System.out.println("5. Logout");
                    break;

                default:
                    System.out.println("Unknown user role.");
                    return;
            }

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            int logoutChoice = switch (user.role()) {
                case "ADMINISTRATOR" -> 8;
                case "FLORIST" -> 3;
                case "ORDER_CLERK" -> 5;
                default -> 0;
            };

            if (choice == logoutChoice) {
                System.out.println("Logout successful!");
                return;
            }

            System.out.println("This feature will be implemented in a later development step.");

        } while (true);
    }
}