### Developer Task 1 

* You will receive instructions from Cassava Smartech on what's required.


## Getting Started
To get started run the mvn clean test to verify all test suits as follows:
```cmd
mvn clean test
```
### Bug Fixes

*  Added **static** to Logger in MobileNumberUtils class
*  Corrected the PreInsert to **PrePersist** correct JPA annotation in SubscriberRequest domain
*  Corrected the calling of a super to **this.super()** default constructor in PartnerCOdeValidatorImpl
*  Corrected JPA save from persist to persist subscriberRequest and update createdSubscriberReq
*  Corrected the name of the Test from **EpayResourcesIT to EpayResourcesITTest**  file by appending Test at the end of the file so that mvn will recognise it when running unit Tests
*  Added **spring test and junit testing dependencies** in Pom of electronic-payments-api module to enable JUNIT testing.
*  Corrected the mapped object (to **request** from Request) on NamedQueries in SubscriberRequest domain
*  Correct instance variables initialization in ResponseCOde Enum, by adding **this** keyword to reference an instance variable
*  Added the correct && in @around argument parsing
     - Injected  a service that findSubscriberRequestsByPartnerCode 
     - Set responseCode, balance in checkingMissing fields is its validated

*  Added @PathVariable for partnerCode  in EpayResource controller enquireAirtimeBalance Method


### Enabled Logger
1. By creating /data and gave it 777 permission on my development system.


### BUSINESS MODULE UNIT TESTING
In order to run some unit testing in this package i had to implement and configure a few things as follows
* Created PartnerResponse DTO in econet-utils
* Created TransactionResponse DTO in electronic-payments-api
* Created BusinessApiTest file
* Created EpayBusinessResource Controller in resources package

#### Web Dependency Injection For the New test file
* Created aspects with RequestBusinessInterceptor
* Created EpayBusinessAspectConfig and EpayBusinessWebConfig
* Created EpayBusinessRequestProcessor and ReportingBusinessProcessor Middleware Services between the Controller and the Services

#### Additional Classes 
* Created TransactionsResponse DTO
* Created EpayBusinessResource Controller
* Added validatePartnerCodeData service method
* Added sql testfiles in resources
* Created  BusinessApiTest  test file


### Enabling Web Service Connection Pooling for the intelligent-network-api module

* Installed dbcp2 and postgresql dependencies
*  Configured DB Connection Pooling in IntelligentNetworkPublisher with pool initial size of 2
 


### Embedded Jetty web Server
To run the emebeded jetty web server run the following command

```cmd
mvn jetty:run
```
You should the apple to see the webserver being served at  localhost PORT **8080**

### To run the coverage test
To run the test coverage for the business module you should run the following cmd 

```cmd
mvn clean verify
```
and jacoco/index.html should be a able to fire the report.