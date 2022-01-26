package com.kms.api.tests;

import static com.kms.api.util.RestUtil.mapRestResponseToPojo;

import com.fasterxml.jackson.databind.introspect.POJOPropertiesCollector;
import com.kms.api.model.LaptopBag;
import com.kms.api.requests.RequestFactory;
import com.kms.api.util.RequestBuilder;
import com.kms.api.util.ValidationUtil;
import io.cucumber.java.After;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.List;

public class AddProductSteps extends TestBase {

  public static String path = "";
  private Object requestPayload;
  private LaptopBag reqAddLaptop;
  public static LaptopBag resAddLaptop;
  private int id;
  private Response res;

  @Given("^the path \"([^\"]*)\" to the endpoint$")
  public void thePathToAddTheProduct(String path) {
    this.path = path;
  }

  @And("^the payload of the request with BrandName as \"([^\"]*)\", Features as \"([^\"]*)\", LaptopName as \"([^\"]*)\"$")
  public void thePayloadOfTheRequestWithBrandNameAsFeaturesAsLaptopNameAs(
          String brandName, String feature, String laptopName) {
    String[] array = feature.split(",");
    List<String> lst = Arrays.asList(array);
    id = (int) (Math.random() * 10000);
    requestPayload = RequestBuilder.requestPayload(laptopName, brandName, id, lst);
  }

  @When("^I perform the request to add new product$")
  public void iPerformTheRequestToApplication() {
    try {
      reqAddLaptop = (LaptopBag) requestPayload;
      res = RequestFactory.addProduct(path, reqAddLaptop);
      resAddLaptop = mapRestResponseToPojo(res, LaptopBag.class);
    } catch (Exception ex) {
      res = RequestFactory.addProduct(path, requestPayload);
    }
  }

  @Then("^the status code \"([^\"]*)\" should return$")
  public void theStatusCodeShouldReturn(String statusCode) {
      ValidationUtil.validateStatusCode(res, Integer.parseInt(statusCode));
  }

  @And("^the product is added successfully with an integer Id$")
  public void theProductIsAddedSuccessfullyWithAnIntegerId() {
    ValidationUtil.validateStringEqual(resAddLaptop.getId(), id);
  }

  @But("^I supply invalid json payload$")
  public void iSupplyInvalidJsonPayload() {
    requestPayload = "Invalid Json";
  }

  @After("@add")
  public void tearDown() {
    res = RequestFactory.deleteProduct("delete/" + id);
    ValidationUtil.validateStringEqual(String.valueOf(id), res.thenReturn().getBody().asString());
  }
}
