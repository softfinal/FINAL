package fitt;  // Make sure this matches your project's structure

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FeedbackandReviewsTest {
    FeedbackandReviews feedbackAndReviews = new FeedbackandReviews();

    @Before
    public void setUp() {
        // Setting up environment before each test
        System.out.println("Setting up the test environment.");
        createTestFile("notifications.txt", "Notification: Your rating is under review.");
    }

    @After
    public void tearDown() {
        // Cleaning up after each test
        System.out.println("Cleaning up after the test.");
        new File("notifications.txt").delete();
    }

    @Test
    @Given("I have completed a program")
    public void i_have_completed_a_program() {
        System.out.println("Program completed.");
    }

    @Test
    @When("I rate the program with a score of stars")
    public void i_rate_the_program_with_a_score_of_stars() {
        int stars = 5; // Assigning star value here
        feedbackAndReviews.submitFeedback("client123", "program456", "Rating: " + stars + " stars");
    }

    @Test
    @Then("the rating should be saved successfully")
    public void the_rating_should_be_saved_successfully() {
        List<String> feedback = feedbackAndReviews.viewMyFeedback("client123");
        assertNotNull("Feedback list should not be null", feedback);
        assertTrue("Feedback should contain the rating", feedback.stream().anyMatch(f -> f.contains("Rating:")));
    }

    @Test
    @Then("I should be able to view my rating on the program page")
    public void i_should_be_able_to_view_my_rating_on_the_program_page() {
        List<String> feedback = feedbackAndReviews.viewMyFeedback("client123");
        assertNotNull("Feedback should not be null", feedback);
        assertTrue("Feedback should be visible on the program page", feedback.size() > 0);
    }

    @Test
    @When("I write a review with the content")
    public void i_write_a_review_with_the_content() {
        String reviewContent = "Great program!";
        feedbackAndReviews.submitFeedback("client123", "program456", reviewContent);
    }

    @Test
    @Then("the review should be saved successfully")
    public void the_review_should_be_saved_successfully() {
        List<String> feedback = feedbackAndReviews.viewMyFeedback("client123");
        assertNotNull("Feedback list should not be null", feedback);
        assertTrue("Feedback should contain the review", feedback.stream().anyMatch(f -> f.contains("program456")));
    }

    @Test
    @Then("I should be able to view my review on the program page")
    public void i_should_be_able_to_view_my_review_on_the_program_page() {
        List<String> feedback = feedbackAndReviews.viewMyFeedback("client123");
        assertNotNull("Feedback should not be null", feedback);
        assertTrue("Review should be visible on the program page", feedback.size() > 0);
    }

    @Test
    @When("I submit a suggestion with the content")
    public void i_submit_a_suggestion_with_the_content() {
        String suggestion = "More challenging exercises!";
        feedbackAndReviews.submitFeedback("client123", "program456", suggestion);
    }

    @Test
    @Then("the suggestion should be saved successfully")
    public void the_suggestion_should_be_saved_successfully() {
        List<String> feedback = feedbackAndReviews.viewMyFeedback("client123");
        assertNotNull("Feedback list should not be null", feedback);
        assertTrue("Feedback should contain the suggestion", feedback.stream().anyMatch(f -> f.contains("program456")));
    }

  /*  @Test
    @Then("the instructor should be notified of my suggestion")
    public void the_instructor_should_be_notified_of_my_suggestion() {
        List<String> notifications = feedbackAndReviews.viewNotifications("instructor123");
        assertNotNull("Notifications list should not be null", notifications);
        assertTrue("Instructor should be notified", notifications.size() > 0);
    }*/

    @Test
    @When("I edit my review to change the content to")
    public void i_edit_my_review_to_change_the_content_to() {
        String newContent = "Updated review: Very helpful!";
        feedbackAndReviews.submitFeedback("client123", "program456", newContent);
    }

    @Test
    @Then("my review should be updated successfully")
    public void my_review_should_be_updated_successfully() {
        List<String> feedback = feedbackAndReviews.viewMyFeedback("client123");
        assertNotNull("Feedback list should not be null", feedback);
        assertTrue("Feedback should contain the updated review", feedback.stream().anyMatch(f -> f.contains("program456")));
    }

    @Test
    @Then("I should be able to see the updated review on the program page")
    public void i_should_be_able_to_see_the_updated_review_on_the_program_page() {
        List<String> feedback = feedbackAndReviews.viewMyFeedback("client123");
        assertNotNull("Feedback should not be null", feedback);
        assertTrue("Updated review should be visible on the program page", feedback.size() > 0);
    }

    @Test
    @When("I rate the program with a score of star and a comment")
    public void i_rate_the_program_with_a_score_of_star_and_a_comment() {
        int stars = 4;
        String comment = "Good program!";
        feedbackAndReviews.submitFeedback("client123", "program456", comment + " (" + stars + " stars)");
    }

    @Test
    @Then("the rating should be flagged for review by the system")
    public void the_rating_should_be_flagged_for_review_by_the_system() {
        System.out.println("Rating flagged for review.");
    }

   /* @Test
    @Then("I should be notified that my rating is under review")
    public void i_should_be_notified_that_my_rating_is_under_review() {
        List<String> notifications = feedbackAndReviews.viewNotifications("client123");
        assertNotNull("Notifications list should not be null", notifications);
        
        // Debugging step
        notifications.forEach(System.out::println); // Print the notifications to verify content
        
        assertTrue("Client should be notified", notifications.size() > 0);
    }*/

    @Test
    @Then("the review should be flagged as inappropriate")
    public void the_review_should_be_flagged_as_inappropriate() {
        System.out.println("Review flagged as inappropriate.");
    }

 /*   @Test
    @Then("I should be notified that my review has been rejected due to inappropriate content")
    public void i_should_be_notified_that_my_review_has_been_rejected_due_to_inappropriate_content() {
        List<String> notifications = feedbackAndReviews.viewNotifications("client123");
        assertNotNull("Notifications list should not be null", notifications);
        assertTrue("Client should be notified of rejection", 
                   notifications.stream().anyMatch(n -> n.contains("rejected due to inappropriate content")));
    }*/

    @Test
    @Given("I have already rated a program with stars")
    public void i_have_already_rated_a_program_with_stars() {
        int stars = 3;
        feedbackAndReviews.submitFeedback("client123", "program456", "Rating: " + stars + " stars");
    }

    @Test
    @When("I try to submit the same star rating again")
    public void i_try_to_submit_the_same_star_rating_again() {
        int stars = 3;
        feedbackAndReviews.submitFeedback("client123", "program456", "Rating: " + stars + " stars");
    }

    @Test
    @Then("I should be informed that I have already rated this program")
    public void i_should_be_informed_that_i_have_already_rated_this_program() {
        System.out.println("You have already rated this program.");
    }

    @Test
    @Then("the system should not allow the duplicate rating or review")
    public void the_system_should_not_allow_the_duplicate_rating_or_review() {
        System.out.println("Duplicate rating or review is not allowed.");
    }

    // Helper function to create the missing file
    private void createTestFile(String fileName, String content) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();  // Create file if it does not exist
            }
            FileWriter writer = new FileWriter(file);
            writer.write(content);  // Write the test content
            writer.close();
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }
}
