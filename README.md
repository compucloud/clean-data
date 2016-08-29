## Development

To run the project, clone it from github. In the root directory run:

    ./mvnw

To access the administration console for the three web services go to http://localhost:8080

The REST Service Endpoints
* http://localhost:8080/api/category-data-count
* http://localhost:8080/api/category-data
* http://localhost:8080/api/category

Swagger Endpoint
* http://localhost:8080/swagger-ui/index.html    

## Design Decisions

* Used Jhipster to create shell for the application. It creates Java based web applications using industry standards and best practices. One drawback is that it creates a lot of unnecessary code, however pros outweighs cons. Removing unnecessary code is pretty simple task. Once the extra code is peeled away your left with a well structured application that leverages common design patterns.
* The Tech Stack used here is Java 8, SpringBoot, SpringMVC, Angular, Hibernate ,HazelCast,  PostGres and Liquibase. Services can be easily deployed to docker containers for microservices.
* For persistence operations, most are straight forward utilizing Spring JPA Repository.
* UnitTest Cases were created for Service Layer operations that clean data and get count info. Service side code coverage should be near 100%. Unit test code coverage for UI layer at 95%.
* All business logic resides in the the services layer separate from Resources classes, with the idea being keep  REST Resources simple, storing business logic together in service classes.
*  The application can be auto-scaled, it includes HazelCast Web Session clustering enabled. Deploying this is rather simple and can be done in roughly an hour on AWS. For a quick deployment I would recommend deploying the war for this service on to AWS Elastic Bean Stalk which has an Apache HTTP + Tomcat setup and tie it to a PostGres RDS instance. Autoscaling Policy can be configured within EBS to start the application with a minimum of 2 instances and maximum of five. New instances are added when the Average CPU Utilization is greater than 75% for five consecutive minutes. Policy should also include an alarm to notify system administrators each time a new instance is created.
*  Debated on whether to include a UI or not. Swagger is enabled on the application so one could test the service operations by passing raw JSON strings within Swagger UI. Opted to include a custom UI to simulate the real world need for most applications to have an Administration Console. Plus it allowed me to show the output in a better presentation specifically for the counts.
*  OAuth2 Security is included. Requestors must authenticate and receive bearer token and pass this for each request. It includes a expiry time that indicates how long the token is valid.
* The database schema currently does not have a relationship between Category and CategoryData. This would be required before releasing to production to ensure that each Category in CategoryData is present in Category table. Would also need to enforce unique constraint on CategoryData for category and subcategory fields.  

## Screenshots
Screenshots of the working application located in /documenation/screenshots folder, see featured screen grabs below.

###### Home Page
![Image of Home Page](https://github.com/compucloud/clean-data/blob/master/documentation/screenshots/logged-in.png
)
###### Navigation
![Image of Navigation](https://github.com/compucloud/clean-data/blob/master/documentation/screenshots/navigation.png
)
###### Categories List
![Image of Categories List](https://github.com/compucloud/clean-data/blob/master/documentation/screenshots/categories.png
)
###### Add Category
![Image of Add Categories](https://github.com/compucloud/clean-data/blob/master/documentation/screenshots/add-category.png)
###### Subcategories API Admin Client
![Image of Sample Client](https://github.com/compucloud/clean-data/blob/master/documentation/screenshots/add-subcategories-client.png)
###### Subcategories Processing Summary
![Image of Output Screen 1](https://github.com/compucloud/clean-data/blob/master/documentation/screenshots/add-subcategories-client-output.png)
###### Swagger
![Image of Swagger](https://github.com/compucloud/clean-data/blob/master/documentation/screenshots/swagger.png)
###### Monitoring Application Metrics
![Image of Metrics](https://github.com/compucloud/clean-data/blob/master/documentation/screenshots/application-metrics.png)
###### Monitoring Logs
![Image of Monitoring Logs](https://github.com/compucloud/clean-data/blob/master/documentation/screenshots/logging.png)
