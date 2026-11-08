package com.gabriela.ui;

import com.gabriela.dao.UserDao;
import com.gabriela.model.User;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private final UserDao userDao;
    private final User currentUser;

    public AdminMenu(User currentUser) {
        this.userDao = new UserDao();
        this.currentUser = currentUser;
    }

    public void showMenu(Scanner scanner) {

        int choice;

        do {
            System.out.println("\n===== ADMINISTRATIVE MANAGEMENT =====");
            System.out.println("1. View All System Users");
            System.out.println("2. View Pending Registrations");
            System.out.println("3. Approve User Account");
            System.out.println("4. Update User Role and Status");
            System.out.println("5. Logout");

            choice = readIntInput(scanner, "Enter your choice: ");

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
                    updateUser(scanner);
                    break;

                case 5:
                    System.out.println("Logout successful!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 5);
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

        int userId = readIntInput(
                scanner,
                "\nEnter User ID to approve (0 to cancel): "
        );

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

    // UPDATE USER ROLE AND STATUS
    private void updateUser(Scanner scanner) {

        System.out.println("\n===== UPDATE USER ACCOUNT =====");

        List<User> users = userDao.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("No user accounts found.");
            return;
        }

        displayUsers(users);

        int userId = readIntInput(
                scanner,
                "\nEnter User ID to update (0 to cancel): "
        );

        if (userId == 0) {
            System.out.println("Update cancelled.");
            return;
        }

        User selectedUser = users.stream()
                .filter(user -> user.id() == userId)
                .findFirst()
                .orElse(null);

        if (selectedUser == null) {
            System.out.println("User ID not found.");
            return;
        }

        // PREVENT ADMINISTRATOR FROM MODIFYING OWN ACTIVE ACCOUNT
        if (currentUser != null
                && selectedUser.id() == currentUser.id()
                && currentUser.status().equalsIgnoreCase("APPROVED")) {

            System.out.println("You cannot modify your own active administrator account.");
            return;
        }

        System.out.println("\nSelected User: " + selectedUser.username());
        System.out.println("Current Role: " + selectedUser.role());
        System.out.println("Current Status: " + selectedUser.status());

        String newRole = readValidRole(scanner);

        if (newRole == null) {
            System.out.println("Update cancelled.");
            return;
        }

        String newStatus = readValidStatus(scanner);

        if (newStatus == null) {
            System.out.println("Update cancelled.");
            return;
        }

        boolean updated = userDao.updateUserRoleAndStatus(
                userId,
                newRole,
                newStatus
        );

        if (updated) {
            System.out.println("User role and status updated successfully!");
        } else {
            System.out.println("Failed to update user account.");
        }
    }

    // READ INTEGER INPUT SAFELY
    private int readIntInput(Scanner scanner, String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {
                int number = Integer.parseInt(input);

                if (number < 0) {
                    System.out.println("Please enter a valid positive number or 0 to cancel.");
                    continue;
                }

                return number;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numbers only.");
            }
        }
    }

    // VALIDATE USER ROLE
    private String readValidRole(Scanner scanner) {

        while (true) {

            System.out.println("\nAvailable Roles:");
            System.out.println("1. ADMINISTRATOR");
            System.out.println("2. FLORIST");
            System.out.println("3. ORDER_CLERK");
            System.out.println("0. Cancel");

            int choice = readIntInput(
                    scanner,
                    "Select new role: "
            );

            switch (choice) {

                case 1:
                    return "ADMINISTRATOR";

                case 2:
                    return "FLORIST";

                case 3:
                    return "ORDER_CLERK";

                case 0:
                    return null;

                default:
                    System.out.println("Invalid role selection. Please try again.");
            }
        }
    }

    // VALIDATE USER STATUS
    private String readValidStatus(Scanner scanner) {

        while (true) {

            System.out.println("\nAvailable Statuses:");
            System.out.println("1. APPROVED");
            System.out.println("2. PENDING");
            System.out.println("0. Cancel");

            int choice = readIntInput(
                    scanner,
                    "Select new status: "
            );

            switch (choice) {

                case 1:
                    return "APPROVED";

                case 2:
                    return "PENDING";

                case 0:
                    return null;

                default:
                    System.out.println("Invalid status selection. Please try again.");
            }
        }
    }
}
