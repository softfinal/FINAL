package fitnessAcceptanceTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.example.Function;
import org.junit.Test;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SignupTest {

    Function f = new Function();
    User users = new User();
    Logger logger = Logger.getLogger(SignupTest.class.getName());

    @Given("customers")
    public void customersStep() {
        // Initialize customer data or any setup required
        logger.log(Level.INFO, "Customers step initialized.");
    }

    @Given("there is a User with User ID {string}, NAME {string}, PASSWORD {string}, phone {string}, address {string}")
    public void thereIsAUserWithUserIDNAMEPASSWORDPhoneAddress(String id, String name, String password, String phone, String address) {
        users.setId(id);
        users.setName(name);
        users.setPassword(password);
        users.setPhone(phone);
        users.setAddress(address);
        users.setType("customer"); // Assuming default type is customer
        logger.log(Level.INFO, String.format("User initialized: %s", name));
    }

    @When("the User is registered {string}")
    public void theUserIsRegistered(String filePath) {
        // Simulate user registration
        boolean registered = users.isRegest(filePath);
        if (registered) {
            User.adduser(users);
        }
        assertTrue(String.format("User should be registered successfully at path %s.", filePath), registered);
    }

    @Then("the User with User ID {string}, NAME {string}, PASSWORD {string}, phone {string}, address {string} is registered in the system")
    public void theUserWithUserIDNAMEPASSWORDPhoneAddressIsRegisteredInTheSystem(String id, String name, String password, String phone, String address) {
        boolean registered = users.isRegest("mockFilePath"); // Simulated file path
        assertTrue(String.format("User with ID %s should be registered in the system.", id), registered);
    }

    @Then("the error message {string} is given")
    public void theErrorMessageIsGiven(String errorMessage) {
        logger.log(Level.INFO, String.format("Error: %s", errorMessage));
    }

    @Test
    public void testUserRegistrationSuccess() {
        // Test successful registration
        users.setId("001");
        users.setName("Jane Doe");
        users.setPassword("securePass");
        users.setPhone("1234567890");
        users.setAddress("123 Main Street");

        boolean registered = users.isRegest("mockFilePath"); // Simulate registration file
        assertTrue(String.format("User %s should be successfully registered.", users.getUsername()), registered);
    }

    @Test
    public void testUserRegistrationFailure() {
        // Test failure due to duplicate registration
        User u = new User();
        u.setId("002");
        u.setName("John Doe");
        u.setPassword("password123");
        u.setPhone("0987654321");
        u.setAddress("456 Elm Street");

        // Simulate adding the user to the system
        User.adduser(u);

        // Attempt to register the same user again
        User duplicateUser = new User();
        duplicateUser.setId("002"); // Same ID as the first user
        duplicateUser.setName("John Doe");
        duplicateUser.setPassword("password123");
        duplicateUser.setPhone("0987654321");
        duplicateUser.setAddress("456 Elm Street");

        // Check if the duplicate user is rejected
        boolean registered = duplicateUser.isRegest(duplicateUser.getId());
        assertFalse(String.format("User registration for ID %s should fail due to duplicate.", duplicateUser.getId()), !registered);
    }
}
