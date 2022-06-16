# Sport Team Manager

Aplikace slouží k podpoře organizace týmových aktivit.

Popis funkcí je uveden na wiki.

##  Požadavky

- [ ] HEROKU
- [X] Aplikace: Java - Spring Boot, Angular
- [X] DB: PostgreSQL
- [X] Cache: EhCache jako MyBatis extension, v resources/mapperMyBatis/*/*.xml
- [ ] Messaging: X
- [X] Security: OAuth2 - local, Google, Facebook, Github (nevrací private email => volání Github API)
- [X] Interceptor - Využit pro logging (SLF4J) request a response. Vložen do RestTemplate.
- [X] Controller: REST
- [X] Architektura - Vícevrstevnatá
- [ ] Init postup: zde
- [ ] Elasticsearch: X
- [X] Design patterns: X => domluvili jsme se na náhradě technologiemi.
- Liquibase - Pro verzování databáze, enum data.
- H2 - Embedded databáze pro testování, při inicializaci se nahrají changelogy a testovací data.
- Docker - BE, FE, DB.
- MyBatis - Interface pro komunikaci s DB, využívá cache.
- [ ] Cloud services: X



TODO přidat odkazy na místa, HEROKU, init postup
