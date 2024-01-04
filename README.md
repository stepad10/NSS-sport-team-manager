# Sport Team Manager

The application is used to support the organization of team activities.

A description of functions, technologies... is provided on the wiki.

## Known issues

GitHub OAuth isn't working, unfortunately there's only 1 redirect address, so I left localhost.

## Requirements

- [x] HEROKU
- [x] Application: Java - Spring Boot, Angular
- [x] DB: PostgreSQL
- [x] [Cache](https://gitlab.fel.cvut.cz/stepad10/nss-projekt/-/tree/master/BE/src/main/resources/eu/profinit/stm/mapperMyBatis): EhCache as MyBatis extension, in resources/mapperMyBatis/_/_.xml
- [ ] Messaging: X
- [x] [Security](https://gitlab.fel.cvut.cz/stepad10/nss-projekt/-/tree/master/BE/src/main/java/eu/profinit/stm/security): OAuth2 - local, Google, Facebook, Github (doesn't return private email => calls Github API)
- [x] [Interceptor](https://gitlab.fel.cvut.cz/stepad10/nss-projekt/-/blob/master/BE/src/main/java/eu/profinit/stm/service/LoggingInterceptor.java) - Used for logging (SLF4J) requests and responses. Inserted into RestTemplate.
- [x] [Controller](https://gitlab.fel.cvut.cz/stepad10/nss-projekt/-/tree/master/BE/src/main/java/eu/profinit/stm/controller): REST
- [x] Architecture - Multilayered
- [x] Init procedure: below
- [ ] Elasticsearch: X
- [x] Design patterns: X => we agreed on replacement with technologies.
- [x] [Liquibase](https://gitlab.fel.cvut.cz/stepad10/nss-projekt/-/tree/master/BE/src/main/resources) - For database versioning, enum data.
- [x] H2 - Embedded database for testing, on initialization changelogs and test data are loaded. Settings in test .properties.
- [x] [Docker](https://gitlab.fel.cvut.cz/stepad10/nss-projekt/-/blob/docker-compose/docker-compose.yml) - BE, FE, DB. Dockerfile in individual folders.
- [x] [MyBatis](https://gitlab.fel.cvut.cz/stepad10/nss-projekt/-/tree/master/BE/src/main/java/eu/profinit/stm/mapperMyBatis) - Interface for communication with DB, uses cache.
- [ ] Cloud services: X

## Initial Procedure

1. Download Docker and run it.
2. From the "docker-compose" branch, download the "docker-compose.yml" file and place it in the source folder.
3. In the source folder, run this command:

```bash
docker-compose up
```

4. A multi-container application should start in Docker.
5. The application is now available at http://localhost:4200.