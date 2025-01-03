//package fitnessAcceptanceTest;
//
//import static org.junit.Assert.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import fitnessAcceptanceTest.ProgramManagement.AccountManagementSystem;
//import fitnessAcceptanceTest.ProgramManagement.Client;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
//public class AccountManagementStepsTest {
//	 private AccountManagementSystem accountSystem = new AccountManagementSystem();
//	    private Client currentClient;
//	    private static Logger logger = Logger.getLogger(AccountManagementStepsTest.class.getName());
//
//	    @Given("I am logged in as a Client")
//	    public void i_am_logged_in_as_a_client() {
//	        currentClient = new Client(1, "John Doe", 25, "Weight Loss", "Vegetarian");
//	        assertNotNull("Client should be initialized", currentClient);
//	        logger.log(Level.INFO, "Client logged in: {0}", currentClient.getName());
//	    }
//
//	    @When("I create a profile with my personal details including age and fitness goals")
//	    public void i_create_a_profile_with_my_personal_details_including_age_and_fitness_goals() {
//	        int clientId = accountSystem.createProfile("John Doe", 25, "Weight Loss", "Vegetarian");
//	        currentClient = accountSystem.getClientById(clientId);
//	        assertNotNull("Profile should be created", currentClient);
//	    }
//
//	    @Then("my personal details should be saved successfully in my profile")
//	    public void my_personal_details_should_be_saved_successfully_in_my_profile() {
//	        assertEquals("John Doe", currentClient.getName());
//	        assertEquals(25, currentClient.age);
//	        assertEquals("Weight Loss", currentClient.fitnessGoals);
//	        assertEquals("Vegetarian", currentClient.dietaryPreferences);
//	    }
//
//	    @When("I update my personal details such as age and fitness goals")
//	    public void i_update_my_personal_details_such_as_age_and_fitness_goals() {
//	        currentClient.updateProfile("John Doe", 26, "Muscle Building", "Gluten-Free");
//	        logger.log(Level.INFO, "Updated profile: {0}", currentClient);
//	    }
//
//	    @Then("my updated personal details should be saved successfully in my profile")
//	    public void my_updated_personal_details_should_be_saved_successfully_in_my_profile() {
//	        assertEquals(26, currentClient.age);
//	        assertEquals("Muscle Building", currentClient.fitnessGoals);
//	        assertEquals("Gluten-Free", currentClient.dietaryPreferences);
//	    }
//
//	    @When("I choose to delete my profile")
//	    public void i_choose_to_delete_my_profile() {
//	        accountSystem.deactivateAccount(String.valueOf(currentClient.id));
//	        currentClient.setAccountStatus("Inactive");
//	    }
//
//	    @Then("my profile should be removed from the system")
//	    public void my_profile_should_be_removed_from_the_system() {
//	        assertEquals("Inactive", currentClient.getAccountStatus());
//	    }
//
//	    @Then("I should no longer be able to access my account")
//	    public void i_should_no_longer_be_able_to_access_my_account() {
//	        assertEquals("Inactive", currentClient.getAccountStatus());
//	        logger.log(Level.INFO, "Account deactivated for client: {0}", currentClient.getName());
//	    }
//
//	    @When("I attempt to access another client's profile")
//	    public void i_attempt_to_access_another_client_s_profile() {
//	        Client otherClient = new Client(2, "Jane Doe", 30, "Muscle Gain", "Vegan");
//	        accountSystem.createProfile("Jane Doe", 30, "Muscle Gain", "Vegan");
//
//	        try {
//	            if (currentClient.id != otherClient.id) {
//	                throw new UnauthorizedAccessException("Insufficient permissions to view or edit this profile.");
//	            }
//	        } catch (UnauthorizedAccessException e) {
//	            logger.log(Level.WARNING, e.getMessage());
//	        }
//	    }
//
//	    @Then("I should receive an error message indicating insufficient permissions")
//	    public void i_should_receive_an_error_message_indicating_insufficient_permissions() {
//	        logger.log(Level.INFO, "Permission denied for accessing another client's profile");
//	    }
//
//	    // Helper class for unauthorized access exceptions
//	    private static class UnauthorizedAccessException extends Exception {
//	        public UnauthorizedAccessException(String message) {
//	            super(message);
//	        }
//	    }
//	}
//
//
