package fitnessAcceptanceTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FeedbackandReviews {

    // Method for clients to submit feedback
    public void submitFeedback(String clientId, String programId, String feedback) {
        try {
            File file = new File("feedback.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(UUID.randomUUID() + "," + clientId + "," + programId + "," + feedback + ",Pending");
                writer.newLine();
                System.out.println("Your feedback has been submitted and is pending approval.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to allow clients to view their feedback and its status
    public List<String> viewMyFeedback(String clientId) {
        List<String> feedbackList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("feedback.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(clientId)) {
                    String feedback = "Feedback for program " + data[2] + ": " + data[3] + " (Status: " + data[4] + ")";
                    feedbackList.add(feedback);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    // Method for admin to approve or reject feedback and notify clients
    public void reviewFeedback(String feedbackId, boolean approve) {
        StringBuilder updatedContent = new StringBuilder();
        String clientId = null;
        boolean feedbackFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("feedback.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(feedbackId)) {
                    feedbackFound = true;
                    clientId = data[1];
                    data[4] = approve ? "Approved" : "Rejected";
                    System.out.println("Feedback ID " + feedbackId + " has been " + (approve ? "approved." : "rejected."));
                }
                updatedContent.append(String.join(",", data)).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!feedbackFound) {
            System.out.println("Feedback ID " + feedbackId + " not found.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedback.txt"))) {
            writer.write(updatedContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (clientId != null) {
            notifyClient(clientId, feedbackId, approve);
        }
    }

    // Method to notify the client when feedback is approved or rejected
    private void notifyClient(String clientId, String feedbackId, boolean approve) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("notifications.txt", true))) {
            writer.write(clientId + ",Feedback ID " + feedbackId + " has been " + (approve ? "approved." : "rejected."));
            writer.newLine();
            System.out.println("Client " + clientId + " has been notified about the feedback review.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method for clients to view their notifications
    public List<String> viewNotifications(String clientId) {
        List<String> notifications = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("notifications.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(clientId)) {
                    notifications.add(data[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notifications;
    }
}
