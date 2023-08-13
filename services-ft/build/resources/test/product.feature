Feature: Product Service Test

  Background: User is logged in
    Given I have valid user with id 1
    When I call user login api
    Then HTTP response code is 200
    And HTTP response body is not empty or null

  Scenario: Product is created successfully
    Given product table is empty
    When I post product api with the following
      """
      {"name": "foo", "calories": 100}
      """
    Then HTTP response code is 200
    And HTTP response body contains the JSON key name with string value foo
    And HTTP response body contains the JSON key calories with int value 100

  Scenario: Product get successfully
    Given product table has products
      | 1 | fooz | 100 |
      | 2 | barz | 200 |
    When I get product api
    Then HTTP response code is 200
    And HTTP response body contains the JSON key [0].name with string value fooz
    And HTTP response body contains the JSON key [0].calories with int value 100
    And HTTP response body contains the JSON key [1].name with string value barz
    And HTTP response body contains the JSON key [1].calories with int value 200

  Scenario: Product get by id successfully
    Given product table has products
      | 1 | fooz | 100 |
      | 2 | barz | 200 |
    When I get product api /product/1
    Then HTTP response code is 200
    And HTTP response body contains the JSON key name with string value fooz
    And HTTP response body contains the JSON key calories with int value 100
