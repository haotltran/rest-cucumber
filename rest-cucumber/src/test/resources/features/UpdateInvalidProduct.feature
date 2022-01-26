@product
Feature: Update an invalid product

  Scenario Outline: Update invalid product Id
    Given the path "<path>" to the endpoint
    When I perform the PUT request with invalid id and BrandName as "<brandName>", Features as "<feature>", LaptopName as "<laptopName>"
    Then the status code "<statusCode>" should return
    And No response data returns
    Examples:
      | path   | brandName | feature                                      | laptopName | statusCode |
      | update | Dell      | 8GB RAM,1TB Hard Drive,15 Inch Lcd,Touch Pad | Latitude   | 404        |