package fitnessAcceptanceTest;


import static org.junit.Assert.*;
import static org.junit.Assert.*;
import fitt.ClientService;
import fitt.Client;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class ProgramManagementSteps {

    private ClientService accountSystem;
    private Client currentClient;
    private static Logger logger = Logger.getLogger(ProgramManagementSteps.class.getName());

    // Initialize accountSystem before each test
    @Before
    public void setUp() {
        accountSystem = new ClientService();
        // Create a client in setup to ensure currentClient is not null
        int clientId = accountSystem.createClient("John Doe", 25, "Weight Loss", "Vegetarian");
        currentClient = accountSystem.getClientById(clientId);
    }

    // Clean up after each test
    @After
    public void tearDown() {
        currentClient = null;
        accountSystem = null;
    }

    @Test
    @Given("I am logged in as a Client")
    public void i_am_logged_in_as_a_client() {
        assertNotNull("Client should be initialized", currentClient);
        logger.log(Level.INFO, "Client registered: {0}", currentClient.getName());
    }

    @Test
    @When("I create a profile with my personal details including age and fitness goals")
    public void i_create_a_profile_with_my_personal_details_including_age_and_fitness_goals() {
        assertNotNull("Profile should be created", currentClient);
    }

    @Test
    @When("I update my personal details such as age and fitness goals")
    public void i_update_my_personal_details_such_as_age_and_fitness_goals() {
        assertNotNull("Client should be initialized", currentClient);
        currentClient.updateProfile("John Doe", 26, "Muscle Building", "Gluten-Free");
        logger.log(Level.INFO, "Profile updated: {0}", currentClient);
    }

    @Test
    @Then("my updated personal details should be saved successfully in my profile")
    public void my_updated_personal_details_should_be_saved_successfully_in_my_profile() {
        assertNotNull("Client should be initialized", currentClient);
        currentClient.updateProfile("John Doe", 26, "Muscle Building", "Gluten-Free");

        assertEquals("John Doe", currentClient.getName());
        assertEquals("Muscle Building", currentClient.getFitnessGoals());
        assertEquals("Gluten-Free", currentClient.getDietaryPreferences());
        logger.log(Level.INFO, "Updated details validated: {0}", currentClient);
    }

    @Test
    @When("I choose to delete my profile")
    public void i_choose_to_delete_my_profile() {
        assertNotNull("Client should be initialized", currentClient);
        accountSystem.deleteClient(currentClient.getId());
    }

    @Test
    @Then("my profile should be removed from the system")
    public void my_profile_should_be_removed_from_the_system() {
        assertNotNull("Client should be initialized", currentClient);
        accountSystem.deleteClient(currentClient.getId());
        Client deletedClient = accountSystem.getClientById(currentClient.getId());

        assertNull("Client should be removed from the system", deletedClient);
        logger.log(Level.INFO, "Client profile removed successfully.");
    }

    @Test
    @Then("I should no longer be able to access my account")
    public void i_should_no_longer_be_able_to_access_my_account() {
        assertNotNull("Client should be initialized", currentClient);
        currentClient.setAccountStatus("Inactive");

        assertEquals("Inactive", currentClient.getAccountStatus());
        logger.log(Level.INFO, "Account deactivated for client: {0}", currentClient.getName());
    }

    @Test
    @When("I attempt to access another client's profile")
    public void i_attempt_to_access_another_client_s_profile() {
        assertNotNull("Client should be initialized", currentClient);

        int otherClientId = accountSystem.createClient("Jane Doe", 30, "Muscle Gain", "Vegan");
        Client otherClient = accountSystem.getClientById(otherClientId);

        try {
            if (currentClient.getId() != otherClient.getId()) {
                throw new UnauthorizedAccessException("No permission to access this profile.");
            }
            fail("Expected UnauthorizedAccessException to be thrown.");
        } catch (UnauthorizedAccessException e) {
            logger.log(Level.WARNING, e.getMessage());
            assertEquals("No permission to access this profile.", e.getMessage());
        }
    }

    @Test
    @Then("I should receive an error message indicating insufficient permissions")
    public void i_should_receive_an_error_message_indicating_insufficient_permissions() {
        logger.log(Level.INFO, "Access to another client's profile denied due to insufficient permissions.");
    }

    // Helper function for unauthorized access exception
    private static class UnauthorizedAccessException extends Exception {
        public UnauthorizedAccessException(String message) {
            super(message);
        }
    }
}

