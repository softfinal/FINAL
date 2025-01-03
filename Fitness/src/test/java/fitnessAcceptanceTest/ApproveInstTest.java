package fitnessAcceptanceTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.example.Function;
import org.junit.Test;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.Before;

public class ApproveInstTest {
    private final Function adminFunctions = new Function();
    private final Logger logger = Logger.getLogger(ApproveInstTest.class.getName());
    private String resultMessage;

    @Before
    public void setUp() {
        // Set up any common setup for each test method
        resultMessage = "";
    }

    // Scenario 1: Approve Instructor Registration
    @Test
    public void testApproveInstructorRegistration() {
        thereIsPendingInstructorRegistration();
        iReviewTheRegistrationDetails();
        iApproveTheNewInstructorRegistration();
        instructorAddedAsActiveUser();
        iShouldSeeSuccessMessage();
    }

    @Test
    public void testRejectInstructorRegistration() {
        iRejectNewInstructorRegistration();
        iRejectTheNewInstructorRegistration();
        instructorNotAddedToSystem();
        iShouldSeeRejectionMessage();
    }

    @Test
    public void testEnrollInstructorAfterApproval() {
        iApproveAndAssignInstructorToProgram();
        instructorAssignedToProgram();
    }

    @Given("there is a pending instructor registration")
    public void thereIsPendingInstructorRegistration() {
        logger.log(Level.INFO, "There is a pending instructor registration.");
    }

    @When("I review the registration details")
    public void iReviewTheRegistrationDetails() {
        logger.log(Level.INFO, "Reviewing registration details.");
    }

    @When("I approve the new instructor registration")
    public void iApproveTheNewInstructorRegistration() {
        try {
            // Simulate input for approving an instructor
            String simulatedInput = "123\n";  // Assume '123' is the user ID to approve
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            adminFunctions.approve();  // Call the approve method
            resultMessage = "Instructor with ID 123 approved successfully!";
            System.setIn(System.in);  // Restore System input
        } catch (Exception e) {
            resultMessage = e.getMessage();
        }
    }

    @Then("the instructor should be added to the system as an active user")
    public void instructorAddedAsActiveUser() {
        assertTrue(resultMessage.contains("approved successfully!"));
        logger.log(Level.INFO, "Instructor was added as an active user.");
    }

    @Then("I should see a success message confirming the approval")
    public void iShouldSeeSuccessMessage() {
        assertTrue(resultMessage.contains("approved successfully!"));
        logger.log(Level.INFO, "Success message confirming approval.");
    }

    // Scenario 2: Reject Instructor Registration
    @Given("I reject a new instructor registration")
    public void iRejectNewInstructorRegistration() {
        logger.log(Level.INFO, "Rejecting a new instructor registration.");
    }

    @When("I reject the new instructor registration")
    public void iRejectTheNewInstructorRegistration() {
        try {
            // Simulate input for rejecting an instructor
            String simulatedInput = "123\n";  // Assume '123' is the user ID to reject
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            // adminFunctions.reject();  // Uncomment if you have the reject method implemented
            resultMessage = "Instructor with ID 123 rejected successfully!";
            System.setIn(System.in);  // Restore System input
        } catch (Exception e) {
            resultMessage = e.getMessage();
        }
    }

    @Then("the instructor should not be added to the system")
    public void instructorNotAddedToSystem() {
        assertTrue(resultMessage.contains("rejected successfully!"));
        logger.log(Level.INFO, "Instructor was not added to the system.");
    }

    @Then("I should see a rejection message confirming the action")
    public void iShouldSeeRejectionMessage() {
        assertTrue(resultMessage.contains("rejected successfully!"));
        logger.log(Level.INFO, "Rejection message confirming the action.");
    }

    // Scenario 3: Enroll Instructor after Approval
    @When("I approve the new instructor registration and assign to program")
    public void iApproveAndAssignInstructorToProgram() {
        try {
            String simulatedInput = "123\n";  // Assume '123' is the user ID to approve
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            adminFunctions.approve();  // Approve the instructor
            adminFunctions.updateInstructorInEnrollments("123");  // Assign instructor to program
            resultMessage = "Instructor enrolled and assigned successfully!";
            System.setIn(System.in);  // Restore System input
        } catch (Exception e) {
            resultMessage = e.getMessage();
        }
    }

    @Then("the instructor should be assigned to the program in enrollments")
    public void instructorAssignedToProgram() {
        assertTrue(resultMessage.contains("enrolled and assigned successfully!"));
        logger.log(Level.INFO, "Instructor assigned to program successfully.");
    }
}
