@Project
Feature: Todo-Ly CRUD-Projects

  Background: Setup ApiRequestHandler
    Given I set apiRequestHandler with proper credential

  @Project_001 @deleteProjects
  Scenario: Crete project
    When I create a project with name "SergioProject from cucumber"
    Then I should see field "Content" with value "SergioProject from cucumber"
    And I validate createProject response schema

  @Project_002 @deleteProjects
  Scenario: Get a project
    Given I create a project with name "GetProject from cucumber"
    And I should see field "Content" with value "GetProject from cucumber"
    When I get a project with "projectId"
    Then I should see field "Content" with value "GetProject from cucumber"

  @Project_003 @createProjects @deleteProjects
  Scenario: Update a project
    When I update project name with "Project Update from Cucumber"
    Then I should see field "Content" with value "Project Update from Cucumber"

  @Project_003 @createProjects
  Scenario: Delete a project
    When I delete a board with "boardId"
  Then I validate that status code of response is 200
#    Then I should see response body as "{\n\"_value\": null\n}"
