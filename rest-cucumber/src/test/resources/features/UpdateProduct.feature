@product
Feature: Update an existing product

  Background:
    Given the path "add" to the endpoint
    And the payload of the request with BrandName as "Dell", Features as "8GB RAM,1TB Hard Drive", LaptopName as "Latitude"
    When I perform the request to add new product
    Then the status code "200" should return
    And the product is added successfully with an integer Id

  @update
  Scenario Outline: Post and then update the details
    Given the path "<path>" to the endpoint
    When I perform the PUT request with id and BrandName as "<brandName>", Features as "<feature>", LaptopName as "<laptopName>"
    Then the status code "<statusCode>" should return
    And Details should get updated
    Examples:
      | path   | brandName | feature                                      | laptopName | statusCode |
      | update | Dell      | 8GB RAM,1TB Hard Drive,15 Inch Lcd,Touch Pad | Latitude   | 200        |
