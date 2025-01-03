#Auther:Bana
@tag
Feature: Account Management
Account Management should allow clients to create, update, and view their personal details and dietary preferences.

  @tag1
  Scenario: Create a new client profile with personal details
  A client should be able to create a profile with personal details such as age and fitness goals.

    Given I am logged in as a Client
    When I create a profile with my personal details including age and fitness goals
    Then my personal details should be saved successfully in my profile

  @tag2
  Scenario: Update personal details in my profile
  A client should be able to update their personal details such as age and fitness goals.

    Given I am logged in as a Client
    When I update my personal details such as age and fitness goals
    Then my updated personal details should be saved successfully in my profile

  @tag3
  Scenario: Set dietary preferences in my profile
  A client should be able to set dietary preferences or restrictions (e.g., vegetarian, gluten-free).

    Given I am logged in as a Client
    When I set my dietary preferences or restrictions (e.g., vegetarian, gluten-free)
    Then my dietary preferences should be saved successfully in my profile

  @tag4
  Scenario: Edit dietary preferences or restrictions
  A client should be able to edit dietary preferences or restrictions (e.g., add lactose intolerance).

    Given I am logged in as a Client
    When I edit my dietary preferences or restrictions (e.g., add lactose intolerance)
    Then my edited dietary preferences should be updated successfully in my profile

  @tag5
  Scenario: View personal details and dietary preferences
  A client should be able to view their personal details, fitness goals, and dietary preferences.

    Given I am logged in as a Client
    When I view my profile
    Then I should be able to see my personal details, fitness goals, and dietary preferences 
    
    
    @tag6
Scenario: Attempt to create a profile with invalid details
  A client should not be able to create a profile with invalid data (e.g., non-numeric age).

    Given I am logged in as a Client
    When I attempt to create a profile with invalid details such as age = "abc"
    Then I should see an error message indicating invalid input
    And my profile should not be saved
    
    @tag7
Scenario: Attempt to create a profile with missing mandatory details
  A client should not be able to create a profile without providing mandatory fields.

    Given I am logged in as a Client
    When I attempt to create a profile without providing my age
    Then I should see an error message indicating that age is required
    And my profile should not be saved
    
    @tag8
Scenario: Delete my profile
  A client should be able to delete their profile permanently.

    Given I am logged in as a Client
    When I choose to delete my profile
    Then my profile should be removed from the system
    And I should no longer be able to access my account
    
    @tag9
Scenario: Unauthorized access to another client's profile
  A user should not be able to view or edit another client's profile.

    Given I am logged in as a Client
    When I attempt to access another client's profile
    Then I should receive an error message indicating insufficient permissions
    And I should not be able to view or edit their profile
    
    