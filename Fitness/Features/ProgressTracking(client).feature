#Auther:Bana

@tag
Feature: Progress Tracking
Progress Tracking should allow clients to track their fitness milestones and view achievements.

  @tag1
  Scenario: Track personal fitness milestone (weight)
  A client should be able to track their weight milestone and view it in their progress section.

    Given I am logged in as a Client
    When I update my weight in my profile
    Then my weight should be saved successfully in my profile
    And I should be able to view my updated weight in the progress section

  @tag2
  Scenario: Track personal fitness milestone (BMI)
  A client should be able to track their BMI milestone and view it in their progress section.

    Given I am logged in as a Client
    When I update my BMI in my profile
    Then my BMI should be saved successfully in my profile
    And I should be able to view my updated BMI in the progress section

  @tag3
  Scenario: Track personal fitness milestone (attendance)
  A client should be able to track their attendance in fitness program sessions.

    Given I am logged in as a Client
    When I attend a fitness program session
    Then my attendance should be recorded for the program
    And I should be able to view my attendance history in the progress section

  @tag4
  Scenario: View achievements or badges after completing a program
  A client should be able to receive an achievement or badge after completing a program and view it in the achievements section.

    Given I am logged in as a Client
    When I complete a fitness program
    Then I should receive an achievement or badge for completing the program
    And the achievement or badge should appear in the achievements section of my profile

  @tag5
  Scenario: View all tracked milestones and achievements
  A client should be able to view all their tracked milestones and achievements in the progress section.

    Given I am logged in as a Client
    When I view my progress section
    Then I should be able to see my tracked milestones such as weight, BMI, and attendance
    And I should be able to see the badges or achievements earned for completing programs
    
    @tag6
Scenario: Enter an invalid weight
  A client should be informed if they enter an invalid weight value (e.g., negative or extremely high).

    Given I am logged in as a Client
    When I update my weight to "-10"
    Then I should see an error message saying "Invalid weight value"
    And my weight should not be saved until a valid value is entered
    
    
    @tag7
Scenario: Enter an invalid BMI
  A client should be informed if they enter an invalid BMI value (e.g., BMI less than 10 or greater than 50).

    Given I am logged in as a Client
    When I update my BMI to "5"
    Then I should see an error message saying "Invalid BMI value"
    And my BMI should not be saved until a valid value is entered
    
    @tag8
Scenario: Failed to record attendance in a session
  A client should be notified if attendance for a session cannot be recorded.

    Given I am logged in as a Client
    When I attend a fitness program session
    And the system fails to record my attendance
    Then I should see a message saying "Attendance could not be recorded, please try again"
    
    @tag9
Scenario: Failure to receive achievement or badge after completing a program
  A client should be notified if they do not receive an achievement or badge after completing a program.

    Given I am logged in as a Client
    When I complete a fitness program
    And the system fails to assign an achievement or badge
    Then I should see a message saying "Achievement or badge could not be awarded, please contact support"
    
    @tag10
Scenario: View progress over time for weight or BMI
  A client should be able to view how their weight or BMI has changed over time.

    Given I am logged in as a Client
    When I view my progress section
    Then I should see a graph or list showing the changes in my weight and/or BMI over time
    
    @tag11
Scenario: Remove weight or BMI tracking
  A client should be able to remove tracking for weight or BMI from their progress section.

    Given I am logged in as a Client
    When I choose to remove weight tracking from my profile
    Then my weight should no longer be tracked and will be removed from the progress section
    