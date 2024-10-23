@Board
Feature: Board

  Background: Setup ApiRequestHandler
    Given I set apiRequestHandler with proper credential

  @Board_001 @deleteBoard
  Scenario: Crete board
    When I create a board with name "Sergio Boards API cucumber"
    Then I should see field "name" with value "Sergio Boards API cucumber"
    And I validate createBoard response schema

  @Board_002 @deleteBoard
  Scenario: Get a board
    Given I create a board with name "Get Boards API cucumber"
    And I should see field "name" with value "Get Boards API cucumber"
    When I get a board with "boardId"
    Then I should see field "name" with value "Get Boards API cucumber"

  @Board_003 @createBoard @deleteBoard
  Scenario: Update a board
    When I update board name with "API Board update name"
    Then I should see field "name" with value "API Board update name"

  @Board_003 @createBoard
  Scenario: Delete a board
    When I delete a board with "boardId"
  Then I validate that status code of response is 200
#    Then I should see response body as "{\n\"_value\": null\n}"
