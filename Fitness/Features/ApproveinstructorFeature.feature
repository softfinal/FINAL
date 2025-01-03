
@tag
Feature: Approve New Instructor Registrations
Admin should be able to review and approve new instructor registrations.

  @tag1
  Scenario: Approve New Instructor
  Admin should be able to approve a new instructor registration after reviewing the details.

    Given I am logged in as an admin
    And there is a pending instructor registration
    When I review the registration details
    And I approve the new instructor registration
    Then the instructor should be added to the system as an active user
    And I should see a success message confirming the approval

  @tag2
  Scenario: Reject New Instructor
  Admin should be able to reject an instructor registration if they are not suitable.

    Given I am logged in as an admin
    And there is a pending instructor registration
    When I review the registration details
    And I reject the new instructor registration
    Then the instructor should not be added to the system
    And I should see a rejection message confirming the action

  @tag3
  Scenario: View Pending Instructor Registrations
  Admin should be able to see a list of pending instructor registrations for review.

    Given I am logged in as an admin
    When I navigate to the "Pending Registrations" section
    Then I should see a list of all pending instructor registrations
    And I should be able to review the details of each registration

  @tag4
  Scenario: Notify Instructor After Approval
  The system should send a notification to the instructor after their registration is approved.

    Given I am logged in as an admin
    And I approve a new instructor registration
    When the registration is approved
    Then the instructor should receive a notification email confirming their approval
    And I should see a message confirming the email has been sent

  @tag5
  Scenario: Notify Instructor After Rejection
  The system should notify the instructor if their registration is rejected.

    Given I am logged in as an admin
    And I reject a new instructor registration
    When the registration is rejected
    Then the instructor should receive a notification email about the rejection
    And I should see a message confirming the rejection email has been sent
