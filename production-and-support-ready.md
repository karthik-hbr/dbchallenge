Points to consider for "production-and-support-ready"


1. In Controller classes annotated with "@RestController" (for eg AccountsController.java), I would provide a path parameter to each method to make a REST API
call unique.

2. I would add validation to the getAccount method in AccountsController.java class to validate if the account is present or not.

3. I would add some kind of  Response Body to both getAccount and createAccount methods of AccountsController.java to give more than just basic HttpStatus Response

4. I would create a @RestControllerAdvice annotated class for centralized exception handling.

5. I would log all the logs in a file

6. I would add Authentication and Authorization features using Spring Security and JWT/OAuth 2.