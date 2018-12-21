Feature: Foo
  Scenario: Creating a Foo
    Given there is a name "Bar"
    When name is given to Foo api
    Then a Foo is created with name "Bar"
  Scenario Outline: Creating many Foos
    Given there is a name "<name>"
    When name is given to Foo api
    Then a Foo is created with name "<name>"
    Examples:
    |name|
    |Foo |
    |Bar |