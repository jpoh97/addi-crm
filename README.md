# addi-crm project

### Decisions

This project is using hexagonal architecture because it let us to focus in the domain as the center of the application. In this particular use case the domain looks anemic but as the description says that is a sales pipeline application where we'll deal with different stages so it's better to have an architecture that let us to extend the application in the future.

The domain model has as aggregate root the Person class because the description focus in 2 kind of person (leads and prospects). Maybe in the future when we have more information about the Pipeline and Stage we could change the aggregate root for some of these classes.

I created a DomainException class to represent all the domains rules and validations that I perform in the domain or use cases. I didn't create multiple Exception classes in order to avoid repetitive code between the different exceptions as I'm not doing something special for each exception, I'm only change the message.

The PersonRepository is an interface that represents a port of hexagonal architecture. The adapter class is called PersonRepositoryInMemory that it's present in the infrastructure layer. With this, we're decoupling the domain from any framework or library used to access to the Database.

In the application layer, we have the ConvertLeadIntoProspect class that is a use case (or application service) representing the specific business requirement. This one is using the command design pattern. Also, we have some ports to access the external services. These ports are present here and not in the domain layer because are not part of this specific domain (sales pipeline).

In the infrastructure layer we have the Person rest controller that has an endpoint to convert a Lead into a Prospect. This endpoint is a Patch because we only need to update one state in the person (the stage in the pipeline). Also, we have the external systems adapters (implementing the ports from application layer), these classes are using the Multiny webclient in order to perform the HTTP requests. Finally, we have an ExceptionHandler that return a 400 Bad request when a domain exception is thrown.

As the problem specify that I need to perform multiple requests (some of them in parallel), it's valuable to use reactive programming in order to take care of threads. For that reason I'm using Quarkus that let me to create reactive applications e2e. It's based in Reactive Stream, so it's easy to understand if you have worked with another frameworks like webflux or vertx. I'm returning Uni as wrapper of the responses to have a reactive response from the rest controller to the repository.

I'm using WireMockStubs to create the HTTP Stubs. There I'm defining the endpoints and responses, simulating a latency for each request between 2 and 5 seconds (random value).

For testing the domain, I have the PersonTest class that has all the test for the Person aggregate. As the aggregates work as a cluster of objects, I can use a sociable unit test for check the behavior of all the objects using the aggregate root (Person class). I'm using a test data builder pattern to build the aggregate.

Finally, I have the PersonResourceTest that is a integration test that checks all the possible cases for the converIntoProspect controller method. It's using wiremock to stub the http requests returning the data required for each test.

### Future tasks

* Depending on the future business requirements, we could change the domain to represent the Pipeline and Stages as entities and change the aggregate root from Person class to Pipeline.

* Create a docker image using the Quarkus Native feature.

* Create different execution contexts for the database access and external services call to avoid any block in the event loop (main threads that receives the requests).

* Communicate with the external services using message queues.

* Use an actual database.

* Communicate the different components of the application using an Event Bus.

* Create http filters for security validations (tokens, cors, ets).

* As I'm using Java 15, I could create my custom jdk image using jlink in order to have a light version of the jar.

## Quarkus documentation

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./gradlew quarkusDev
```

## Packaging and running the application

The application can be packaged using:
```shell script
./gradlew build
```
It produces the `addi-crm-1.0.0-SNAPSHOT-runner.jar` file in the `/build` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/lib` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar build/addi-crm-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/addi-crm-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.

# RESTEasy JAX-RS

<p>A Hello World RESTEasy resource</p>

Guide: https://quarkus.io/guides/rest-json
