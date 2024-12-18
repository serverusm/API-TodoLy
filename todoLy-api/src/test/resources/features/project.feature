@Project
Feature: Todo-Ly CRUD-Projects

  Background: Setup ApiRequestHandler
#    Given I set apiRequestHandler with proper credential
    Given I set auth "sergiomamani2014@gmail.com" and "Krattosmamani9/"

  @Project_001
  Scenario: Crete project
#    When I create a project with name "SergioProject from cucumber"
    When I create a project with:
      """
      { "Content":"SergioProject from cucumber", "Icon":5 }
      """
    Then I should see field "Content" with value "SergioProject from cucumber"
    Then I validate that status code of response is 200
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

  @Project_004 @createProjects
  Scenario: Delete a project
    When I delete a project with "projectId"
    Then I validate that status code of response is 200
#    Then I should see response body as "{\n\"_value\": null\n}"
