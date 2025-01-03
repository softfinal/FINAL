#Author:Bana
@tag
Feature: Feedback and Reviews
Feedback and Reviews should allow clients to rate, review, and suggest improvements for completed programs.

  @tag1
  Scenario: Rate a completed program
  A client should be able to rate a completed program.

    Given I have completed a program
    When I rate the program with a score of 5 stars
    Then the rating should be saved successfully
    And I should be able to view my rating on the program page

  @tag2
  Scenario: Write a review for a completed program
  A client should be able to write a review for a completed program.

    Given I have completed a program
    When I write a review with the content "Great program, helped me lose weight"
    Then the review should be saved successfully
    And I should be able to view my review on the program page

  @tag3
  Scenario: Submit a suggestion for improvement to an instructor
  A client should be able to submit a suggestion for improvement to an instructor.

    Given I have completed a program
    When I submit a suggestion with the content "Would love to have more flexibility in the schedule"
    Then the suggestion should be saved successfully
    And the instructor should be notified of my suggestion

  @tag4
  Scenario: View all ratings and reviews for a completed program
  A client should be able to view all ratings and reviews for a program.

    Given I have completed a program
    When I view the program page
    Then I should be able to see all ratings and reviews from other clients

  @tag5
  Scenario: Edit my review for a completed program
  A client should be able to edit a previously written review.

    Given I have completed a program
    And I have already written a review
    When I edit my review to change the content to "Excellent program, very helpful"
    Then my review should be updated successfully
    And I should be able to see the updated review on the program page
    
    @tag6
Scenario: Submit a negative or inappropriate rating
  A client should be informed if their rating is deemed inappropriate or if they provide a rating below acceptable standards.

    Given I have completed a program
    When I rate the program with a score of 1 star and a comment "Terrible, didn't help me at all"
    Then the rating should be flagged for review by the system
    And I should be notified that my rating is under review
    
    @tag7
Scenario: Write an inappropriate review
  A client should be informed if their review is deemed inappropriate or violates guidelines.

    Given I have completed a program
    When I write a review with content "This program was garbage!"
    Then the review should be flagged as inappropriate
    And I should be notified that my review has been rejected due to inappropriate content
    @tag8
Scenario: Submit a duplicate review or rating
  A client should be informed if they try to submit a duplicate rating or review for the same program.

    Given I have already rated a program with 5 stars
    When I try to submit the same 5-star rating again
    Then I should be informed that I have already rated this program
    And the system should not allow the duplicate rating or review
    
    @tag9
Scenario: Submit an incomplete or vague review
  A client should be informed if they submit a vague or incomplete review.

    Given I have completed a program
    When I submit a review with the content "Good program"
    Then the system should prompt me to provide more details if the review is too vague
    And the review should not be saved until I provide a more detailed review
    
    @tag10
Scenario: Admin review of ratings and reviews before publishing
  The system should allow an admin to review and approve ratings and reviews before they are published.

    Given I have submitted a rating and review for a program
    When the admin reviews my rating and review
    Then the admin should approve or reject the review based on guidelines
    And I should be notified if my review is published or rejected
    