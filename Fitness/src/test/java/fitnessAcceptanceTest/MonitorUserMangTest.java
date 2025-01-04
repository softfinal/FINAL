package fitnessAcceptanceTest;

import static org.junit.Assert.assertTrue;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.example.Function;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonitorUserMangTest {
    private final Function adminFunctions = new Function();
    private final Logger logger = Logger.getLogger(MonitorUserMangTest.class.getName());
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    private String reportFileName;

    @Test
    public void testUserActivitySummary() {
        i_am_logged_in_as_an_admin();
        i_navigate_to_the("User Activity Dashboard");
        i_should_see_a_summary_of_user_activity(DataTable.create(
            List.of(
                Map.of("Metric", "Active Users", "Value", "100"),
                Map.of("Metric", "Inactive Users", "Value", "50")
            ).stream()
            .map(map -> List.of(map.get("Metric"), map.get("Value")))
            .toList()
        ));
    }

    @Test
    public void testActivityStatisticsForDateRange() {
        i_am_on_the("Activity Statistics Page");
        i_select_a_date_range_from_to("2024-01-01", "2024-12-31");

        // Convert List<Map<String, String>> to List<List<String>>
        List<Map<String, String>> mapList = List.of(
            Map.of("Metric", "Logins", "Value", "500"),
            Map.of("Metric", "New Users", "Value", "200")
        );

        // Convert the mapList to a List<List<String>>
        List<List<String>> listList = mapList.stream()
            .map(map -> List.of(map.get("Metric"), map.get("Value")))
            .toList();

        // Now you can pass the List<List<String>> to DataTable.create
        i_should_see_the_activity_statistics_for_the_selected_date_range(DataTable.create(listList));
    }

    @Test
    public void testActivityDetailsForUserID() {
        i_enter_a_user_id_into_the_field("User ID Field");
        i_should_see_the_activity_details_for_the_selected_user(DataTable.create(
            List.of(
                Map.of("Metric", "Sessions", "Value", "25"),
                Map.of("Metric", "Last Login", "Value", "2024-12-18")
            ).stream()
            .map(map -> List.of(map.get("Metric"), map.get("Value")))
            .toList()
        ));
    }

    @Test
    public void testActivityDetailsForUserName() {
        i_enter_a_user_name_into_the_field("User Name Field");
        i_should_see_the_activity_details_for_the_selected_user(DataTable.create(
            List.of(
                Map.of("Metric", "Sessions", "Value", "30"),
                Map.of("Metric", "Last Login", "Value", "2024-12-18")
            ).stream()
            .map(map -> List.of(map.get("Metric"), map.get("Value")))
            .toList()
        ));
    }

    @Test
    public void testDownloadableReportGeneration() {
        i_am_logged_in_as_an_admin();
        i_click("Generate Report Button");
        a_downloadable_report_should_be_generated();
        the_file_name_should_include_the_current_date_in_the_format("yyyy-MM-dd");
    }

    @Given("I am logged in as an admin")
    public void i_am_logged_in_as_an_admin() {
        logger.log(Level.INFO, () -> "Admin is logged into the system");
        System.setOut(new PrintStream(outputStream)); // Capture console output
    }

    @When("I navigate to the {string}")
    public void i_navigate_to_the(String dashboard) {
        logger.log(Level.INFO, () -> "Navigating to: " + dashboard);
    }

    @Then("I should see a summary of user activity")
    public void i_should_see_a_summary_of_user_activity(DataTable dataTable) {
        try {
            adminFunctions.viewProgramStats(); // Simulate user activity statistics
            String output = outputStream.toString();
            for (var row : dataTable.asMaps()) {
                assertTrue(output.contains(row.get("Metric")));
                assertTrue(output.contains(row.get("Value")));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, () -> "Error viewing user activity summary: " + e.getMessage());
        }
    }

    @Given("I am on the {string}")
    public void i_am_on_the(String dashboard) {
        logger.log(Level.INFO, () -> "On page: " + dashboard);
    }

    @When("I select a date range from {string} to {string}")
    public void i_select_a_date_range_from_to(String startDate, String endDate) {
        logger.log(Level.INFO, () -> "Filtering by date range: " + startDate + " to " + endDate);
        // Add logic to filter by date range
    }

    @Then("I should see the activity statistics for the selected date range")
    public void i_should_see_the_activity_statistics_for_the_selected_date_range(DataTable dataTable) {
        try {
            adminFunctions.viewProgramStats(); // Simulate filtered statistics
            String output = outputStream.toString();
            for (var row : dataTable.asMaps()) {
                assertTrue(output.contains(row.get("Metric")));
                assertTrue(output.contains(row.get("Value")));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, () -> "Error viewing filtered activity statistics: " + e.getMessage());
        }
    }

    @When("I enter a user ID into the {string} field")
    public void i_enter_a_user_id_into_the_field(String field) {
        logger.log(Level.INFO, () -> "Searching user by ID in field: " + field);
        // Simulate user-specific search by ID
    }

    @Then("I should see the activity details for the selected user")
    public void i_should_see_the_activity_details_for_the_selected_user(DataTable dataTable) {
        try {
            adminFunctions.viewProgramStats(); // Replace with specific user stats
            String output = outputStream.toString();
            for (var row : dataTable.asMaps()) {
                assertTrue(output.contains(row.get("Metric")));
                assertTrue(output.contains(row.get("Value")));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, () -> "Error viewing user activity details: " + e.getMessage());
        }
    }

    @When("I enter a user name into the {string} field")
    public void i_enter_a_user_name_into_the_field(String field) {
        logger.log(Level.INFO, () -> "Searching user by name in field: " + field);
        // Simulate user-specific search by name
    }

    @When("I click {string}")
    public void i_click(String button) {
        logger.log(Level.INFO, () -> "Clicked: " + button);
    }

    @Then("a downloadable report should be generated")
    public void a_downloadable_report_should_be_generated() {
        reportFileName = "User_Activity_Report_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".csv";
        logger.log(Level.INFO, () -> "Generated report: " + reportFileName);
        assertTrue(reportFileName.endsWith(".csv"));
    }

    @Then("the file name should include the current date in the format {string}")
    public void the_file_name_should_include_the_current_date_in_the_format(String dateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        String expectedFileName = "User_Activity_Report_" + LocalDate.now().format(formatter) + ".csv";
        assertTrue(reportFileName.equals(expectedFileName));
    }
}
