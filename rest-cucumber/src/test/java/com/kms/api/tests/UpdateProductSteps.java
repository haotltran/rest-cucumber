package com.kms.api.tests;

import com.kms.api.model.LaptopBag;
import com.kms.api.requests.RequestFactory;
import com.kms.api.util.RequestBuilder;
import com.kms.api.util.ValidationUtil;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.*;

import static com.kms.api.util.RestUtil.mapRestResponseToPojo;

public class UpdateProductSteps extends TestBase{
    private Object requestPayload;
    private LaptopBag reqUpdateLaptop;
    private LaptopBag resUpdateLaptop;
    private int id;
    private Response res;


    @When("^I perform the PUT request with id and BrandName as \"([^\"]*)\", Features as \"([^\"]*)\", LaptopName as \"([^\"]*)\"$")
    public void iPerformThePUTRequestWithIdAndBrandNameAsFeaturesAsLaptopNameAs(
            String brandName, String feature, String laptopName) {
        String[] array = feature.split(",");
        List<String> lst = Arrays.asList(array);
        id = AddProductSteps.resAddLaptop.getId();

        requestPayload = RequestBuilder.requestPayload(laptopName, brandName, id, lst);
        reqUpdateLaptop = (LaptopBag) requestPayload;
        res = RequestFactory.updateProduct(AddProductSteps.path, reqUpdateLaptop);
        resUpdateLaptop = mapRestResponseToPojo(res, LaptopBag.class);
    }

    @And("^Details should get updated$")
    public void detailsShouldGetUpdated() {
        ValidationUtil.validateStringEqual(resUpdateLaptop.getId(), id);
        ValidationUtil.validateStringEqual(resUpdateLaptop.getBrandName(), reqUpdateLaptop.getBrandName());
        ValidationUtil.validateStringEqual(resUpdateLaptop.getLaptopName(), reqUpdateLaptop.getLaptopName());
        ValidationUtil.validateStringEqual(resUpdateLaptop.getFeatures(), reqUpdateLaptop.getFeatures());
    }

    @When("^I perform the PUT request with invalid id and BrandName as \"([^\"]*)\", Features as \"([^\"]*)\", LaptopName as \"([^\"]*)\"")
    public void iPerformThePUTRequestWithInvalidIdAndBrandNameAsFeaturesAsLaptopNameAs(
            String brandName, String feature, String laptopName) {
        String[] array = feature.split(",");
        List<String> lst = Arrays.asList(array);
        id = (int) (Math.random() * 10000);

        try {
            requestPayload = RequestBuilder.requestPayload(laptopName, brandName, id, lst);
            reqUpdateLaptop = (LaptopBag) requestPayload;
            res = RequestFactory.updateProduct(AddProductSteps.path, reqUpdateLaptop);
            resUpdateLaptop = mapRestResponseToPojo(res, LaptopBag.class);
        }
        catch (Exception ex){
            System.out.println("The response body return null");
        }
    }

    @Then("^the status code \"([^\"]*)\" should be returned$")
    public void theStatusCodeShouldBeReturned(String statusCode) {
        ValidationUtil.validateStatusCode(res, Integer.parseInt(statusCode));
    }

    @And("^No response data returns$")
    public void noResponseDataReturns() {
        ValidationUtil.validateStringEqual("", res.thenReturn().getBody().asString());
    }

    @After ("@update")
    public void tearDown(){
        res = RequestFactory.deleteProduct("delete/"+id);
        ValidationUtil.validateStringEqual(String.valueOf(id), res.thenReturn().getBody().asString());
    }
}