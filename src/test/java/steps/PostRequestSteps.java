package steps;

import utils.PlaceIdUtils;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.logging.Logger;

import context.TestContext;

public class PostRequestSteps {
    private static final Logger LOGGER = Logger.getLogger(PostRequestSteps.class.getName());
    private String requestBody;
    private final TestContext context;
    
    public PostRequestSteps(TestContext context) {
        this.context = context;
    }

    @Given("I load the request body from {string}")
    public void loadRequestBody(String filePath) throws IOException {
        LOGGER.info("Loading request body from: " + filePath);
        requestBody = new String(Files.readAllBytes(Paths.get(filePath)));
        LOGGER.info("Request body loaded: " + requestBody);
    }

    @When("I send a POST request to {string}")
    public void sendPostRequest(String endpoint) {
        String fullUrl = RestAssured.baseURI + endpoint + "?key=" + context.queryParams.getString("key");
        LOGGER.info("Sending POST request to: " + fullUrl);
        context.response = context.request.queryParams(context.queryParams.toMap()).body(requestBody).when().post(fullUrl);
        LOGGER.info("Response received: " + context.response.asString());
        
        // Log the response body for debugging
        LOGGER.info("Response body: " + context.response.getBody().asPrettyString());
        
        // Extract and save place_id
        String placeId = context.response.jsonPath().getString("place_id");
        LOGGER.info("Extracted place_id: " + placeId);
        try {
            PlaceIdUtils.savePlaceId(placeId);
            LOGGER.info("place_id saved successfully");
        } catch (IOException e) {
            LOGGER.severe("Error saving place_id: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
