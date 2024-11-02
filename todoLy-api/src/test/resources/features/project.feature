@Project
Feature: Todo-Ly CRUD-Projects

  Background: Setup ApiRequestHandler
    Given I set apiRequestHandler with proper credential

  @Project_001 @deleteProject
  Scenario: Crete project
    When I create a project with name "SergioProject from cucumber"
    Then I should see field "Content" with value "SergioProject from cucumber"
    And I validate createProject response schema

  @Project_002 @deleteProject
  Scenario: Get a project
    Given I create a board with name "Get Boards API cucumber"
    And I should see field "name" with value "Get Boards API cucumber"
    When I get a board with "boardId"
    Then I should see field "name" with value "Get Boards API cucumber"

  @Project_003 @createProject @deleteProject
  Scenario: Update a project
    When I update board name with "API Board update name"
    Then I should see field "name" with value "API Board update name"

  @Project_003 @createProject
  Scenario: Delete a project
    When I delete a board with "boardId"
  Then I validate that status code of response is 200
#    Then I should see response body as "{\n\"_value\": null\n}"
