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
    
    // Method for clients to submit feedback
    public void submitFeedback(String clientId, String programId, String feedback) {
        try {
            File file = new File(FEEDBACK_FILE_PATH);
            boolean isFileCreated = file.createNewFile();
            logFileCreation(isFileCreated);
            writeFeedbackToFile(clientId, programId, feedback, file);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while submitting feedback.", e);
        }
    }

    private void logFileCreation(boolean isFileCreated) {
        if (logger.isLoggable(Level.INFO)) {
            if (isFileCreated) {
                logger.info(String.format("Feedback file created successfully at: %s", FEEDBACK_FILE_PATH));
            } else {
                logger.info(String.format("Feedback file already exists at: %s", FEEDBACK_FILE_PATH));
            }
        }
    }

    private void writeFeedbackToFile(String clientId, String programId, String feedback, File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(String.format("%s,%s,%s,%s,Pending", UUID.randomUUID(), clientId, programId, feedback));
            writer.newLine();
            logFeedbackSubmission();
        }
    }

    private void logFeedbackSubmission() {
        if (logger.isLoggable(Level.INFO)) {
            logger.info("Your feedback has been submitted and is pending approval.");
        }
    }

    // Method to allow clients to view their feedback and its status
    public List<String> viewMyFeedback(String clientId) {
        List<String> feedbackList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FEEDBACK_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processFeedbackLine(clientId, feedbackList, line);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while retrieving feedback.", e);
        }
        return feedbackList;
    }

    private void processFeedbackLine(String clientId, List<String> feedbackList, String line) {
        String[] data = line.split(",");
        if (data[1].equals(clientId)) {
            feedbackList.add(formatFeedback(data));
        }
    }

    private String formatFeedback(String[] data) {
        return String.format("Feedback for program %s: %s (Status: %s)", data[2], data[3], data[4]);
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
        String clientId = null;
        boolean feedbackFound = false;
        StringBuilder updatedContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(FEEDBACK_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(feedbackId)) {
                    feedbackFound = true;
                    clientId = data[1];
                    updateFeedbackStatus(data, approve);
                }
                updatedContent.append(String.join(",", data)).append("\n");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while reviewing feedback.", e);
        }

        if (feedbackFound) {
            updatedContentToWrite = updatedContent;
            return clientId;
        } else {
            logFeedbackNotFound(feedbackId);
            return null;
        }
    }

    private void updateFeedbackStatus(String[] data, boolean approve) {
        data[4] = approve ? "Approved" : "Rejected";
        logFeedbackApprovalRejection(data[0], approve);
    }

    private void logFeedbackApprovalRejection(String feedbackId, boolean approve) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format("Feedback ID %s has been %s.", feedbackId, approve ? "approved" : "rejected"));
        }
    }

    private void logFeedbackNotFound(String feedbackId) {
        if (logger.isLoggable(Level.WARNING)) {
            logger.warning(String.format("Feedback ID %s not found.", feedbackId));
        }
    }

    private void writeUpdatedFeedback() {
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
        if (logger.isLoggable(Level.INFO)) {
            writeNotificationToFile(clientId, feedbackId, approve);
        }
    }

    private void writeNotificationToFile(String clientId, String feedbackId, boolean approve) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOTIFICATIONS_FILE_PATH, true))) {
            writer.write(String.format("%s,Feedback ID %s has been %s.", clientId, feedbackId, approve ? "approved" : "rejected"));
            writer.newLine();
            logNotificationSent(clientId, feedbackId, approve);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while notifying the client.", e);
        }
    }

    private void logNotificationSent(String clientId, String feedbackId, boolean approve) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format("Client %s has been notified about the feedback review of ID %s. Status: %s.",
                    clientId, feedbackId, approve ? "Approved" : "Rejected"));
        }
    }

    // Method for clients to view their notifications
    public List<String> viewNotifications(String clientId) {
        List<String> notifications = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(NOTIFICATIONS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processNotificationLine(clientId, notifications, line);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while retrieving notifications.", e);
        }
        return notifications;
    }

    private void processNotificationLine(String clientId, List<String> notifications, String line) {
        String[] data = line.split(",");
        if (data[0].equals(clientId)) {
            notifications.add(data[1]);
        }
    }
}
