package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

public class User {

    // Logger instance for logging messages
    private static final Logger logger = Logger.getLogger(User.class.getName());

    // Static map to store users (thread-safe)
    private static final ConcurrentMap<String, User> users = new ConcurrentHashMap<>();

    // User attributes (private for encapsulation)
    private String username;
    private String password; // Ideally should be hashed
    private String address;
    private String id;
    private String phone;
    private String type;
    private boolean loggedIn;

    // Default constructor for initialization
    public User() {
        // Add a default admin user
        addDefaultUser("loaa", "123", "admin");
    }

    // Constructor with parameters to initialize a user
    public User(String username, String password, String type) {
        this.username = username;
        this.password = password; // Hash this in a real-world application
        this.type = type;
    }

    // Static method to add a default user (used during initialization)
    private static void addDefaultUser(String username, String password, String type) {
        User defaultUser = new User(username, password, type);
        users.putIfAbsent(username, defaultUser);
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null && !username.trim().isEmpty()) {
            this.username = username;
        } else {
            logger.warning("Username cannot be null or empty.");
        }
    }

    public void setPassword(String password) {
        if (password != null && !password.trim().isEmpty()) {
            this.password = password; // Hash this in a real-world application
        } else {
            logger.warning("Password cannot be null or empty.");
        }
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    private void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    // Login method
    public boolean login(String username, String password) {
        if (username == null || password == null) {
            logger.warning("Username or password cannot be null.");
            return false;
        }

        User user = users.get(username);
        if (user != null && user.password.equals(password)) {
            this.loggedIn = true;
            logger.info(String.format("Login successful for user: %s", username));
            return true;
        } else {
            logger.warning(String.format("Login failed for user: %s", username));
            return false;
        }
    }

    // Static method to add a user (requires the current user to be logged in)
    public static boolean addUser(User currentUser, User newUser) {
        if (currentUser == null || !currentUser.isLoggedIn()) {
            logger.warning("You must be logged in to add a user.");
            return false;
        }

        if (newUser != null && !users.containsKey(newUser.getUsername())) {
            users.put(newUser.getUsername(), newUser);
            logger.info(String.format("User '%s' added successfully.", newUser.getUsername()));
            return true;
        } else {
            logger.warning("User already exists or new user details are invalid.");
            return false;
        }
    }

    // Static method to check if a username is already registered
    public static boolean isRegistered(String username) {
        if (username == null || username.trim().isEmpty()) {
            logger.warning("Username cannot be null or empty.");
            return false;
        }

        boolean exists = users.containsKey(username);
        if (exists) {
            logger.info(String.format("User '%s' is already registered.", username));
        } else {
            logger.info(String.format("User '%s' is not registered.", username));
        }
        return exists;
    }

    // Main method for testing (optional)
    public static void main(String[] args) {
        // Initialize the system with a default user
        User system = new User();

        // Test login
        User admin = new User();
        admin.login("loaa", "123");

        // Add a new user
        User newUser = new User("john_doe", "password", "user");
        addUser(admin, newUser);

        // Check registration
        System.out.println("Is 'john_doe' registered? " + isRegistered("john_doe"));
        System.out.println("Is 'jane_doe' registered? " + isRegistered("jane_doe"));
    }
}
