## Fintra http example

A fintra http server example. I tried to cover as much as possible from the finatra functionality that you would need.
My intent is only to show you how to setup the finatra server and how to inject services. The services can be designed in your own way. 

Following has been used in this sample project
* swagger
* jwt authentication
* twitter modules
* twitter futures
* google guice injection

### Start
* Run Bootstrap
* Go to http://localhost:8888/api-docs/ui

### Advise
* NEVER block a future or you will have concurrency issues.
* TRY to covert scala futures at source. (ex. let your slick repositories return twitter futures instead of the scala futures)
* Execute your future in a seperate future pool (@EmployeeResource)

### References
* Bootstrap -> The definition of the finatra http server
* SwaggerHttpDocument -> Swagger injection
* TwitterConverters -> converter function between scala and twitter futures.
* resources -> Fintra controllers
* JwtAuthenticate -> json web token authentication

### Info
* The path api/v1/login/authenticate must be improved with an ecryped password
* Always setup your api in https


