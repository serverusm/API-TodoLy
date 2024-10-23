Feature: calcauladora

  Background:
    Given I set a mes sage

  @Calculadora
  Scenario Outline: Suma
    Given I have "number1" with value <number1Value>
    And I have "number2" with value <number2Value>
    When I perform the "<method>" of number1 and number2
    Then I get result of <result>
    Examples:
      | number1Value | number2Value | result | method      |
      | 12           | 2            | 14     | addition    |
      | 12           | 12           | 24     | addition    |
      | 1            | 1            | 2      | addition    |
      | 12           | 12           | 0      | subtraction |


#  Scenario: Resta
#    Given I have "number1" with value 12
#    And I have "number2" with value 12
#    When I perform the subtraction of number1 and number2
#    Then I get result of 0

#  Scenario: Suma 3
#    Given I have "number1" with value 1
#    And I have "number2" with value 1
#    When I perform the addition of number1 and number2
#    Then I get result of 2