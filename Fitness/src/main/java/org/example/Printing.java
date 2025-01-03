package org.example;
import java.util.logging.*;

public class Printing {
    private static final Logger LOGGER = Logger.getLogger(Printing.class.getName());

    // Static block to set up custom logger with color support
    static {
        // Create a console handler with a custom formatter that only prints the message
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter() {
            @Override
            public String format(LogRecord logRecord) {
                String message = super.formatMessage(logRecord);
                return ANSI_CYAN + message + ANSI_RESET; // Default color (Cyan)
            }
        });

        LOGGER.setUseParentHandlers(false); // Disable default handlers
        LOGGER.addHandler(consoleHandler); // Add custom handler
    }

    // Method to print simple message with default formatting
    public void printSomething(String msg) {
        LOGGER.log(Level.INFO, msg);
    }

    // Method to print message with specific color
    public void printInColor(String msg, String color) {
        String formattedMsg = color + msg + ANSI_RESET;
        LOGGER.log(Level.INFO, formattedMsg);
    }

    // Method to get a colored string for later use
    public String getColoredString(String input, String color) {
        return color + input + ANSI_RESET;
    }

    // Define standard color constants for logging
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_MAGENTA = "\u001B[35m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_GRAY = "\u001B[90m";
    // Custom color constants
    public static final String ANSI_ORANGE = "\u001B[38;5;208m";
    public static final String ANSI_PINK = "\u001B[38;5;205m";
    public static final String ANSI_LIME = "\u001B[38;5;154m";

    // Method to color text directly
    public static String colorText(String text, String color) {
        return color + text + ANSI_RESET;
    }

    // Method to print warning messages
    public void printWarning(String message) {
        LOGGER.log(Level.WARNING, "⚠️ WARNING: {0}", message);
    }

    // Method to print error messages
    public void printError(String message) {
        LOGGER.log(Level.SEVERE, "❌ ERROR: {0}", message);
    }

    // Static method to print success messages
    public static void printSuccess(String message) {
        LOGGER.log(Level.INFO, "✅ SUCCESS: {0}", message);
    }
}
