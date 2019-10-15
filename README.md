# CSRF crypto Token.
This library allows to create CSRF tokens that don't need any memory storage to work, because all the 
magic is done with cryptography.
The signature algorithm used is HMAC-SHA256. The data used to produce the signature is:
(milisecs + uuid + secret)
where:
 * secret is something only known by the application.
 * milisecs is the Zulu time since 1-1-1970.
 * uuid is a random UUID.
## What's the need for a new CSRF library?.
* Most of the libraries available require a session storage to work.

# Usage.  
~~~
//generation

TokenService tokenService = new TokenService();
String token = tokenService.generateToken(SECRET, SIGNATURE_KEY);

//validation
boolean verify = tokenService.verifyToken(token,SECRET,SIGNATURE_KEY,10);

~~~
 
# Next improvements.

* Create a lambda function or a microservice.
* Improve testing by using JUnit, NG.
* Provide validation libraries for Microframeworks like Micronaut, Spark
* Vote for more...