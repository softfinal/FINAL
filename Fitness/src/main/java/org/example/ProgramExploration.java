package fitt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgramExploration {

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
        try (BufferedReader reader = new BufferedReader(new FileReader("programs.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println("Program Title: " + data[0] + ", Difficulty Level: " + data[1] + ", Focus Area: " + data[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            try (BufferedReader reader = new BufferedReader(new FileReader("programs.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[1].equals(difficultyLevel) && data[2].equals(focusArea)) {
                        System.out.println("Program Title: " + data[0] + ", Difficulty Level: " + data[1] + ", Focus Area: " + data[2]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void viewProgramDetails(String programName) {
            try (BufferedReader reader = new BufferedReader(new FileReader("programs.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[0].equals(programName)) {
                        System.out.println("Program Title: " + data[0]);
                        System.out.println("Difficulty Level: " + data[1]);
                        System.out.println("Focus Area: " + data[2]);
                        System.out.println("Schedule: " + data[3]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void enrollInProgram(String clientId, String programName) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("subscriptions.txt", true))) {
                writer.write(clientId + "," + programName + ",Enrolled");
                writer.newLine();
                System.out.println("You have successfully enrolled in the program.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void viewEnrolledPrograms(String clientId) {
            try (BufferedReader reader = new BufferedReader(new FileReader("subscriptions.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[0].equals(clientId)) {
                        System.out.println("Program Name: " + data[1] + " - Status: " + data[2]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void viewProgramSchedule(String programName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("programs.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(programName)) {
                    System.out.println("Schedule for " + data[0] + ": " + data[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}