
@tag
Feature: User Management
Admin should be able to manage user accounts for instructors and clients.

  @tag1
  Scenario: Add New User
  Admin should be able to add new users by entering their details like name, email, role, and password.

    Given I am logged in as an admin
    When I add a new user with valid details
    Then the new user should be created successfully
    And I should see a success message confirming the addition

  @tag2
  Scenario: Update User Details
  Admin should be able to update a user's information such as role, contact details, and password.

    Given I am logged in as an admin
    When I update the user details with new information
    Then the updated user details should be saved
    And I should see a confirmation message with the updated information

  @tag3
  Scenario: Deactivate User Account
  Admin should be able to deactivate a user’s account to prevent further access.

    Given I am logged in as an admin
    When I deactivate a user account
    Then the user should be deactivated
    And I should see a success message confirming the deactivation

  
  @tag4
Scenario: User Already Exists
  Given I am logged in as an admin
  When I try to add a new user with an existing email
  Then I should be notified that the email is already registered
  And I should be asked to choose a different email address

  
  @tag5
Scenario: Invalid Email Format
  Given I am logged in as an admin
  When I try to add a user with an invalid email address
  Then I should be notified that the email format is invalid
  And I should be prompted to enter a valid email address

    
   

