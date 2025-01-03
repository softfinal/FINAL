Feature: Subscription Management

  # Admin Scenarios

  Scenario: Add a New Subscription Plan
    Given I am logged in as an admin
    When I add a new subscription plan with the following details:
      | Plan Name | Type     | Price   | Duration  |
      | Premium   | Client   | $50/mo | Monthly   |
    Then the new subscription plan "Premium" for "Client" should be available for selection

  Scenario: Update an Existing Subscription Plan
    Given I am logged in as an admin
    And a subscription plan "Basic" for "Instructor" exists
    When I update the subscription plan "Basic" with the new price "$30/mo"
    Then the subscription plan "Basic" should reflect the updated price "$30/mo"

  Scenario: Deactivate a Subscription Plan
    Given I am logged in as an admin
    And a subscription plan "Premium" for "Client" exists
    When I deactivate the subscription plan "Premium"
    Then the subscription plan "Premium" should no longer be available for new users
    And existing users with "Premium" plans should retain their benefits until the end of the billing cycle

  Scenario: View All Subscription Plans
    Given I am logged in as an admin
    When I view all subscription plans
    Then I should see a list of all active plans with details:
      | Plan Name  | Type        | Price   | Duration  |
      | Basic      | Client      | $20/mo | Monthly   |
      | Premium    | Client      | $50/mo | Monthly   |
      | Instructor | Instructor  | $40/mo | Monthly   |

  # User Scenarios

  Scenario: Upgrade to a Premium Subscription
    Given I am logged in as a user with a "Basic" subscription
    When I upgrade to a "Premium" subscription
    Then my subscription type should be updated to "Premium"
    And I should be charged $50 for the current billing cycle

  Scenario: Downgrade to a Basic Subscription
    Given I am logged in as a user with a "Premium" subscription
    When I downgrade to a "Basic" subscription
    Then my subscription type should be updated to "Basic"
    And the changes should take effect in the next billing cycle

  Scenario: Cancel a Subscription
    Given I am logged in as a user with an active subscription
    When I cancel my subscription
    Then my subscription should remain active until the end of the billing cycle
    And I should receive a confirmation message "Your subscription has been canceled and will end on <end_date>."

  Scenario: View Available Subscription Plans
    Given I am logged in as a user
    When I view the available subscription plans
    Then I should see a list of plans with details:
      | Plan Name  | Type     | Price   | Duration  |
      | Basic      | Client   | $20/mo | Monthly   |
      | Premium    | Client   | $50/mo | Monthly   |

  Scenario: Receive Notification for Expiring Subscription
    Given I am logged in as a user with a subscription expiring in 7 days
    When the system generates notifications
    Then I should receive a message "Your subscription will expire on <end_date>. Renew now to avoid interruption."
