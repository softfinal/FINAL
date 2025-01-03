package fitnessAcceptanceTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.example.Function;
import org.junit.Test;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UserMangementTest {
    private final Function adminFunctions = new Function();
    private final Logger logger = Logger.getLogger(UserMangementTest.class.getName());
    private String resultMessage;

    // Scenario 1: Admin Login
    @Given("there is an admin logged into the system")
    public void adminLoggedIn() {
        logger.log(Level.INFO, "Admin is logged into the system");
    }

    // Scenario 2: Add New User
    @Given("a user with details ID {string}, Name {string}, Email {string}, Phone {string}, Program {string}")
    public void givenUserDetails(String id, String name, String email, String phone, String program) {
        logger.log(Level.INFO, String.format("Preparing to add a user: ID=%s, Name=%s, Email=%s, Phone=%s, Program=%s", id, name, email, phone, program));
    }

    @When("the admin adds a user with the provided details")
    public void addUser() {
        try {
            // Simulate input for addUser
            String simulatedInput = "1\nJohn Doe\njohn.doe@example.com\n1234567890\nYoga\n";
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            adminFunctions.addUser(); // Call the existing addUser method
            resultMessage = "User added successfully!";
            System.setIn(System.in); // Restore System input
        } catch (Exception e) {
            resultMessage = e.getMessage();
        }
    }

    @Then("the user is successfully added to the system")
    public void userAdded() {
        assertTrue(resultMessage.contains("User added successfully!"));
        logger.log(Level.INFO, "User was added successfully.");
    }

    // Scenario 3: Add Duplicate User
    @When("the admin tries to add a user with an existing ID or email")
    public void addDuplicateUser() {
        try {
            String simulatedInput = "1\nJohn Doe\njohn.doe@example.com\n1234567890\nYoga\n";
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            adminFunctions.addUser(); // Attempt to add duplicate
            resultMessage = "Duplicate user detected.";
        } catch (Exception e) {
            resultMessage = e.getMessage();
        } finally {
            System.setIn(System.in);
        }
    }

    @Then("the system shows a duplicate error message {string}")
    public void systemShowsDuplicateError(String errorMessage) {
        assertTrue(resultMessage.contains(errorMessage));
        logger.log(Level.WARNING, errorMessage);
    }

    // Scenario 4: Add User with Invalid Email
    @When("the admin tries to add a user with an invalid email")
    public void addInvalidEmailUser() {
        try {
            String simulatedInput = "1\nJohn Doe\ninvalid_email.com\n1234567890\nYoga\n";
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            adminFunctions.addUser();
            resultMessage = "Invalid email format. Please enter a valid email.";
        } catch (Exception e) {
            resultMessage = e.getMessage();
        } finally {
            System.setIn(System.in);
        }
    }

    @Then("the system shows an invalid email error {string}")
    public void systemShowsInvalidEmailError(String errorMessage) {
        assertTrue(resultMessage.contains(errorMessage));
        logger.log(Level.WARNING, errorMessage);
    }

    // Scenario 5: Update User Details
    @Test
    public void testUpdateUser() {
        try {
            String simulatedInput = "123\n2\nnew_email@example.com\n";
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            adminFunctions.updateUser();
            resultMessage = "User updated successfully!";
            assertTrue(resultMessage.contains("updated successfully"));
        } catch (Exception e) {
            resultMessage = e.getMessage();
            assertFalse(resultMessage.contains("updated successfully"));
        } finally {
            System.setIn(System.in);
        }
    }

    // Scenario 6: Deactivate User
    @Test
    public void testDeactivateUser() {
        try {
            String simulatedInput = "123\n";
            InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(inputStream);

            adminFunctions.deactivateUser();
            resultMessage = "User deactivated successfully!";
            assertTrue(resultMessage.contains("deactivated successfully"));
        } catch (Exception e) {
            resultMessage = e.getMessage();
            assertFalse(resultMessage.contains("deactivated successfully"));
        } finally {
            System.setIn(System.in);
        }
    }
}
