#Auther:Bana

@tag
Feature: Program Exploration and Enrollment
Program Exploration and Enrollment should allow clients to browse programs by filters and enroll in them.

  @tag1
  Scenario: Browse programs by difficulty level
  A client should be able to browse programs by difficulty level.

    Given I am logged in as a Client
    When I browse the programs and filter by difficulty level "Beginner"
    Then I should see a list of programs with difficulty level "Beginner"

  @tag2
  Scenario: Browse programs by focus area
  A client should be able to browse programs by focus area.

    Given I am logged in as a Client
    When I browse the programs and filter by focus area "Weight Loss"
    Then I should see a list of programs with focus area "Weight Loss"

  @tag3
  Scenario: Browse programs by multiple filters (difficulty level and focus area)
  A client should be able to browse programs by multiple filters such as difficulty level and focus area.

    Given I am logged in as a Client
    When I browse the programs and filter by difficulty level "Intermediate" and focus area "Muscle Building"
    Then I should see a list of programs with difficulty level "Intermediate" and focus area "Muscle Building"

  @tag4
  Scenario: Enroll in a program
  A client should be able to enroll in a selected program.

    Given I am logged in as a Client
    When I select a program with difficulty level "Advanced" and focus area "Flexibility"
    And I click on "Enroll"
    Then I should be successfully enrolled in the selected program

  @tag5
  Scenario: View schedule of an enrolled program
  A client should be able to view the schedule of an enrolled program.

    Given I am logged in as a Client
    When I am enrolled in a program with focus area "Muscle Building"
    Then I should be able to view the program's schedule
    And I should see the start and end times of the program
    
    @tag6
Scenario: No programs available for the selected filters
  A client should be notified if no programs match the selected filters.

    Given I am logged in as a Client
    When I browse the programs and filter by difficulty level "Expert"
    Then I should see a message saying "No programs available for the selected filters"
    
    @tag7
Scenario: Attempt to enroll in a fully booked program
  A client should not be able to enroll in a program if it is fully booked.

    Given I am logged in as a Client
    And the program with difficulty level "Advanced" and focus area "Weight Loss" is fully booked
    When I attempt to enroll in the program
    Then I should see a message saying "This program is fully booked"
    
    @tag8
Scenario: Attempt to enroll in the same program multiple times
  A client should not be able to enroll in the same program more than once.

    Given I am logged in as a Client
    And I am already enrolled in the program with focus area "Flexibility"
    When I attempt to enroll in the same program again
    Then I should see a message saying "You are already enrolled in this program"
    
    @tag9
Scenario: Cancel enrollment in a program
  A client should be able to cancel their enrollment in a program.

    Given I am logged in as a Client
    And I am enrolled in the program with focus area "Muscle Building"
    When I choose to cancel my enrollment
    Then I should be successfully unenrolled from the program
    And I should see a confirmation message saying "You have successfully canceled your enrollment"
    
    @tag10
Scenario: View program details before enrollment
  A client should be able to view full details of a program before enrolling.

    Given I am logged in as a Client
    When I select the program with focus area "Weight Loss"
    Then I should see the program's details including its difficulty level, schedule, and description
    
    