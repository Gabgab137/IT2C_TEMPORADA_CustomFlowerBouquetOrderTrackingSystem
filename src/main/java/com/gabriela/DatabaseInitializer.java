
package com.gabriela;

import com.gabriela.dao.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {

        String[] createTables = {

                // 1. USERS
                """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                roles TEXT NOT NULL,
                status TEXT NOT NULL DEFAULT 'PENDING'
            )
            """,

                // 2. CUSTOMERS
                """
            CREATE TABLE IF NOT EXISTS CUSTOMERS (
                CustomerID INTEGER PRIMARY KEY AUTOINCREMENT,
                FullName TEXT NOT NULL,
                PhoneNumber TEXT,
                Email TEXT,
                Address TEXT
            )
            """,

                // 3. FLOWER_TYPES
                """
            CREATE TABLE IF NOT EXISTS FLOWER_TYPES (
                FlowerTypeID INTEGER PRIMARY KEY AUTOINCREMENT,
                FlowerName TEXT NOT NULL,
                Description TEXT,
                AvailabilityStatus TEXT
            )
            """,

                // 4. BOUQUET_STYLES
                """
            CREATE TABLE IF NOT EXISTS BOUQUET_STYLES (
                BouquetStyleID INTEGER PRIMARY KEY AUTOINCREMENT,
                StyleName TEXT NOT NULL,
                Description TEXT,
                AvailabilityStatus TEXT
            )
            """,

                // 5. BOUQUET_ORDERS
                """
            CREATE TABLE IF NOT EXISTS BOUQUET_ORDERS (
                OrderID INTEGER PRIMARY KEY AUTOINCREMENT,
                CustomerID INTEGER NOT NULL,
                Occasion TEXT,
                BudgetMinimum REAL,
                BudgetMaximum REAL,
                ColorPreference TEXT,
                BouquetStyleID INTEGER,
                SpecialInstructions TEXT,
                RecipientName TEXT,
                RecipientContact TEXT,
                FulfillmentMethod TEXT,
                FulfillmentAddress TEXT,
                RequestedDate TEXT,
                RequestedTime TEXT,
                GiftMessage TEXT,
                OrderDate TIMESTAMP,
                Status TEXT,
                TotalPrice REAL,

                FOREIGN KEY (CustomerID)
                    REFERENCES CUSTOMERS(CustomerID),

                FOREIGN KEY (BouquetStyleID)
                    REFERENCES BOUQUET_STYLES(BouquetStyleID)
            )
            """,

                // 6. ORDER_DETAILS
                """
            CREATE TABLE IF NOT EXISTS ORDER_DETAILS (
                OrderDetailID INTEGER PRIMARY KEY AUTOINCREMENT,
                OrderID INTEGER NOT NULL,
                FlowerTypeID INTEGER NOT NULL,
                Quantity INTEGER,
                Notes TEXT,

                FOREIGN KEY (OrderID)
                    REFERENCES BOUQUET_ORDERS(OrderID),

                FOREIGN KEY (FlowerTypeID)
                    REFERENCES FLOWER_TYPES(FlowerTypeID)
            )
            """,

                // 7. ACTIVITY_LOGS
                """
            CREATE TABLE IF NOT EXISTS ACTIVITY_LOGS (
                LogID INTEGER PRIMARY KEY AUTOINCREMENT,
                UserID INTEGER NOT NULL,
                ActionType TEXT,
                AffectedRecord TEXT,
                Description TEXT,
                ActivityDateTime TIMESTAMP,

                FOREIGN KEY (UserID)
                    REFERENCES users(id)
            )
            """
        };

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("PRAGMA foreign_keys = ON");

            for (String sql : createTables) {
                statement.execute(sql);
            }

            System.out.println("All seven database tables initialized successfully!");

        } catch (SQLException e) {
            System.out.println("Database initialization failed: " + e.getMessage());
        }
    }
}
