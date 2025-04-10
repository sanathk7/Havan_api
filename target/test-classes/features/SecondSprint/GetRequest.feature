Feature: Maps API GET Request
  @order2
  Scenario: Verify GET request with place_id
    Given I load the base URL from config
    And I set up the Maps API request
    And I load the place_id from "src/test/resources/data/placeId.json"
    When I send a GET request to "/get/json"
    Then the response status code should be 200
    And I log the response body