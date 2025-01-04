package fitnessAcceptanceTest;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import io.cucumber.java.en.Then;

public class ProgressTrackingTest {

    private File milestonesFile;
    private File attendanceFile;
    private String clientId;
    private ProgressTracking progressTracking;

    @Before
    public void setUp() {
        try {
            // Initialize milestones file with mock data
            milestonesFile = new File("milestones.txt");
            if (!milestonesFile.exists()) {
                milestonesFile.createNewFile();
            }
            try (FileWriter writer = new FileWriter(milestonesFile)) {
                writer.write("1, 75.5, 22.0, 2024-12-19\n");  // ClientId: 1, Weight, BMI, Date
            }

            // Initialize attendance file with mock data
            attendanceFile = new File("attendance.txt");
            if (!attendanceFile.exists()) {
                attendanceFile.createNewFile();
            }
            try (FileWriter writer = new FileWriter(attendanceFile)) {
                writer.write("1, program1, true, 2024-12-19\n");  // ClientId: 1, Program, Date, Attended
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize the ProgressTracking object and clientId
        progressTracking = new ProgressTracking();
        clientId = "1";  // Use string as the client ID

        System.out.println("Test setup complete with mock data.");
    }

    @After
    public void tearDown() {
        // Clean up after the test, if necessary
        System.out.println("Test cleanup complete.");
    }

    @Test
    @Then("my weight should be saved successfully in my profile")
    public void my_weight_should_be_saved_successfully_in_my_profile() {
        List<String> milestones = progressTracking.viewMilestones(clientId);
        assertTrue("Milestones should include the weight", 
                   milestones.stream().anyMatch(m -> m.contains("Weight: 75.5")));
        System.out.println("Weight saved successfully.");
    }

    @Test
    @Then("I should be able to view my updated weight in the progress section")
    public void i_should_be_able_to_view_my_updated_weight_in_the_progress_section() {
        List<String> milestones = progressTracking.viewMilestones(clientId);
        assertTrue("Updated weight should be visible in milestones", 
                   milestones.stream().anyMatch(m -> m.contains("Weight: 75.5")));
        System.out.println("Updated weight is visible.");
    }

    @Test
    @Then("my BMI should be saved successfully in my profile")
    public void my_bmi_should_be_saved_successfully_in_my_profile() {
        List<String> milestones = progressTracking.viewMilestones(clientId);

        // Print milestones for debugging
        System.out.println("Milestones: " + milestones);

        assertTrue("Milestones should include the updated BMI", 
                   milestones.stream().anyMatch(m -> m.contains("BMI: 22.0")));
        System.out.println("BMI saved successfully.");
    }

    @Test
    @Then("I should be able to view my updated BMI in the progress section")
    public void i_should_be_able_to_view_my_updated_bmi_in_the_progress_section() {
        List<String> milestones = progressTracking.viewMilestones(clientId);
        assertTrue("Updated BMI should be visible in milestones", 
                   milestones.stream().anyMatch(m -> m.contains("BMI: 22.0")));
        System.out.println("Updated BMI is visible.");
    }

    @Test
    @Then("my attendance should be recorded for the program")
    public void my_attendance_should_be_recorded_for_the_program() {
        List<String> attendance = progressTracking.viewAttendance(clientId);

        assertNotNull("Attendance list should not be null.", attendance);
        assertFalse("Attendance list should not be empty.", attendance.isEmpty());

        boolean isAttendanceRecorded = attendance.stream()
            .anyMatch(a -> a.contains("Program ID: program1") && 
                           a.contains("Attended: true") && 
                           a.contains("Date: 2024-12-19"));

        assertTrue("Attendance should be recorded for Program: program1 on Date: 2024-12-19.", isAttendanceRecorded);

        System.out.println("Attendance recorded successfully.");
    }


    @Test
    @Then("my weight should not be saved until a valid value is entered")
    public void my_weight_should_not_be_saved_until_a_valid_value_is_entered() {
        List<String> milestones = progressTracking.viewMilestones(clientId);
        assertFalse("Invalid weight should not be saved", 
                    milestones.stream().anyMatch(m -> m.contains("Weight: -1")));
        System.out.println("Invalid weight was not saved.");
    }

    @Test
    @Then("my BMI should not be saved until a valid value is entered")
    public void my_bmi_should_not_be_saved_until_a_valid_value_is_entered() {
        List<String> milestones = progressTracking.viewMilestones(clientId);
        assertFalse("Invalid BMI should not be saved", 
                    milestones.stream().anyMatch(m -> m.contains("BMI: -1")));
        System.out.println("Invalid BMI was not saved.");
    }
 // تأكد من أن clientId هو نوع String (أو النوع الصحيح الذي تستخدمه)
    String clientId1 = "1";  // أو استخدم القيمة الصحيحة

    @Test
    @Then("my weight should no longer be tracked and will be removed from the progress section")
    public void my_weight_should_no_longer_be_tracked_and_will_be_removed_from_the_progress_section() {
        // إزالة الوزن من التتبع
        progressTracking.removeWeight(clientId);

        // التأكد من أن التحديث تم بشكل صحيح
        List<String> milestones = progressTracking.viewMilestones(clientId);
        System.out.println("Milestones after removing weight: " + milestones);  // طباعة المعالم للتحقق

        // التأكد من عدم وجود الوزن في المعالم
        assertFalse("Weight should be removed from tracking", 
                    milestones.stream().anyMatch(m -> m.contains("Weight")));

        System.out.println("Weight tracking removed successfully.");
    }


} 
    
    
    
  
