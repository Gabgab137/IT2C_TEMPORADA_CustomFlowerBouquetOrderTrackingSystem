
package com.gabriela.ui;

import com.gabriela.dao.UserDao;
import java.util.Scanner;

public class AuthMenu {

    public static void showMenu() {

        Scanner scanner = new Scanner(System.in);
        UserDao userDao = new UserDao();

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

                    String role = "";

                    switch (roleChoice) {
                        case 1:
                            role = "ADMINISTRATOR";
                            break;
                        case 2:
                            role = "FLORIST";
                            break;
                        case 3:
                            role = "ORDER_CLERK";
                            break;
                        default:
                            System.out.println("Invalid role choice.");
                    }

                    if (!role.isEmpty()) {
                        userDao.registerUser(username, password, role);
                    }
                    break;


                case 2:
                    System.out.println("\n===== LOGIN =====");

                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();

                    var loggedInUser = userDao.loginUser(
                            loginUsername, loginPassword
                    );


                    if (loggedInUser != null) {
                        System.out.println("\nLogin successful!");
                        System.out.println("Welcome, " + loggedInUser.username());

                        boolean exit = showUserMenu(loggedInUser, scanner);

                        if (exit) {
                            return;
                        }

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

    private static boolean showUserMenu(
            com.gabriela.model.User user,
            Scanner scanner) {

        int choice;

        do {
            System.out.println("\n===== USER MENU =====");
            System.out.println("1. View Account Information");
            System.out.println("2. Logout");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.println("\n===== ACCOUNT INFORMATION =====");
                    System.out.println("Username: " + user.username());
                    System.out.println("Role: " + user.role());
                    System.out.println("Status: " + user.status());
                    break;

                case 2:
                    System.out.println("\nLogout successful!");
                    System.out.println("Returning to main menu...");
                    return false;

                case 3:
                    System.out.println("\nThank you for using the system!");
                    return true;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (true);
    }
}
