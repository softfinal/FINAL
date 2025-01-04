package org.example;

import java.io.*;
import java.util.*;
import java.util.logging.*;

public class ProgressTracking {

    private static final String MILESTONES_TXT = "milestones.txt";
    private static final Logger logger = Logger.getLogger(ProgressTracking.class.getName());

    // A class to represent the progress milestones for the client
    public static class FitnessMilestone {
        private double weight;
        private double bmi;
        private String date;

        public FitnessMilestone(double weight, double bmi, String date) {
            this.weight = weight;
            this.bmi = bmi;
            this.date = date;
        }

        public double getWeight() {
            return weight;
        }

        public double getBmi() {
            return bmi;
        }

        public String getDate() {
            return date;
        }

        @Override
        public String toString() {
            return "Date: " + date + ", Weight: " + weight + "kg, BMI: " + bmi;
        }
    }

    // A class to represent the achievements and badges for the client
    public static class Achievement {
        private String name;
        private String description;

        public Achievement(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return name + ": " + description;
        }
    }

    // Method to track fitness milestones (weight, BMI, and date)
    public void addMilestone(String clientId, double weight, double bmi, String date) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MILESTONES_TXT, true))) {
            writer.write(clientId + "," + weight + "," + bmi + "," + date);
            writer.newLine();
            logger.info("Milestone recorded for client " + clientId);
        } catch (IOException e) {
            logger.severe("Error writing milestone: " + e.getMessage());
        }
    }

    // Method to view progress milestones for a client
    public List<String> viewMilestones(String clientId) {
        List<String> milestones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(MILESTONES_TXT))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(clientId)) {
                    milestones.add(new FitnessMilestone(Double.parseDouble(data[1]),
                            Double.parseDouble(data[2]), data[3]).toString());
                }
            }
        } catch (IOException e) {
            logger.severe("Error reading milestones: " + e.getMessage());
        }
        return milestones;
    }

    // Method to track attendance for a client
    public void trackAttendance(String clientId, String programId, boolean attended, String date) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("attendance.txt", true))) {
            writer.write(clientId + "," + programId + "," + attended + "," + date);
            writer.newLine();
            logger.info("Attendance recorded for client " + clientId + " on " + date);
        } catch (IOException e) {
            logger.severe("Error writing attendance: " + e.getMessage());
        }
    }

    // Method to view attendance for a client
    public List<String> viewAttendance(String clientId) {
        List<String> attendanceList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("attendance.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].trim().equals(clientId)) { // Ensure exact match for clientId
                    attendanceList.add("Program ID: " + data[1].trim() + ", Attended: " + data[2].trim() + ", Date: " + data[3].trim());
                }
            }
        } catch (IOException e) {
            logger.severe("Error reading attendance: " + e.getMessage());
        }
        return attendanceList;
    }

    // Method to add achievements or badges for clients
    public void addAchievement(String clientId, String achievementName, String description) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("achievements.txt", true))) {
            writer.write(clientId + "," + achievementName + "," + description);
            writer.newLine();
            logger.info("Achievement '" + achievementName + "' added for client " + clientId);
        } catch (IOException e) {
            logger.severe("Error writing achievement: " + e.getMessage());
        }
    }

    // Method to remove weight tracking for a client
    public void removeWeight(String clientId) {
        File tempFile = new File("milestones_temp.txt");
        File originalFile = new File(MILESTONES_TXT);

        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (!data[0].trim().equals(clientId)) { // Ensure exact match for clientId
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            logger.severe("Error during milestone removal: " + e.getMessage());
        }

        // Replace the original file with the updated file
        if (originalFile.delete() && tempFile.renameTo(originalFile)) {
            logger.info("Weight removed successfully for client ID: " + clientId);
        } else {
            logger.severe("Failed to update milestones file.");
        }
    }

    public static void main(String[] args) {
        ProgressTracking progressTracking = new ProgressTracking();
        
        // Test the methods
        progressTracking.addMilestone("123", 75.5, 23.5, "2025-01-04");
        progressTracking.addAchievement("123", "First Milestone", "Completed first fitness milestone.");
    }
}
