package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class User {

    // Logger instance for logging messages
    private static final Logger logger = LoggerFactory.getLogger(User.class);

    // User attributes
    private String password;
    private String username;
    private String address;
    private String id;
    private String phone;
    private String type;
    private boolean logged;
    protected static final List<User> users1 = new ArrayList<>();

    // Default constructor
    public User() {
        // Add the default user if required
        User.users1.add(new User("loaa", "123", "admin"));
    }

    // Constructor with parameters to initialize the user
    public User(String name, String pass, String type) {
        this.username = name;
        this.password = pass;
        this.type = type;
    }

    // Setter for type
    public void setType(String string) {
        if (string != null) {
            this.type = string;
        }
    }

    // Setter for login state
    public void setLogstate(boolean l) {
        this.logged = l;
    }

    // Getter for login state
    public boolean getLogstate() {
        return logged;
    }

    // Setter for username
    public void setUserName(String string) {
        if (string != null) {
            this.username = string;
        }
    }

    // Setter for password
    public void setPass(String string2) {
        if (string2 != null) {
            this.password = string2;
        }
    }

    // Method to login by matching username and password
    public void loginCH(String string, String string2) {
        if (string != null && string2 != null) {
            logged = string.equals(username) && string2.equals(password);
            setLogstate(logged);

            // Log only if login was successful or failed
            if (logged) {
                logger.debug("Login successful for user: {}", username);
            } else {
                logger.warn("Login failed for user: {}", username);
            }
        } else {
            logger.warn("Username or password cannot be null");
        }
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Method to add a user to the list of users if logged in
    public static void adduser(User l) {
        // Only proceed if the user is logged in
        if (l != null && l.getLogstate()) {
            users1.add(l);
            // Log only if adding the user was successful
            logger.debug("User '{}' added successfully.", l.getUsername());
        } else if (l != null) {
            logger.warn("You should login first to add a user.");
        }
    }

    // Method to check if a user is registered
    public boolean isRegest(String string) {
        boolean result = false;
        if (string != null) {
            boolean found = false;
            // Iterate only if necessary (string is not null)
            for (User user : users1) {
                if (user.getUsername().equals(string)) {
                    found = true;
                    break; // No need to check further if user is found
                }
            }

            // Log only if the result is meaningful
            if (found) {
                logger.debug("User '{}' is already registered.", string);
                result = false; // User is already registered
            } else {
                logger.debug("User '{}' is not registered.", string);
                result = true; // User is not registered
            }
        } else {
            logger.warn("Username cannot be null");
        }
        return result;  // Return true if the user is not registered, false otherwise
    }

    // Setter for user ID
    public void setId(String string) {
        if (string != null) {
            this.id = string;
        }
    }

    // Setter for phone number
    public void setPhone(String string) {
        if (string != null) {
            this.phone = string;
        }
    }

    // Setter for address
    public void setAddress(String string) {
        if (string != null) {
            this.address = string;
        }
    }
}
