Feature: Maps API DELETE Request

  Scenario: Verify DELETE request with place_id
    Given I load the base URL from config
    And I set up the Maps API request
    And I load the place_id from "src/test/resources/data/placeId.json"
    And I load the query parameters from "src/test/resources/data/deleteQueryParams.json"
    When I send a DELETE request to "/delete/json" with body from "src/test/resources/data/deleteRequestBody.json"
    Then the response status code should be 200
    And the response should contain "status" with value "OK"
    And I log the response body

