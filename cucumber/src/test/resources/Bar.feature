Feature: Bar
  Scenario: Creating a Bar
    Given there is a name "Foo"
    When name is given to Bar api
    Then a Bar is created with name "Foo"
  Scenario Outline: Creating many Bars
    Given there is a name "<name>"
    When name is given to Bar api
    Then a Bar is created with name "<name>"
    Examples:
      |name|
      |Foo |
      |Bar |
