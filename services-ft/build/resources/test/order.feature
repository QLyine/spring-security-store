Feature: Product Service Test

  Background: User is logged in
    Given I have valid user with id 1
    When I call user login api
    Then HTTP response code is 200
    And HTTP response body is not empty or null

  Scenario: Order is created successfully
    Given order table is empty
    When I post order api with the following
      """
      {"userId": 1, "productId": [1 , 10]}
      """
    Then HTTP response code is 200
    And HTTP response body contains the JSON key userId with int value 1
