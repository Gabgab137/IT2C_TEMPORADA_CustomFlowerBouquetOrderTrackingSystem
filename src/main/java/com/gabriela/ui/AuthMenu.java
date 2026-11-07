package com.gabriela.ui;

import com.gabriela.Authentication;
import com.gabriela.model.User;
import java.util.Scanner;

public class AuthMenu {

    public static void showMenu() {

        Scanner scanner = new Scanner(System.in);
        Authentication authentication = new Authentication();

        int choice;

        do {
            System.out.println("\n===== CUSTOM FLOWER BOUQUET SYSTEM =====");
            System.out.println("1. Register Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.println("\n===== REGISTER ACCOUNT =====");

                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    System.out.println("\nSelect Role:");
                    System.out.println("1. ADMINISTRATOR");
                    System.out.println("2. FLORIST");
                    System.out.println("3. ORDER_CLERK");
                    System.out.print("Enter role choice: ");

                    int roleChoice = scanner.nextInt();
                    scanner.nextLine();

                    String role = switch (roleChoice) {
                        case 1 -> "ADMINISTRATOR";
                        case 2 -> "FLORIST";
                        case 3 -> "ORDER_CLERK";
                        default -> "";
                    };

                    if (!role.isEmpty()) {
                        authentication.register(username, password, role);
                    } else {
                        System.out.println("Invalid role choice.");
                    }
                    break;

                case 2:
                    System.out.println("\n===== LOGIN =====");

                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();

                    User loggedInUser = authentication.login(
                            loginUsername, loginPassword
                    );

                    if (loggedInUser != null) {
                        System.out.println("\nLogin successful!");
                        System.out.println("Welcome, " + loggedInUser.username());

                        RoleMenu.showMenu(loggedInUser, scanner);

                    } else {
                        System.out.println("\nInvalid username or password.");
                    }
                    break;

                case 3:
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 3);
    }
}