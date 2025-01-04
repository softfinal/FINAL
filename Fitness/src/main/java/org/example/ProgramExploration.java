package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProgramExploration {

    private static final String S_S_DIFFICULTY_LEVEL_S_FOCUS_AREA_S = "%s %s, Difficulty Level: %s, Focus Area: %s";
    private static final String PROGRAM_TITLE = "Program Title: ";
    private static final String PROGRAMS_TXT = "C:\\Users\\Hp Zbook\\git\\repository3\\Fitness\\target\\programs.txt";
    private static final Logger logger = Logger.getLogger(ProgramExploration.class.getName()); // Logger instance

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
            this.isFullyBooked = true; // After enrollment, mark as booked
        }

        public String getProgramDetails() {
            return "Program: " + name + ", Difficulty: " + difficultyLevel + ", Focus: " + focusArea + ", Schedule: " + schedule;
        }
    }

    public void listAvailablePrograms() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PROGRAMS_TXT))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (logger.isLoggable(Level.INFO)) {
                    logger.info(String.format(S_S_DIFFICULTY_LEVEL_S_FOCUS_AREA_S, PROGRAM_TITLE, data[0], data[1], data[2]));
                }
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
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[1].equals(difficultyLevel) && data[2].equals(focusArea)) {
                        if (logger.isLoggable(Level.INFO)) {
                            logger.info(String.format(S_S_DIFFICULTY_LEVEL_S_FOCUS_AREA_S, PROGRAM_TITLE, data[0], data[1], data[2]));
                        }
                    }
                }
            } catch (IOException e) {
                logger.severe("Error reading the programs file while filtering: " + e.getMessage());
            }
        }

        public void viewProgramDetails(String programName) {
            try (BufferedReader reader = new BufferedReader(new FileReader(PROGRAMS_TXT))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[0].equals(programName)) {
                        if (logger.isLoggable(Level.INFO)) {
                            logger.info(String.format(S_S_DIFFICULTY_LEVEL_S_FOCUS_AREA_S, PROGRAM_TITLE, data[0], data[1], data[2]));
                            logger.info(String.format("Schedule: %s", data[3]));
                        }
                    }
                }
            } catch (IOException e) {
                logger.severe("Error reading the programs file while viewing details: " + e.getMessage());
            }
        }

        public void enrollInProgram(String clientId, String programName) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("subscriptions.txt", true))) {
                writer.write(clientId + "," + programName + ",Enrolled");
                writer.newLine();
                if (logger.isLoggable(Level.INFO)) {
                    logger.info(String.format("Client %s has successfully enrolled in the program %s", clientId, programName));
                }
            } catch (IOException e) {
                logger.severe("Error enrolling in the program: " + e.getMessage());
            }
        }

        public void viewEnrolledPrograms(String clientId) {
            try (BufferedReader reader = new BufferedReader(new FileReader("subscriptions.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[0].equals(clientId)) {
                        if (logger.isLoggable(Level.INFO)) {
                            logger.info(String.format("Program Name: %s - Status: %s", data[1], data[2]));
                        }
                    }
                }
            } catch (IOException e) {
                logger.severe("Error reading the subscriptions file: " + e.getMessage());
            }
        }
    }

    public void viewProgramSchedule(String programName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PROGRAMS_TXT))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(programName)) {
                    if (logger.isLoggable(Level.INFO)) {
                        logger.info(String.format("Schedule for %s: %s", data[0], data[3]));
                    }
                }
            }
        } catch (IOException e) {
            logger.severe("Error reading the programs file while viewing schedule: " + e.getMessage());
        }
    }
}
