Feature: User Service Test

  Scenario: User is registred successfully
    Given user foo does not exist
    When I call user register api with the following
      """
      {"username": "foo", "password": "bar"}
      """
    Then HTTP response code is 200

  Scenario: User logins successfully
    Given I have valid user with id 1
    When I call user login api
    Then HTTP response code is 200
    And HTTP response body is not empty or null

  Scenario: Fetch valid user successfully
    Given I have valid user with id 1
    When I call user login api
    Then HTTP response code is 200
    And HTTP response body is not empty or null
    When I call api /user/1
    Then HTTP response code is 200
    And HTTP response body contains the JSON key username with string value valid
    And HTTP response body contains the JSON key password
    And HTTP response body contains the JSON key calories with int value 0

  Scenario: Fetch valid user with wrong auth
    Given I have valid user with id 1
    When I call user login api
    Then HTTP response code is 200
    And HTTP response body is not empty or null
    When I call api /user/1 with wrong auth
    Then HTTP response code is 401
