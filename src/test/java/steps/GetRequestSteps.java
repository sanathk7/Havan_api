package steps;

import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import context.TestContext;

import java.util.logging.Logger;

public class GetRequestSteps{
    private static final Logger LOGGER = Logger.getLogger(GetRequestSteps.class.getName());
    private final TestContext context;
    
    public GetRequestSteps(TestContext context) {
        this.context = context;
    }

    @When("I send a GET request to {string}")
    public void sendGetRequest(String endpoint) {
        String placeId = context.getPlaceId(); // Get from context
        String fullUrl = RestAssured.baseURI + endpoint + "?key=qaclick123&place_id=" + placeId;
        LOGGER.info("Sending GET request to: " + fullUrl);
        context.response = context.request.when().get(fullUrl);
        LOGGER.info("Response received: " + context.response.asPrettyString());
    }
}