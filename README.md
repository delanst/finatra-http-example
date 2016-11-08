## Fintra http example

A finatra http server example. I tried to cover as much as possible from the finatra functionality that you would need.
My intent is only to show you how to setup the finatra server and how to inject services. The services can be designed in your own way. 

Following has been used in this sample project
* swagger
* jwt authentication
* twitter modules
* twitter futures
* google guice injection

#### Swagger
You can go to http://localhost:8888/api-docs/ui for the swagger api. This provides documentation about the api. One context
is not protected (Account) in order to let you authenticate. @see Jwt authentication
Use a tool like postman in order to make your actual calls.

#### Jwt authentication
Here we have a basic jwt authentication system. Just must first authenticate to finatra application. You can do this with the following
context /api/v1/account/authenticate.
In the response header you find a header "Authorization" with Bearer token.
Use this bearer token in your request header for each other request you make. A small addition has been done where the token
is only valid for two hours. This is not the most ideal but it get's you started. Implement your own toughts about this.

#### Twitter modules
Modules allows you to inject your required services with google guice. In this case we have two modules
* EmployeeModule
* CompanyModule

We add both modules to the finatra framework by overriding the modules in the finatra server (Bootstrap).

#### Twitter futures
Opposed to the scala future we use here twitter futures. I find them richer then the scala futures. Here you always have to make a choice.
Whatever you choose always convert futures at the root!!! If you have a slick repository convert it immediately to  twitter futures for
further alignment of your application.

#### Guice injection
Guice allows you to inject services into the finatra framework. Here we use the minimum for the injection of services into
our controllers. 
This is accomplished with the @Inject annotation in the Employee and Company controller.

#### Preflight
Most browsers perform a preflight call. They call the options on your context before the GET, PUT, POST, DELETE. The
header information is used in order to check your request headers against the http server. If something is wrong with
the configuration then the call will fail. This is important if you server would be called by a angular app.
So we need to set some headers in order to pass the pre-flight check. Here are the headers that are most common.
* Access-Control-Allow-Origin => set * (in other environment you will need to specify the correct dns)
* Access-Control-Allow-Methods => HEAD, GET, PUT, POST, DELETE, OPTIONS (choose allowed methods for you)
* Access-Control-Allow-Headers => Origin, X-Requested-With, Content-Type, Accept, Authorization (if you have some extra headers add them here.)

### Start
* Run Bootstrap
* Go to http://localhost:8888/api-docs/ui

### Advise
* NEVER block a future or you will have concurrency issues (finatra framework captures them).
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


