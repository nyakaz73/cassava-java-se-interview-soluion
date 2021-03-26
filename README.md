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


