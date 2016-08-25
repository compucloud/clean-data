## Development

To run the project, clone it from github. In the root directory run:

    ./mvnw

## Screenshots
Screenshots of the working application located in /documenation/screenshots folder


## Design Decisions

* Used Jhipster to create shell for the application. It creates Java web applications using industry best practices. One drawback is that it creates a lot of unnecessary code, however pros outweighs cons. Removing unnecessary code is pretty simple task. Once the extra code is peeled away your left with a well structured application that leverages common design patterns.
* The Tech Stack used here is Java 8, SpringBoot, SpringMVC,Angular,Hibernate ,HazelCast, PostGres. Services can be easily deployed to docker containers for microservices.
* For persistence operations most are straight forward utilizing Spring JPARepository like CRUD operations for Category. For CategoryData Collections were used to group Categories to show count information. This should be optimized by performing groupBy queries on the database instead. In general, the business rule operations can be optimized significantly by offloading more the work to the database instead of pulling into Java memory stack.
* UnitTest Cases were created for Service Layer operations that clean data and get count info. Service side code coverage should be near 100%. Unit test for UI layer at 95%.
* All business logic resides in the the Services layer separate from Resources clasess.Idea being to keep Resources simple, storing business logic together.
*  The application can be autoscaled, it includes HazelCast Web Session clustering enabled.
*  Debated on whether to include a UI or not. Swagger is enabled on the application so one could test the service operations by passing JSON strings on Swagger UI. Opted to include a UI to simulate the realworld need for most applications to have an Administration Console. Plus it allowed me to show the output in a better presentation specifically for the counts.
*  OAuth2 Security is included. Requestors must authenticate and receive bearer token and pass this for each request. It includes a expiry time that indicates how long the token is valid.
* The DataLayer currently does not have a relationship between Category and CategoryData. This would be required before releasing to production to ensue that each Category in CategoryData is present in Category table. Would also need to enforce unique constraint on CategoryData for category and subcategory fields.
