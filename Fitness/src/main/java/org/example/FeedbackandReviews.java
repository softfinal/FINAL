package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeedbackandReviews {

    private static final Logger logger = Logger.getLogger(FeedbackandReviews.class.getName());

    // Customizable feedback file path from environment variable or default
    private static final String FEEDBACK_FILE_PATH = System.getenv("FEEDBACK_FILE_PATH") != null
            ? System.getenv("FEEDBACK_FILE_PATH")
            : "C:/Users/HP ZBook/git/repository3/fitness/target/feedback.txt"; // Default path

    private static final String NOTIFICATIONS_FILE_PATH = "notifications.txt";
    private StringBuilder updatedContentToWrite = new StringBuilder();

    // Method for clients to submit feedback
    public void submitFeedback(String clientId, String programId, String feedback) {
        try {
            File file = new File(FEEDBACK_FILE_PATH);
            boolean isFileCreated = file.createNewFile();

            // Log only when the file is created or already exists
            if (isFileCreated && logger.isLoggable(Level.INFO)) {
                logger.info(String.format("Feedback file created successfully at: %s", FEEDBACK_FILE_PATH));
            } else if (logger.isLoggable(Level.INFO)) {
                logger.info(String.format("Feedback file already exists at: %s", FEEDBACK_FILE_PATH));
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(String.format("%s,%s,%s,%s,Pending", UUID.randomUUID(), clientId, programId, feedback));
                writer.newLine();
                if (logger.isLoggable(Level.INFO)) {
                    logger.info("Your feedback has been submitted and is pending approval.");
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while submitting feedback.", e);
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
                    String feedback = String.format("Feedback for program %s: %s (Status: %s)", data[2], data[3], data[4]);
                    feedbackList.add(feedback);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while retrieving feedback.", e);
        }
        return feedbackList;
    }

    // Method for admin to approve or reject feedback and notify clients
    public void reviewFeedback(String feedbackId, boolean approve) {
        String clientId = findAndUpdateFeedback(feedbackId, approve);
        if (clientId != null) {
            writeUpdatedFeedback();
            notifyClient(clientId, feedbackId, approve);
        }
    }

    private String findAndUpdateFeedback(String feedbackId, boolean approve) {
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
                    if (logger.isLoggable(Level.INFO)) {
                        logger.info(String.format("Feedback ID %s has been %s.", feedbackId, approve ? "approved" : "rejected"));
                    }
                }
                updatedContent.append(String.join(",", data)).append("\n");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while reviewing feedback.", e);
        }

        if (!feedbackFound && logger.isLoggable(Level.WARNING)) {
            logger.warning(String.format("Feedback ID %s not found.", feedbackId));
            return null;
        }

        updatedContentToWrite = updatedContent;
        return clientId;
    }

    private void writeUpdatedFeedback() {
        // Write updated content only if feedback has been found and updated
        if (updatedContentToWrite.length() > 0) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FEEDBACK_FILE_PATH))) {
                writer.write(updatedContentToWrite.toString());
            } catch (IOException e) {
                logger.log(Level.SEVERE, "An error occurred while updating feedback.", e);
            }
        }
    }

    // Method to notify the client when feedback is approved or rejected
    private void notifyClient(String clientId, String feedbackId, boolean approve) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOTIFICATIONS_FILE_PATH, true))) {
            writer.write(String.format("%s,Feedback ID %s has been %s.", clientId, feedbackId, approve ? "approved" : "rejected"));
            writer.newLine();
            // Log notification to client only if feedback was updated
            if (logger.isLoggable(Level.INFO)) {
                logger.info(String.format("Client %s has been notified about the feedback review of ID %s. Status: %s.",
                        clientId, feedbackId, approve ? "Approved" : "Rejected"));
            }
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
            logger.log(Level.SEVERE, "An error occurred while retrieving notifications.", e);
        }
        return notifications;
    }
}
