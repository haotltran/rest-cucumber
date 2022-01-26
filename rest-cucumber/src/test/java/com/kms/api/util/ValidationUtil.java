package com.kms.api.util;

import static com.kms.api.util.RestUtil.mapPojoToString;

import groovy.util.logging.Slf4j;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;

@Slf4j
public class ValidationUtil {

  /** ************************** Response Validation *************************** */

  /**
   * Http status code validation
   *
   * @param res Http response is returned after sending a request to specific service
   * @param expectedStatusCode any http status code that is returned expectation
   */
  public static void validateStatusCode(Response res, int expectedStatusCode) {
    res.then().statusCode(expectedStatusCode);
  }

  /**
   * Convert Pojo objects to Json string and compare them for the validation
   *
   * @param expectedObject The object is expected
   * @param actualObject The object is got from executing the system
   * @param <T> generic type of the objects
   */
  public static <T> void validatePojoObjects(T expectedObject, T actualObject) {
    Assert.assertEquals(mapPojoToString(expectedObject), (mapPojoToString(actualObject)));
  }

  public static <T> void validatePojoObjects(
      double expectedObject, double actualObject, double delta) {
    Assert.assertEquals(expectedObject, actualObject, delta);
  }

  /**
   * String value validation for being not empty string and null
   *
   * @param str string value of an attribute or attribute name
   */
  public static void validateStringValueNotEmptyOrNull(String str) {
    Assert.assertTrue(!str.isEmpty());
  }

  /**
   * String value validation for being included into a text
   *
   * @param text full text
   * @param str string is included into the text
   */
  public static void validateStringIncluded(String text, String str) {
    Assert.assertTrue(text.contains(str));
  }

  /**
   * String value validation for text equal
   *
   * @param expectedStr expected text
   * @param actualStr actual text
   */
  public static void validateStringEqual(Object expectedStr, Object actualStr) {
    // Assert.assertTrue(Objects.equals(expectedStr, actualStr));
    Assert.assertEquals(expectedStr, actualStr);
  }

  /**
   * Validate number is greater than 0
   *
   * @param matcher the number is used to compare with actual number
   * @param actualNum actual number
   */
  public static void validateNumberGreaterThanOrEqual(int matcher, int actualNum) {
    Assert.assertThat(actualNum, Matchers.greaterThanOrEqualTo(matcher));
  }
}
