## Development

To run the project, clone it from github. In the root directory run:

    ./mvnw


## Design Decisions
1) Used DTOs
2) Pulled Data into memory
3) Unit test near 100%
4) Offload more of the work to database
5) Service layer contains all business logic
6) Used jhipster to generate the shell project
7) The application includes HazelCase Web Session clustering configured to
make it immediately ready for autoscaling.
8) Using Java 8, Angular 1.5, Spring MVC for Rest , Hibernate for Perisistence
9) Created Administration Console to for adding Categories, a Sample Client to load data,
and a Report showing current state of datastore with counts. Please see screenshots attached.
10) OAuth2 Security
11) Need to Add One To Many Relationship on Category to CategoryData to enforce referential
integrity, also make CategoryData table records unique.
