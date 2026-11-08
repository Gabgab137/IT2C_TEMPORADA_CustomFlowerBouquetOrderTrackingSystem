
package com.gabriela.ui;

import com.gabriela.dao.UserDao;
import com.gabriela.model.User;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private final UserDao userDao;

    public AdminMenu() {
        userDao = new UserDao();
    }

    public void showMenu(Scanner scanner) {

        int choice = 0;

        do {
            System.out.println("\n===== ADMINISTRATIVE MANAGEMENT =====");
            System.out.println("1. View All System Users");
            System.out.println("2. View Pending Registrations");
            System.out.println("3. Approve User Account");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {

                case 1:
                    displayUsers(userDao.getAllUsers());
                    break;

                case 2:
                    displayUsers(userDao.getPendingUsers());
                    break;

                case 3:
                    approveUser(scanner);
                    break;

                case 4:
                    System.out.println("Logout successful!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 4);
    }

    // DISPLAY USERS IN A FORMATTED TABLE
    private void displayUsers(List<User> users) {

        System.out.println("\n===== SYSTEM USER RECORDS =====");

        String border = "------------------------------------------------------";

        System.out.println(border);

        System.out.printf(
                "%-5s | %-15s | %-12s | %-10s%n",
                "ID", "USERNAME", "ROLE", "STATUS"
        );

        System.out.println(border);

        if (users.isEmpty()) {
            System.out.println("No user records found.");
        } else {

            for (User user : users) {

                System.out.printf(
                        "%-5s | %-15s | %-12s | %-10s%n",
                        user.id(),
                        user.username(),
                        user.role(),
                        user.status()
                );
            }
        }

        System.out.println(border);
    }

    // APPROVE PENDING USER ACCOUNT
    private void approveUser(Scanner scanner) {

        List<User> pendingUsers = userDao.getPendingUsers();

        System.out.println("\n===== PENDING USER APPROVAL =====");

        if (pendingUsers.isEmpty()) {
            System.out.println("There are no pending registrations.");
            return;
        }

        displayUsers(pendingUsers);

        System.out.print("\nEnter User ID to approve (0 to cancel): ");

        String input = scanner.nextLine();

        int userId;

        try {
            userId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid User ID.");
            return;
        }

        if (userId == 0) {
            System.out.println("Approval cancelled.");
            return;
        }

        boolean isPendingUser = pendingUsers.stream()
                .anyMatch(user -> user.id() == userId);

        if (!isPendingUser) {
            System.out.println("User ID not found in pending registrations.");
            return;
        }

        if (userDao.approveUser(userId)) {
            System.out.println("User account approved successfully!");
        } else {
            System.out.println("Failed to approve user account.");
        }
    }
}
