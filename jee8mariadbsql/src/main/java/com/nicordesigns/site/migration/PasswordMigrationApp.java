package com.nicordesigns.site.migration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Standalone password migration utility
 * Run this once to encrypt existing plain text passwords
 */
public class PasswordMigrationApp {
    
    // MariaDB connection settings from your IDE configuration
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/charitydb";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "123"; // Update this with your actual password
    
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public static void main(String[] args) {
        System.out.println("Starting password migration...");
        
        try {
            // Load MariaDB JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");
            
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            
            migratePasswords(connection);
            
            connection.close();
            System.out.println("Password migration completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error during migration: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void migratePasswords(Connection connection) throws SQLException {
        // Get all users with their current passwords
        String selectQuery = "SELECT USERNAME, PASSWORD FROM USER_ADMIN";
        Statement selectStatement = connection.createStatement();
        ResultSet resultSet = selectStatement.executeQuery(selectQuery);
        
        List<UserPassword> usersToUpdate = new ArrayList<>();
        
        // Collect users that need password updates
        while (resultSet.next()) {
            String username = resultSet.getString("USERNAME");
            String plainPassword = resultSet.getString("PASSWORD");
            
            // Skip if password is already encrypted (BCrypt hashes start with $2a$, $2b$, or $2y$)
            if (plainPassword != null && 
                (plainPassword.startsWith("$2a$") || plainPassword.startsWith("$2b$") || plainPassword.startsWith("$2y$"))) {
                System.out.println("Password for user '" + username + "' already encrypted, skipping");
                continue;
            }
            
            if (plainPassword != null && !plainPassword.trim().isEmpty()) {
                usersToUpdate.add(new UserPassword(username, plainPassword));
                System.out.println("Found user '" + username + "' with plain text password");
            } else {
                System.out.println("Warning: User '" + username + "' has null or empty password, skipping");
            }
        }
        
        resultSet.close();
        selectStatement.close();
        
        // Update passwords
        String updateQuery = "UPDATE USER_ADMIN SET PASSWORD = ? WHERE USERNAME = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
        
        for (UserPassword user : usersToUpdate) {
            // Encrypt the password
            String encryptedPassword = passwordEncoder.encode(user.plainPassword);
            
            updateStatement.setString(1, encryptedPassword);
            updateStatement.setString(2, user.username);
            
            int rowsUpdated = updateStatement.executeUpdate();
            
            if (rowsUpdated > 0) {
                System.out.println("✓ Updated password for user: " + user.username);
            } else {
                System.out.println("✗ Failed to update password for user: " + user.username);
            }
        }
        
        updateStatement.close();
        
        System.out.println("\nMigration Summary:");
        System.out.println("- Users processed: " + usersToUpdate.size());
        System.out.println("- Passwords encrypted successfully");
    }
    
    // Helper class to hold user data
    private static class UserPassword {
        final String username;
        final String plainPassword;
        
        UserPassword(String username, String plainPassword) {
            this.username = username;
            this.plainPassword = plainPassword;
        }
    }
}