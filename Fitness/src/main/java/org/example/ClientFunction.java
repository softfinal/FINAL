package org.example;

import java.io.*;
import java.util.*;

public class ClientFunction {
	private static final String PROGRAM_TITLE_PREFIX = "Program Title: ";

	static final Printing printing = new Printing();
	static final Scanner scanner = new Scanner(System.in);

    public void clientDashboard(String clientId) throws IOException {
        printing.printSomething("Welcome to the Client Dashboard, " + clientId + "!");
        while (true) {
            printing.printSomething("""
                ---- Client Options ----
                | 1. Account Management  |
                | 2. Program Exploration  |
                | 3. Progress Tracking    |
                | 4. Feedback and Reviews |
                | 5. Log Out              |
                -------------------------
                Enter your choice:
            """);

            int choice = getValidChoice(1, 5);
            switch (choice) {
                case 1 -> manageAccount(clientId);
                case 2 -> explorePrograms(clientId);
                case 3 -> trackProgress(clientId);
                case 4 -> giveFeedback(clientId);
                case 5 -> {
                    printing.printSomething("Logging out...");
                    return;
                }
                default -> printing.printSomething("Invalid choice. Please try again.");
            }
        }
    }

    private void manageAccount(String clientId) throws IOException {
        printing.printSomething("""
            ---- Account Management ----
            | 1. Edit Personal Details  |
            | 2. Edit Dietary Preferences|
            | 3. Back                    |
            ------------------------------
            Enter your choice:
        """);

        int choice = getValidChoice(1, 3);
        switch (choice) {
            case 1 -> editPersonalDetails(clientId);
            case 2 -> editDietaryPreferences(clientId);
            default -> printing.printSomething("Returning to Client Dashboard...");
        }
    }

    private void editPersonalDetails(String clientId) throws IOException {
        printing.printSomething("Enter your new age: ");
        int age = scanner.nextInt();
        scanner.nextLine();  // Clear buffer
        printing.printSomething("Enter your new fitness goals: ");
        String goals = scanner.nextLine();

        String clientDetails = clientId + "," + age + "," + goals;
        updateFile("C:\\Users\\Hp Zbook\\git\\Fitnes\\Fitness\\target\\clients.txt", clientDetails, clientId);
        printing.printSomething("Personal details updated successfully!");
    }

    private void editDietaryPreferences(String clientId) throws IOException {
    	String clientIdd = clientId;
        printing.printSomething("Enter your dietary preferences or restrictions: ");
        String dietaryPreferences = scanner.nextLine();

        String clientDetails = clientIdd + "," + dietaryPreferences;
        updateFile("C:\\Users\\Hp Zbook\\git\\Fitnes\\Fitness\\target\\clients.txt", clientDetails, clientIdd);
        printing.printSomething("Dietary preferences updated successfully!");
    }

    private void explorePrograms(String clientId) throws IOException {
        printing.printSomething("""
            ---- Program Exploration ----
            | 1. Browse by Difficulty Level  |
            | 2. Browse by Focus Area        |
            | 3. View All Programs           |
            | 4. Back                        |
            ------------------------------
            Enter your choice:
        """);

        int choice = getValidChoice(1, 4);
        switch (choice) {
            case 1 -> browseByDifficultyLevel();
            case 2 -> browseByFocusArea();
            case 3 -> viewAllPrograms();
            default -> printing.printSomething("Returning to Client Dashboard...");
        }
    }

    private void browseByDifficultyLevel() throws IOException {
        printing.printSomething("""
            ---- Difficulty Levels ----
            | 1. Beginner   |
            | 2. Intermediate |
            | 3. Advanced    |
            ------------------------------
            Enter your choice:
        """);

        int choice = getValidChoice(1, 3);
        String level = switch (choice) {
            case 1 -> "Beginner";
            case 2 -> "Intermediate";
            case 3 -> "Advanced";
            default -> "";
        };

        printProgramsByFilter("difficulty", level);
    }

    private void browseByFocusArea() throws IOException {
        printing.printSomething("Enter focus area (e.g., weight loss, muscle building, flexibility): ");
        String focusArea = scanner.nextLine();
        printProgramsByFilter("focus", focusArea);
    }

    private void printProgramsByFilter(String filterType, String filterValue) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\\\\\\\Users\\\\\\\\Hp Zbook\\\\\\\\git\\\\\\\\Fitnes\\\\\\\\Fitness\\\\\\\\target\\\\\\\\programs.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (filterType.equals("difficulty") && fields[3].equalsIgnoreCase(filterValue)) {
                    printing.printSomething(PROGRAM_TITLE_PREFIX + fields[1] + " - Difficulty: " + fields[3]);
                } else if (filterType.equals("focus") && fields[4].equalsIgnoreCase(filterValue)) {
                    printing.printSomething(PROGRAM_TITLE_PREFIX + fields[1] + " - Focus Area: " + fields[4]);
                }
            }
        }
    }

    private void viewAllPrograms() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\\\Users\\\\Hp Zbook\\\\git\\\\Fitnes\\\\Fitness\\\\target\\\\programs.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                printing.printSomething(PROGRAM_TITLE_PREFIX + fields[1] + " - Duration: " + fields[2] + " - Price: " + fields[5]);
            }
        }
    }

    private void trackProgress(String clientId) throws IOException {
        printing.printSomething("Fetching your progress...");

        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Hp Zbook\\git\\Fitnes\\Fitness\\target\\programs.txt"))) {
            String line;
            boolean progressFound = false;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[0].equals(clientId)) {
                    progressFound = true;
                    printing.printSomething("Your Progress: " + fields[2]);
                }
            }
            if (!progressFound) {
                printing.printSomething("No progress data available.");
            }
        }
    }

    private void giveFeedback(String clientId) throws IOException {
        printing.printSomething("Enter program title for feedback: ");
        String programTitle = scanner.nextLine();

        printing.printSomething("Enter your feedback for this program: ");
        String feedback = scanner.nextLine();

        String feedbackData = clientId + "," + programTitle + "," + feedback;
        appendToFile("C:\\Users\\Hp Zbook\\git\\Fitnes\\Fitness\\target\\feedback.txt", feedbackData);
        printing.printSomething("Feedback submitted successfully!");
    }

    private void updateFile(String filePath, String newDetails, String clientId) throws IOException {
        List<String> updatedClients = new ArrayList<>();
        boolean clientFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[0].equals(clientId)) {
                    clientFound = true;
                    updatedClients.add(newDetails);
                } else {
                    updatedClients.add(line);
                }
            }
        }

        if (clientFound) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String updatedLine : updatedClients) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            }
            printing.printSomething("Account updated successfully!");
        } else {
            printing.printSomething("Client not found!");
        }
    }

    private void appendToFile(String filePath, String data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(data);
            writer.newLine();
        }
    }

    private int getValidChoice(int min, int max) {
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                if (choice >= min && choice <= max) {
                    return choice;
                }
                printing.printSomething("Invalid choice. Please select between " + min + " and " + max + ".");
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Clear invalid input
                printing.printSomething("Invalid input. Please enter a number.");
            }
        }
    }
}
