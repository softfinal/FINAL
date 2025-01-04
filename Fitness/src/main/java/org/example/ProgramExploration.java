package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProgramExploration {

    private static final String PROGRAM_TITLE = "Program Title: ";
    private static final String PROGRAMS_TXT = "C:/Users/Hp Zbook/git/repository3/Fitness/target/programs.txt";
    private static final Logger logger = Logger.getLogger(ProgramExploration.class.getName());

    public static class Program {
        private String name;
        private String difficultyLevel;
        private String focusArea;
        private boolean isFullyBooked;
        private String schedule;

        public Program(String name, String difficultyLevel, String focusArea, String schedule) {
            this.name = name;
            this.difficultyLevel = difficultyLevel;
            this.focusArea = focusArea;
            this.schedule = schedule;
            this.isFullyBooked = false; // Initially not booked
        }

        public String getName() {
            return name;
        }

        public String getDifficultyLevel() {
            return difficultyLevel;
        }

        public String getFocusArea() {
            return focusArea;
        }

        public boolean isFullyBooked() {
            return isFullyBooked;
        }

        public String getSchedule() {
            return schedule;
        }

        public void enroll() {
            if (!isFullyBooked) {
                this.isFullyBooked = true; // Mark as booked only if not already booked
                logger.info("Enrolled in program: " + name);
            } else {
                logger.warning("Program " + name + " is already fully booked.");
            }
        }

        public String getProgramDetails() {
            return "Program: " + name + ", Difficulty: " + difficultyLevel + ", Focus: " + focusArea + ", Schedule: " + schedule;
        }
    }

    public void listAvailablePrograms() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PROGRAMS_TXT))) {
            String line;
            boolean hasValidData = false;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // Log only if the program data is valid
                if (data.length >= 3 && data[0] != null && !data[0].isEmpty()) {
                    logger.info(String.format("%s %s, Difficulty Level: %s, Focus Area: %s", PROGRAM_TITLE, data[0], data[1], data[2]));
                    hasValidData = true;
                } else {
                    logger.warning("Program data is incomplete or invalid: " + line);
                }
            }
            if (!hasValidData) {
                logger.warning("No valid programs available to display.");
            }
        } catch (IOException e) {
            logger.severe("Error reading the programs file: " + e.getMessage());
        }
    }

    public static class Client {
        private String name;
        private List<Program> enrolledPrograms;

        public Client(String name) {
            this.name = name;
            this.enrolledPrograms = new ArrayList<>();
        }

        public void filterPrograms(String difficultyLevel, String focusArea) {
            try (BufferedReader reader = new BufferedReader(new FileReader(PROGRAMS_TXT))) {
                boolean found = false;
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[1].equals(difficultyLevel) && data[2].equals(focusArea)) {
                        logger.info(String.format("%s %s, Difficulty Level: %s, Focus Area: %s", PROGRAM_TITLE, data[0], data[1], data[2]));
                        found = true;
                    }
                }
                if (!found) {
                    logger.warning("No programs found for the specified criteria.");
                }
            } catch (IOException e) {
                logger.severe("Error reading the programs file: " + e.getMessage());
            }
        }

        public void viewProgramDetails(String programName) {
            try (BufferedReader reader = new BufferedReader(new FileReader(PROGRAMS_TXT))) {
                boolean found = false;
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[0].equals(programName)) {
                        logger.info(String.format("%s %s", PROGRAM_TITLE, data[0]));
                        logger.info("Difficulty Level: " + data[1]);
                        logger.info("Focus Area: " + data[2]);
                        logger.info("Schedule: " + data[3]);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    logger.warning("Program with name " + programName + " not found.");
                }
            } catch (IOException e) {
                logger.severe("Error reading the programs file: " + e.getMessage());
            }
        }

        public void enrollInProgram(String clientId, String programName) {
            if (clientId != null && !clientId.isEmpty() && programName != null && !programName.isEmpty()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("subscriptions.txt", true))) {
                    writer.write(clientId + "," + programName + ",Enrolled");
                    writer.newLine();
                    logger.info("Successfully enrolled in program: " + programName);
                } catch (IOException e) {
                    logger.severe("Error enrolling in the program: " + e.getMessage());
                }
            } else {
                logger.warning("Client ID or Program Name is invalid, cannot enroll.");
            }
        }

        public void viewEnrolledPrograms(String clientId) {
            if (clientId != null && !clientId.isEmpty()) {
                try (BufferedReader reader = new BufferedReader(new FileReader("subscriptions.txt"))) {
                    boolean found = false;
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] data = line.split(",");
                        if (data[0].equals(clientId)) {
                            logger.info("Program Name: " + data[1] + " - Status: " + data[2]);
                            found = true;
                        }
                    }
                    if (!found) {
                        logger.warning("No enrolled programs found for client: " + clientId);
                    }
                } catch (IOException e) {
                    logger.severe("Error reading enrolled programs: " + e.getMessage());
                }
            } else {
                logger.warning("Invalid client ID provided.");
            }
        }
    }

    public void viewProgramSchedule(String programName) {
        if (programName != null && !programName.isEmpty()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(PROGRAMS_TXT))) {
                boolean found = false;
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[0].equals(programName)) {
                        logger.info(String.format("Schedule for %s: %s", data[0], data[3]));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    logger.warning("Schedule not found for program: " + programName);
                }
            } catch (IOException e) {
                logger.severe("Error reading the programs file: " + e.getMessage());
            }
        } else {
            logger.warning("Invalid program name provided.");
        }
    }
}
