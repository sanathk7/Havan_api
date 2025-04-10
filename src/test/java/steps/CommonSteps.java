package steps;

import io.restassured.RestAssured;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;
import utils.PlaceIdUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.json.JSONObject;

import com.aventstack.extentreports.ExtentTest;

import context.TestContext;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.logging.Logger;

import hooks.Hooks;

public class CommonSteps {
    private static final Logger LOGGER = Logger.getLogger(CommonSteps.class.getName());
    protected RequestSpecification request;
    protected Response response;
    private final TestContext context;

    public CommonSteps(TestContext context) {
        this.context = context;
    }

//    @Given("I load the base URL from {string}")
//    public void loadBaseUrl(String filePath) throws IOException {
//        String fullPath = filePath;
//        LOGGER.info("Loading base URL from: " + fullPath);
//        String jsonContent = new String(Files.readAllBytes(Paths.get(fullPath)));
//        JSONObject config = new JSONObject(jsonContent);
//        RestAssured.baseURI = config.getString("baseUrl");
//        
//        RestAssured.baseURI = ConfigReader.getBaseUrl();
//
//        LOGGER.info("Base URL loaded: " + RestAssured.baseURI);
//    }
    
    @Given("I load the base URL from config")
    public void loadBaseUrlFromConfig() throws IOException {
        String baseUrl = ConfigReader.getBaseUrl();
        RestAssured.baseURI = baseUrl;
        LOGGER.info("Base URL loaded from config: " + baseUrl);
    }


    @Given("I set up the Maps API request")
    public void setupRequest() {
        LOGGER.info("Setting up the Maps API request");
        context.request = given().header("Content-Type", "application/json");
    }
    
    @Given("I load the place_id from {string}")
    public void loadPlaceId(String filePath) throws IOException {
        LOGGER.info("Loading place_id from: " + filePath);
        String placeId = PlaceIdUtils.getPlaceId(); // or load from file
        context.setPlaceId(placeId); // Save in context
        LOGGER.info("Place_id loaded: " + placeId);
    }
    
    @Given("I load the query parameters from {string}")
    public void loadQueryParams(String filePath) throws IOException {
        LOGGER.info("Loading query parameters from: " + filePath);
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        context.queryParams = new JSONObject(jsonContent);
        LOGGER.info("Query parameters loaded: " + context.queryParams.toString());
    }


    @Then("the response status code should be {int}")
    public void verifyStatusCode(int statusCode) {
        LOGGER.info("Verifying response status code: " + statusCode);
        context.response.then().statusCode(statusCode);
    }

    @Then("the response should contain {string} with value {string}")
    public void verifyResponse(String key, String value) {
        LOGGER.info("Verifying response contains " + key + " with value " + value);
        
        context.response.then().body(key, equalTo(value));
    }

    @Then("I log the response body")
    public void logResponseBody() {
        LOGGER.info("Logging the response body");

        if (context.response != null) {
            String responseBody = context.response.getBody().asString();
            LOGGER.info("Response body: " + responseBody);

            ExtentTest test = Hooks.getTest();
            if (test != null) {
                test.info("Response Body: <pre>" + responseBody + "</pre>");
            } else {
                LOGGER.warning("ExtentTest was null — could not log to ExtentReports");
            }
        } else {
            LOGGER.warning("Response was null — nothing to log");
        }
    }

}