HTTP-Rest-Api / V0.0.1
===


Description
---

This is a simple, standalone microservice which exposes Rest APIs to offer basic money transactions between accounts.


Quick Start
---

To run the program, just use the usual java -jar command on the .jar file generated in the target directory :

```
> java -jar revolut-test-0.0.1-SNAPSHOT-shaded.jar
```

The server will be automaically started on port 8080. There is no configuration needed.


You can also directly clone the project and run maven clean install to build it locally.

Testing
---

A postman collection is available for manual testing.

And also soap UI project is also available for automated testing.


Exposed API
---

- POST /create/account | body :
    {
"id" : "1",
"email" : "test@test.com",
"surname" : "testName",
"firstname" :"testFirstName", 
"accountBalance" : 1000 }

- GET /accounts/{id} | response body :     {
"id" : "1",
"email" : "test@test.com",
"surname" : "testName",
"firstname" :"testFirstName", 
"accountBalance" : 1000 }

- DELETE /accounts/{id} | response body :     {
"id" : "1",
"email" : "test@test.com",
"surname" : "testName",
"firstname" :"testFirstName", 
"accountBalance" : 1000 } (deleted resource)

- PUT /accounts/{id} | response body :     {
"id" : "1",
"email" : "test@test.com",
"surname" : "testName",
"firstname" :"testFirstName", 
"accountBalance" : 1000 } (updated to newest values)

- GET /accounts | body : [{
"id" : "1",
"email" : "test@test.com",
"surname" : "testName",
"firstname" :"testFirstName", 
"accountBalance" : 1000 }, {
"id" : "2",
"email" : "test2@test.com",
"surname" : "test2Name",
"firstname" :"testF2irstName", 
"accountBalance" : 2000 }] (a list of the accounts)

- POST /transactions/transfert | body : {"source" :"1", "destination" : "2", "amount" :"10000"} | transfert the amount value from source to destination