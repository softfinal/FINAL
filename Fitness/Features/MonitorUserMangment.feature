#Author:loaa
@tag
Feature: Monitor user activity and engagement statistics
  As an admin
  I want to monitor user activity and engagement statistics
  So that I can make informed decisions about user management and platform improvements

  Background:
    Given I am logged in as an admin
@tage1
  Scenario: View user activity summary
    When I navigate to the "User Activity Dashboard"
    Then I should see a summary of user activity
      | Metric                | Value       |
      | Total Users           | <total_users> |
      | Active Users Today    | <active_today> |
      | Total Engagement Time | <engagement_time> |
@tag2
  Scenario: Filter user activity by date range
    Given I am on the "User Activity Dashboard"
    When I select a date range from "<start_date>" to "<end_date>"
    And I click "Filter"
    Then I should see the activity statistics for the selected date range
      | Metric                | Value         |
      | Active Users          | <filtered_active_users> |
      | Average Engagement    | <filtered_engagement>   |
@tag3
  Scenario: Search for user activity by user ID
    Given I am on the "User Activity Dashboard"
    When I enter a user ID into the "Search by User ID" field
    And I click "Search"
    Then I should see the activity details for the selected user
      | Metric                | Value             |
      | Total Logins          | <user_logins>     |
      | Average Session Time  | <user_session>    |
      | Last Active Date      | <last_active>     |
@tag4
  Scenario: Search for user activity by user name
    Given I am on the "User Activity Dashboard"
    When I enter a user name into the "Search by Name" field
    And I click "Search"
    Then I should see the activity details for the selected user
      | Metric                | Value             |
      | Total Logins          | <user_logins>     |
      | Average Session Time  | <user_session>    |
      | Last Active Date      | <last_active>     |
@tag4
  Scenario: Export user activity report
    Given I am on the "User Activity Dashboard"
    When I click "Export Report"
    Then a downloadable report should be generated
    And the file name should include the current date in the format "User_Activity_Report_<yyyy-MM-dd>.csv"
