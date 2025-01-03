Feature: Content Management

  # Admin Scenarios

  Scenario: Approve a Submitted Fitness Tip
    Given I am logged in as an admin
    When I approve a submitted fitness tip titled "5-Minute Stretching Routine"
    Then the tip "5-Minute Stretching Routine" should be publicly visible on the platform

  Scenario: Reject a Submitted Fitness Tip
    Given I am logged in as an admin
    When I reject a submitted fitness tip titled "5-Minute Stretching Routine" with reason "Lack of sufficient details"
    Then the submitter should receive a notification "Lack of sufficient details"
    And the tip "5-Minute Stretching Routine" should not be publicly visible

  Scenario: Delete a Publicly Visible Fitness Tip
    Given I am logged in as an admin
    And the fitness tip "Beginner Yoga Postures" is publicly visible
    When I delete the fitness tip "Beginner Yoga Postures"
    Then the tip "Beginner Yoga Postures" should no longer be visible on the platform

  Scenario: View Feedback for a Fitness Tip
    Given I am logged in as an admin
    When I view feedback for the fitness tip titled "Home Workout Plan"
    Then I should see all user comments and ratings for "Home Workout Plan"

  Scenario: Remove Inappropriate Feedback
    Given I am logged in as an admin
    And there is a feedback comment "Inappropriate language" on the fitness tip titled "Healthy Morning Routine"
    When I remove the inappropriate feedback on "Healthy Morning Routine"
    Then the feedback should no longer be visible under the "Healthy Morning Routine" tip

  Scenario: Respond to a Feedback Question
    Given I am logged in as an admin
    And there is a feedback question "Can I do this routine with back pain?" on the fitness tip "5-Minute Stretching Routine"
    When I respond to the feedback with "Please consult a physician before attempting."
    Then my response should be visible under the feedback question

 