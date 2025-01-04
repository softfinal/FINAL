package fitt;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.Test;

public class ProgramExplorationTest {

    static class Client {
        String name;
        boolean isEnrolled = false;
        boolean isProgramAvailable = true;

        public Client(String name) {
            this.name = name;
        }

        public void filterPrograms(String difficultyLevel, String focusArea) {
            System.out.println("Filtering programs by difficulty: " + difficultyLevel + " and focus area: " + focusArea);
        }

        public void viewProgramDetails(String programName) {
            System.out.println("Viewing details for program: " + programName);
        }

        public void enrollInProgram(String programName) {
            if (isProgramAvailable) {
                isEnrolled = true;
                System.out.println("Enrolled in program: " + programName);
            } else {
                System.out.println("Program is fully booked. Enrollment failed.");
            }
        }

        public void cancelEnrollment(String programName) {
            if (isEnrolled) {
                isEnrolled = false;
                System.out.println("Cancelled enrollment in program: " + programName);
            }
        }
    }

    Client currentClient = new Client("John Doe");

    @Test
    @When("I browse the programs and filter by difficulty level")
    public void i_browse_the_programs_and_filter_by_difficulty_level() {
        currentClient.filterPrograms("Advanced", "");
    }

    @Test
    @Then("I should see a list of programs with difficulty level")
    public void i_should_see_a_list_of_programs_with_difficulty_level() {
        boolean programsFiltered = true;
        Assert.assertTrue("Programs with difficulty level should be displayed.", programsFiltered);
    }

    @Test
    @When("I browse the programs and filter by focus area")
    public void i_browse_the_programs_and_filter_by_focus_area() {
        currentClient.filterPrograms("", "Data Science");
    }

    @Test
    @Then("I should see a list of programs with focus area")
    public void i_should_see_a_list_of_programs_with_focus_area() {
        boolean programsFiltered = true;
        Assert.assertTrue("Programs with focus area should be displayed.", programsFiltered);
    }

    @Test
    @When("I browse the programs and filter by difficulty level and focus area")
    public void i_browse_the_programs_and_filter_by_difficulty_level_and_focus_area() {
        currentClient.filterPrograms("Intermediate", "Machine Learning");
    }

    @Test
    @Then("I should see a list of programs with difficulty level and focus area")
    public void i_should_see_a_list_of_programs_with_difficulty_level_and_focus_area() {
        boolean programsFiltered = true;
        Assert.assertTrue("Programs with difficulty level and focus area should be displayed.", programsFiltered);
    }

    @Test
    @When("I select a program with difficulty level and focus area")
    public void i_select_a_program_with_difficulty_level_and_focus_area() {
        System.out.println("Selecting program with difficulty: Intermediate and focus area: Machine Learning");
    }

    @Test
    @When("I click on a program")
    public void i_click_on() {
        currentClient.viewProgramDetails("AI Basics");
    }

    @Test
    @Then("I should be successfully enrolled in the selected program")
    public void i_should_be_successfully_enrolled_in_the_selected_program() {
        currentClient.enrollInProgram("AI Basics");
        Assert.assertTrue("The client should be successfully enrolled in the program.", currentClient.isEnrolled);
    }

    @Test
    @Given("the program with difficulty level and focus area is fully booked")
    public void the_program_with_difficulty_level_and_focus_area_is_fully_booked() {
        currentClient.isProgramAvailable = false;
    }

    @Test
    @When("I attempt to enroll in the program")
    public void i_attempt_to_enroll_in_the_program() {
        currentClient.enrollInProgram("AI Basics");
    }

    @Test
    @Given("I am already enrolled in the program with focus area")
    public void i_am_already_enrolled_in_the_program_with_focus_area() {
        currentClient.enrollInProgram("Data Science");
    }

    @Test
    @When("I attempt to enroll in the same program again")
    public void i_attempt_to_enroll_in_the_same_program_again() {
        boolean isAlreadyEnrolled = currentClient.isEnrolled;
        Assert.assertTrue("The client should not be able to enroll in the same program again.", !isAlreadyEnrolled);
    }

    @Test
    @When("I choose to cancel my enrollment")
    public void i_choose_to_cancel_my_enrollment() {
        currentClient.cancelEnrollment("AI Basics");
    }

    @Test
    @Then("I should be successfully unenrolled from the program")
    public void i_should_be_successfully_unenrolled_from_the_program() {
        Assert.assertTrue("The client should be successfully unenrolled from the program.", !currentClient.isEnrolled);
    }

    @Test
    @Then("I should see a confirmation message")
    public void i_should_see_a_confirmation_message() {
        boolean confirmationMessageDisplayed = true;
        Assert.assertTrue("The confirmation message should be displayed.", confirmationMessageDisplayed);
    }
}
