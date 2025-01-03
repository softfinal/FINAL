
Feature: Program Monitoring
  As an admin
  I want to monitor program statistics and generate reports
  So that I can analyze performance and track progress effectively

  Background:
    Given I am logged in as an admin

  Scenario: View statistics of the most popular programs
    Given I navigate to the "Program Monitoring Dashboard"
    When I select the option to view "Most Popular Programs"
    Then I should see a list of programs sorted by enrollment
      | Program Name       | Enrollment |
      | Yoga Basics        | <enrollment_1> |
      | Advanced Fitness   | <enrollment_2> |
      | Healthy Cooking    | <enrollment_3> |

  Scenario: Generate reports on revenue, attendance, and client progress
    Given I am on the "Program Monitoring Dashboard"
    When I select the option to generate "Program Performance Reports"
    And I specify the report type as "Revenue"
    Then I should receive a detailed revenue report for all programs
      | Program Name       | Revenue     |
      | Yoga Basics        | <revenue_1> |
      | Advanced Fitness   | <revenue_2> |
      | Healthy Cooking    | <revenue_3> |
    When I specify the report type as "Attendance"
    Then I should receive a detailed attendance report for all programs
      | Program Name       | Attendance |
      | Yoga Basics        | <attendance_1> |
      | Advanced Fitness   | <attendance_2> |
      | Healthy Cooking    | <attendance_3> |
    When I specify the report type as "Client Progress"
    Then I should receive a detailed progress report for all enrolled clients
      | Client Name        | Progress Level |
      | Alice Smith        | 75%            |
      | Bob Johnson        | 85%            |

  Scenario: Track active and completed programs
    Given I am on the "Program Monitoring Dashboard"
    When I select the option to view "Program Status"
    Then I should see programs categorized by status
      | Program Name       | Status       |
      | Yoga Basics        | Active       |
      | Advanced Fitness   | Completed    |
      | Healthy Cooking    | Active       |

  Scenario: Export program performance reports
    Given I am on the "Program Monitoring Dashboard"
    When I click "Export Report"
    And I select the report type as "Revenue"
    Then a downloadable revenue report should be generated
    And the file name should include the current date in the format "Revenue_Report_<yyyy-MM-dd>.csv"
