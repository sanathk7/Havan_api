Feature: Maps API POST Request
  @order1
  Scenario: Verify POST request with JSON file
    Given I load the base URL from config
    And I set up the Maps API request
    And I load the request body from "src/test/resources/data/postRequestBody.json"
    And I load the query parameters from "src/test/resources/data/postQueryParams.json"
    When I send a POST request to "/add/json"
    Then the response status code should be 200
    And the response should contain "status" with value "OK"
    And I log the response body