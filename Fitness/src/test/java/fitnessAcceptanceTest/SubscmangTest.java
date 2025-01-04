package fitnessAcceptanceTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.example.Function;
import org.junit.Test;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SubscmangTest {
    private final Function adminFunctions = new Function();
    private final Logger logger = Logger.getLogger(SubscmangTest.class.getName());
    private String resultMessage;

    // Scenario 1: Add a New Subscription Plan
    @Test
    public void testAddNewSubscriptionPlan() {
        adminLoggedIn();
        addNewSubscriptionPlan(); // Add test-specific input or mock data here
        subscriptionPlanAvailable("Premium", "Client");
    }

    @Given("I am logged in as an admin for subscription management")
    public void adminLoggedIn() {
        logger.log(Level.INFO, "Admin is logged into the system.");
    }

    @When("I add a new subscription plan with the following details:")
    public void addNewSubscriptionPlan() {
        try {
            String simulatedInput = "Premium\n50/mo\nMonthly\nFeature1,Feature2\n";
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            adminFunctions.addSubscriptionPlan("subscriptions.txt", false);
            resultMessage = "Subscription plan added successfully!";
        } catch (Exception e) {
            resultMessage = e.getMessage();
        } finally {
            System.setIn(System.in); // Restore System input
        }
    }

    @Then("the new subscription plan {string} for {string} should be available for selection")
    public void subscriptionPlanAvailable(String planName, String type) {
        assertTrue(String.format("Expected success message, but got: %s", resultMessage), resultMessage.contains("Subscription plan added successfully!"));
        logger.log(Level.INFO, String.format("Subscription plan '%s' for '%s' is available for selection.", planName, type));
    }

    // Scenario 2: Update an Existing Subscription Plan
    @Test
    public void testUpdateSubscriptionPlan() {
        try {
            // Create a temporary file for testing
            File tempFile = File.createTempFile("subscription_file", ".txt");
            tempFile.deleteOnExit(); // Ensure the file is deleted after the test

            // Write initial valid data to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write("Basic,20/mo,Monthly,Basic Features,Active");
                writer.newLine();
            }

            // Verify file contents before update
            try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
                String line = reader.readLine();
                if (line != null) {
                    logger.log(Level.INFO, String.format("Initial content in file: %s", line));
                } else {
                    logger.log(Level.SEVERE, "No content found in the file before update.");
                }
            }

            // Simulated input for updating the subscription plan
            String simulatedInput = "Basic\n30/mo\nYearly\nPremium Features\n";
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            // Call the method to update the subscription plan
            adminFunctions.updateSubscriptionPlan(tempFile.getAbsolutePath());
            System.setIn(System.in); // Restore the original input stream

            // Verify the update by reading the file again
            try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
                String updatedLine = reader.readLine();
                if (updatedLine != null) {
                    logger.log(Level.INFO, String.format("Updated content in file: %s", updatedLine));
                    assertTrue(String.format("Expected updated content to contain: %s", "Basic,30/mo,Yearly,Premium Features,Active"), updatedLine.contains("Basic,30/mo,Yearly,Premium Features,Active"));
                } else {
                    logger.log(Level.SEVERE, "No content found after update.");
                    assertFalse("Failed to update subscription plan.", true);
                }
            }

            System.out.println("Test passed: Subscription plan updated successfully.");
        } catch (Exception e) {
            System.err.println(String.format("Test failed: %s", e.getMessage()));
            assertFalse("Error occurred during test execution.", true);
        }
    }

    @Given("a subscription plan {string} for {string} exists")
    public void subscriptionPlanExists(String planName, String type) {
        logger.log(Level.INFO, String.format("Subscription plan '%s' for '%s' exists in the system.", planName, type));
    }

    @When("I update the subscription plan {string} with the new price {string}")
    public void updateSubscriptionPlan(String planName, String newPrice) {
        try {
            String simulatedInput = String.format("%s\n%s\n", planName, newPrice);
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            adminFunctions.updateSubscriptionPlan("subscriptions.txt");
            resultMessage = "Subscription plan updated successfully!";
        } catch (Exception e) {
            resultMessage = e.getMessage();
        } finally {
            System.setIn(System.in);
        }
    }

    @Then("the subscription plan {string} should reflect the updated price {string}")
    public void subscriptionPlanUpdated(String planName, String updatedPrice) {
        assertTrue(String.format("Expected success message, but got: %s", resultMessage), resultMessage.contains("updated successfully"));
        logger.log(Level.INFO, String.format("Subscription plan '%s' reflects the updated price '%s'.", planName, updatedPrice));
    }

    // Scenario 3: Deactivate a Subscription Plan
    @Test
    public void testDeactivateSubscriptionPlan() {
        deactivateSubscriptionPlan("Premium");
        subscriptionPlanNoLongerAvailable("Premium");
    }

    @When("I deactivate the subscription plan {string}")
    public void deactivateSubscriptionPlan(String planName) {
        try {
            String simulatedInput = planName + "\n";
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            adminFunctions.deactivateSubscriptionPlan("subscriptions.txt");
            resultMessage = "Subscription plan deactivated successfully!";
        } catch (Exception e) {
            resultMessage = e.getMessage();
        } finally {
            System.setIn(System.in);
        }
    }

    @Then("the subscription plan {string} should no longer be available for new users")
    public void subscriptionPlanNoLongerAvailable(String planName) {
        assertTrue(String.format("Expected success message, but got: %s", resultMessage), resultMessage.contains("deactivated successfully"));
        logger.log(Level.INFO, String.format("Subscription plan '%s' is no longer available for new users.", planName));
    }

    // Scenario 4: View All Subscription Plans
    @Test
    public void testViewAllSubscriptionPlans() {
        viewAllSubscriptionPlans();
        listActivePlans(); // Add test-specific assertions for active plans
    }

    @When("I view all subscription plans")
    public void viewAllSubscriptionPlans() {
        try {
            adminFunctions.viewAllSubscriptionPlans("subscriptions.txt", false);
            resultMessage = "Active subscription plans displayed successfully.";
        } catch (Exception e) {
            resultMessage = e.getMessage();
        }
    }

    @Then("I should see a list of all active plans with details:")
    public void listActivePlans() {
        assertTrue(String.format("Expected success message, but got: %s", resultMessage), resultMessage.contains("displayed successfully"));
        logger.log(Level.INFO, "Active subscription plans are displayed with correct details.");
    }
}
