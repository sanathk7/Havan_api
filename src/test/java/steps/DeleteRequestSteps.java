package steps;


import io.cucumber.java.en.When;
import io.restassured.RestAssured;


import org.json.JSONObject;
import java.util.logging.Logger;

import context.TestContext;

public class DeleteRequestSteps {
    private static final Logger LOGGER = Logger.getLogger(DeleteRequestSteps.class.getName());
    private final TestContext context;
    
    public DeleteRequestSteps(TestContext context) {
        this.context = context;
    }
    
    @When("I send a DELETE request to {string} with body from {string}")
    public void sendDeleteRequestWithBody(String endpoint, String bodyFilePath){
        String fullUrl = RestAssured.baseURI + endpoint + "?key=" + context.queryParams.getString("key");
        LOGGER.info("Sending DELETE request to: " + fullUrl);

        JSONObject requestBody = new JSONObject();
        requestBody.put("place_id", context.getPlaceId());
        
        LOGGER.info("Deleting place_id: " + context.getPlaceId());
        LOGGER.info("Request Body: " + requestBody.toString());

        context.response = context.request.body(requestBody.toString()).when().delete(fullUrl);

        LOGGER.info("Raw Response: " + context.response.getBody().asPrettyString());    
    }
}