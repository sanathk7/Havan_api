package steps;

import io.cucumber.java.en.When;

import io.restassured.RestAssured;

import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.logging.Logger;

import context.TestContext;

public class PutRequestSteps {
    private static final Logger LOGGER = Logger.getLogger(PutRequestSteps.class.getName());
    private final TestContext context;
    
    public PutRequestSteps(TestContext context) {
        this.context = context;
    }
    
    @When("I send a PUT request to {string} with body from {string}")
    public void sendPutRequestWithBodyFromFile(String endpoint, String filePath) throws IOException {
        String fullUrl = RestAssured.baseURI + endpoint + "?key=" + context.queryParams.getString("key");
        LOGGER.info("Sending PUT request to: " + fullUrl);

        // Read JSON from file
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject requestBody = new JSONObject(jsonContent);

        // Replace placeholder with actual place_id from context
        requestBody.put("place_id", context.getPlaceId());
        requestBody.put("key", context.queryParams.getString("key")); // update key just in case

        context.response = context.request.body(requestBody.toString()).when().put(fullUrl);
        
        LOGGER.info("Response received: " + context.response.getBody().asPrettyString());
    }

}