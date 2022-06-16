# Sport Team Manager

Aplikace slouží k podpoře organizace týmových aktivit.

Popis funkcí je uveden na wiki

HEROKU
Aplikace: Java - Spring Boot, Angular
DB: PostgreSQL
Cache: EhCache jako MyBatis extension, v resources/mapperMyBatis/*/*.xml
Messaging: X
Security: OAuth2 - local, Google, Facebook, Github (nevrací private email => volání Github API)
Interceptor - Využit pro logging (SLF4J) request a response. Vložen do RestTemplate.
Controller: REST
Architektura - Vícevrstevnatá
Init postup: zde
Elasticsearch: X
Design patterns: X => domluvili jsme se na náhradě technologiemi.
    Liquibase - Pro verzování databáze, enum data.
    H2 - Embedded databáze pro testování, při inicializaci se nahrají changelogy a testovací data.
    Docker - BE, FE, DB.
    MyBatis - Interface pro komunikaci s DB, využívá cache.
Cloud services: X


TODO přidat odkazy na místa, init postup
