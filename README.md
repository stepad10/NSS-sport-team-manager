# Sport Team Manager

Aplikace slouží k podpoře organizace týmových aktivit.

Popis funkcí, technologií, ... je uveden na wiki.

Repo (master branch) je mirrorováno z GitLab na GitHub, kde se pomocí GitHub Actions deployne BE složka na Heroku a FE se buildne do branche gh-pages, kde se hostují GitHub Pages.

Tedy aplikace funguje následovně, na GH Pages je web server, který komunikuje s BE, který běží, společně s databází, na Heroku.

##  Požadavky

- [X] HEROKU
- [X] Aplikace: Java - Spring Boot, Angular
- [X] DB: PostgreSQL
- [X] Cache: EhCache jako MyBatis extension, v resources/mapperMyBatis/*/*.xml
- [ ] Messaging: X
- [X] Security: OAuth2 - local, Google, Facebook, Github (nevrací private email => volání Github API)
- [X] Interceptor - Využit pro logging (SLF4J) request a response. Vložen do RestTemplate.
- [X] Controller: REST
- [X] Architektura - Vícevrstevnatá
- [X] Init postup: níže
- [ ] Elasticsearch: X
- [X] Design patterns: X => domluvili jsme se na náhradě technologiemi.
- Liquibase - Pro verzování databáze, enum data.
- H2 - Embedded databáze pro testování, při inicializaci se nahrají changelogy a testovací data.
- Docker - BE, FE, DB.
- MyBatis - Interface pro komunikaci s DB, využívá cache.
- [ ] Cloud services: X



TODO přidat odkazy na místa
## Init postup

1. Stáhněte si Docker a spusťte.
2. Z branche "docker-compose" si stáhněte "docker-compose.yml" a vložte do source složky.
3. V source složce pusťte tento příkaz:
```
docker-compose up
```
4. V Dockeru by se měla spustit multi-container applikace.
5. Applikace je nyní dostupná na http://localhost:4200.
