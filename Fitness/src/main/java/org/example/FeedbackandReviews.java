package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeedbackandReviews {

    private static final Logger logger = Logger.getLogger(FeedbackandReviews.class.getName());
    private static final String FEEDBACK_FILE_PATH = "C:/Users/HP ZBook/git/repository3/fitness/target/feedback.txt";
    private static final String NOTIFICATIONS_FILE_PATH = "notifications.txt";

    // Method for clients to submit feedback
    public void submitFeedback(String clientId, String programId, String feedback) {
        try {
            // Define the file path
            File file = new File(FEEDBACK_FILE_PATH);

            // Attempt to create the file and handle the boolean result
            boolean isFileCreated = file.createNewFile();
            if (isFileCreated) {
                logger.info("Feedback file created successfully.");
            } else {
                logger.info("Feedback file already exists.");
            }

            // Write feedback to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(UUID.randomUUID() + "," + clientId + "," + programId + "," + feedback + ",Pending");
                writer.newLine();
                logger.info("Your feedback has been submitted and is pending approval.");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while processing feedback.", e);
        }
    }

    // Method to allow clients to view their feedback and its status
    public List<String> viewMyFeedback(String clientId) {
        List<String> feedbackList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FEEDBACK_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(clientId)) {
                    String feedback = "Feedback for program " + data[2] + ": " + data[3] + " (Status: " + data[4] + ")";
                    feedbackList.add(feedback);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while reading feedback.", e);
        }
        return feedbackList;
    }

    // Method for admin to approve or reject feedback and notify clients
    public void reviewFeedback(String feedbackId, boolean approve) {
        StringBuilder updatedContent = new StringBuilder();
        String clientId = null;
        boolean feedbackFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FEEDBACK_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(feedbackId)) {
                    feedbackFound = true;
                    clientId = data[1];
                    data[4] = approve ? "Approved" : "Rejected";
                    logger.info("Feedback ID " + feedbackId + " has been " + (approve ? "approved." : "rejected."));
                }
                updatedContent.append(String.join(",", data)).append("\n");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while reviewing feedback.", e);
        }

        if (!feedbackFound) {
            logger.warning("Feedback ID " + feedbackId + " not found.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FEEDBACK_FILE_PATH))) {
            writer.write(updatedContent.toString());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while updating feedback.", e);
        }

        if (clientId != null) {
            notifyClient(clientId, feedbackId, approve);
        }
    }

    // Method to notify the client when feedback is approved or rejected
    private void notifyClient(String clientId, String feedbackId, boolean approve) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOTIFICATIONS_FILE_PATH, true))) {
            writer.write(clientId + ",Feedback ID " + feedbackId + " has been " + (approve ? "approved." : "rejected."));
            writer.newLine();
            logger.info("Client " + clientId + " has been notified about the feedback review.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while notifying the client.", e);
        }
    }

    // Method for clients to view their notifications
    public List<String> viewNotifications(String clientId) {
        List<String> notifications = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(NOTIFICATIONS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(clientId)) {
                    notifications.add(data[1]);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while reading notifications.", e);
        }
        return notifications;
    }
}
