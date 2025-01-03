package fitnessAcceptanceTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.example.Function;
import org.junit.Before;
import org.junit.Test;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ContentManagTest {
    private final Function adminFunctions = new Function();
    private final Logger logger = Logger.getLogger(ContentManagTest.class.getName());
    private String resultMessage;

    // This method is executed before each test method
    @Before
    public void setUp() {
        resultMessage = "";
    }

    // Scenario 1: Approve Fitness Tip
    @Test
    public void testApproveFitnessTip() {
        String tipTitle = "Healthy Living Tips";
        fitnessTipPendingApproval(tipTitle);

        iApproveFitnessTip(tipTitle);
        theTipShouldBePubliclyVisible(tipTitle);
    }

    @Test
    public void testRejectFitnessTip() {
        String tipTitle = "Healthy Living Tips";
        String reason = "Not suitable for our platform";
        iRejectFitnessTip(tipTitle, reason);
        theSubmitterShouldReceiveNotification("Tip rejected with reason: " + reason);
        theTipShouldNotBePubliclyVisible(tipTitle);
    }

    @Test
    public void testDeleteFitnessTip() {
        String tipTitle = "Healthy Living Tips";
        fitnessTipPubliclyVisible(tipTitle);

        iDeleteFitnessTip(tipTitle);
        theTipShouldNoLongerBeVisible(tipTitle);
    }

    @Test
    public void testViewFeedbackForFitnessTip() {
        String tipTitle = "Healthy Living Tips";
        thereIsFeedbackOnFitnessTip(tipTitle);

        iViewFeedbackForFitnessTip(tipTitle);
        iShouldSeeAllUserCommentsAndRatingsFor(tipTitle);
    }

    @Test
    public void testRemoveInappropriateFeedback() {
        String tipTitle = "Healthy Living Tips";
        thereIsInappropriateFeedbackOnFitnessTip(tipTitle);

        iRemoveInappropriateFeedback(tipTitle);
        theFeedbackShouldNoLongerBeVisibleUnderTip(tipTitle);
    }

    @Test
    public void testRespondToFeedback() {
        String tipTitle = "Healthy Living Tips";
        String response = "Thank you for your feedback!";

        thereIsFeedbackQuestionOnFitnessTip(tipTitle);
        iRespondToFeedback(response);
        myResponseShouldBeVisibleUnderFeedbackQuestion();
    }

    // Scenario 1: Approve Fitness Tip
    @Given("the fitness tip titled {string} is pending approval")
    public void fitnessTipPendingApproval(String tipTitle) {
        logger.log(Level.INFO, "Fitness tip titled \"" + tipTitle + "\" is pending approval.");
    }

    @When("I approve a submitted fitness tip titled {string}")
    public void iApproveFitnessTip(String tipTitle) {
        try {
            String simulatedInput = "approve\n" + tipTitle + "\n";
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            adminFunctions.approveContent(); // Call the approve content method
            resultMessage = "Tip approved successfully!";
            System.setIn(System.in); // Restore System input
        } catch (Exception e) {
            resultMessage = e.getMessage();
        }
    }

    @Then("the tip {string} should be publicly visible on the platform")
    public void theTipShouldBePubliclyVisible(String tipTitle) {
        assertTrue(resultMessage.contains("Tip approved successfully!"));
        logger.log(Level.INFO, "Tip \"" + tipTitle + "\" is now publicly visible.");
    }

    // Scenario 2: Reject Fitness Tip
    @When("I reject a submitted fitness tip titled {string} with reason {string}")
    public void iRejectFitnessTip(String tipTitle, String reason) {
        try {
            String simulatedInput = "reject\n" + tipTitle + "\n" + reason + "\n";
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            adminFunctions.approveContent(); // Call the reject content method
            resultMessage = "Tip rejected with reason: " + reason;
            System.setIn(System.in); // Restore System input
        } catch (Exception e) {
            resultMessage = e.getMessage();
        }
    }

    @Then("the submitter should receive a notification {string}")
    public void theSubmitterShouldReceiveNotification(String notification) {
        assertTrue(resultMessage.contains(notification));
        logger.log(Level.INFO, "Notification sent to submitter: " + notification);
    }

    @Then("the tip {string} should not be publicly visible")
    public void theTipShouldNotBePubliclyVisible(String tipTitle) {
        assertTrue(resultMessage.contains("Tip rejected with reason"));
        logger.log(Level.INFO, "Tip \"" + tipTitle + "\" is no longer publicly visible.");
    }

    // Scenario 3: Delete Fitness Tip
    @Given("the fitness tip titled {string} is publicly visible")
    public void fitnessTipPubliclyVisible(String tipTitle) {
        logger.log(Level.INFO, "Fitness tip titled \"" + tipTitle + "\" is publicly visible.");
    }

    @When("I delete the fitness tip titled {string}")
    public void iDeleteFitnessTip(String tipTitle) {
        try {
            String simulatedInput = "delete\n" + tipTitle + "\n";
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            adminFunctions.deleteContent(simulatedInput, simulatedInput); // Call the delete content method
            resultMessage = "Tip deleted successfully!";
            System.setIn(System.in); // Restore System input
        } catch (Exception e) {
            resultMessage = e.getMessage();
        }
    }

    @Then("the tip {string} should no longer be visible on the platform")
    public void theTipShouldNoLongerBeVisible(String tipTitle) {
        assertTrue(resultMessage.contains("Tip deleted successfully!"));
        logger.log(Level.INFO, "Tip \"" + tipTitle + "\" has been deleted and is no longer visible.");
    }

    // Scenario 4: View Feedback for a Fitness Tip
    @Given("there is feedback on the fitness tip titled {string}")
    public void thereIsFeedbackOnFitnessTip(String tipTitle) {
        logger.log(Level.INFO, "Feedback exists for fitness tip titled \"" + tipTitle + "\".");
    }

    @When("I view feedback for the fitness tip titled {string}")
    public void iViewFeedbackForFitnessTip(String tipTitle) {
        try {
            String simulatedInput = "view_feedback\n" + tipTitle + "\n";
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            adminFunctions.reviewAndRespondFeedback(); // Call the view feedback method
            resultMessage = "Viewing feedback for " + tipTitle;
            System.setIn(System.in); // Restore System input
        } catch (Exception e) {
            resultMessage = e.getMessage();
        }
    }

    @Then("I should see all user comments and ratings for {string}")
    public void iShouldSeeAllUserCommentsAndRatingsFor(String tipTitle) {
        assertTrue(resultMessage.contains("Viewing feedback for"));
        logger.log(Level.INFO, "Viewing all user feedback for the fitness tip titled \"" + tipTitle + "\".");
    }

    // Scenario 5: Remove Inappropriate Feedback
    @Given("there is inappropriate feedback on the fitness tip titled {string}")
    public void thereIsInappropriateFeedbackOnFitnessTip(String tipTitle) {
        logger.log(Level.INFO, "Inappropriate feedback exists on fitness tip titled \"" + tipTitle + "\".");
    }

    @When("I remove the inappropriate feedback on {string}")
    public void iRemoveInappropriateFeedback(String tipTitle) {
        try {
            String simulatedInput = "remove_feedback\n" + tipTitle + "\n";
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            adminFunctions.removeFeedback(); // Call the remove feedback method
            resultMessage = "Inappropriate feedback removed from " + tipTitle;
            System.setIn(System.in); // Restore System input
        } catch (Exception e) {
            resultMessage = e.getMessage();
        }
    }

    @Then("the feedback should no longer be visible under the {string} tip")
    public void theFeedbackShouldNoLongerBeVisibleUnderTip(String tipTitle) {
        assertTrue(resultMessage.contains("Inappropriate feedback removed"));
        logger.log(Level.INFO, "Inappropriate feedback has been removed from tip titled \"" + tipTitle + "\".");
    }

    // Scenario 6: Respond to Feedback Question
    @Given("there is a feedback question on the fitness tip titled {string}")
    public void thereIsFeedbackQuestionOnFitnessTip(String tipTitle) {
        logger.log(Level.INFO, "There is a feedback question for fitness tip titled \"" + tipTitle + "\".");
    }

    @When("I respond to the feedback with {string}")
    public void iRespondToFeedback(String response) {
        try {
            String simulatedInput = "respond_feedback\n" + response + "\n";
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            adminFunctions.reviewAndRespondFeedback(); // Call the respond to feedback method
            resultMessage = "Response posted successfully!";
            System.setIn(System.in); // Restore System input
        } catch (Exception e) {
            resultMessage = e.getMessage();
        }
    }

    @Then("my response should be visible under the feedback question")
    public void myResponseShouldBeVisibleUnderFeedbackQuestion() {
        assertTrue(resultMessage.contains("Response posted successfully!"));
        logger.log(Level.INFO, "Response has been successfully posted.");
    }
}
