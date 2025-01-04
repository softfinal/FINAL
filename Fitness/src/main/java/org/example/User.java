package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class User {

    // Logger instance for logging messages
    private static final Logger logger = Logger.getLogger(User.class.getName());

    // User attributes
    String password;
    String username;
    String address;
    String id;
    String phone;
    String type;
    private boolean logged;
    protected static final List<User> users1 = new ArrayList<>();

    // Default constructor
    public User() {
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
        this.type = string;
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
        this.username = string;
    }

    // Setter for password
    public void setPass(String string2) {
        this.password = string2;
    }

    // Method to login by matching username and password
    public void loginCH(String string, String string2) {
        if (string != null && string2 != null) {
            logged = string.equals(username) && string2.equals(password);
            setLogstate(logged);

            // Only log the success if login was successful
            if (logged) {
                logger.info(String.format("Login successful for user: %s", username));
            } else {
                logger.warning(String.format("Login failed for user: %s", username));
            }
        } else {
            logger.warning("Username or password cannot be null");
        }
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Method to add a user to the list of users if logged in
    public static void adduser(User l) {
        // Only proceed if the current user is logged in
        User u = new User();
        if (u.getLogstate()) {
            users1.add(l);
            // Log only if the user was successfully added
            logger.info(String.format("User '%s' added successfully.", l.getUsername()));
        } else {
            logger.warning("You should login first");
        }
    }

    // Method to check if a user is registered
    public boolean isRegest(String string) {
        if (string != null) {
            boolean found = false;
            for (User user : users1) {
                if (user.getUsername().equals(string)) {
                    found = true;
                    break; // No need to check further if user is found
                }
            }
            if (found) {
                logger.info(String.format("User '%s' is already registered.", string));
            } else {
                logger.info(String.format("User '%s' is not registered.", string));
            }
            return !found;  // Return true if the user is not registered
        } else {
            logger.warning("Username cannot be null");
            return false;
        }
    }

    // Setter for user ID
    public void setId(String string) {
        this.id = string;
    }

    // Setter for phone number
    public void setPhone(String string) {
        this.phone = string;
    }

    // Setter for address
    public void setAddress(String string) {
        this.address = string;
    }
}
