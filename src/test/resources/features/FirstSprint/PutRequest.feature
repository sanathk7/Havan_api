Feature: Maps API PUT Request

  Scenario: Verify PUT request with place_id
    Given I load the base URL from config
    And I set up the Maps API request
    And I load the place_id from "src/test/resources/data/placeId.json"
    And I load the query parameters from "src/test/resources/data/putQueryParams.json"
    When I send a PUT request to "/update/json" with body from "src/test/resources/data/putRequestBody.json"
    Then the response status code should be 200
    And the response should contain "msg" with value "Address successfully updated"
    And I log the response body

