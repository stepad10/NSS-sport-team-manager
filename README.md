# Sport Team Manager

Aplikace slouží k podpoře organizace týmových aktivit.

Popis funkcí, technologií... je uveden na wiki.

Repo (master branch) je mirrorováno z GitLab na GitHub, kde se auto deployne BE složka na Heroku a FE se pomocí GitHub Actions buildne do branche gh-pages, odkud se hostují GitHub Pages.

Tedy aplikace funguje následovně, na GH Pages je web server, který komunikuje s BE, který běží, společně s databází, na Heroku.

## Known issues

GitHub OAuth nefunguje, bohužel je jen 1 adresa na redirect, tak jsem nechal localhost.

## Požadavky

- [x] HEROKU
- [x] Aplikace: Java - Spring Boot, Angular
- [x] DB: PostgreSQL
- [x] [Cache](https://gitlab.fel.cvut.cz/stepad10/nss-projekt/-/tree/master/BE/src/main/resources/eu/profinit/stm/mapperMyBatis): EhCache jako MyBatis extension, v resources/mapperMyBatis/_/_.xml
- [ ] Messaging: X
- [x] [Security](https://gitlab.fel.cvut.cz/stepad10/nss-projekt/-/tree/master/BE/src/main/java/eu/profinit/stm/security): OAuth2 - local, Google, Facebook, Github (nevrací private email => volání Github API)
- [x] [Interceptor](https://gitlab.fel.cvut.cz/stepad10/nss-projekt/-/blob/master/BE/src/main/java/eu/profinit/stm/service/LoggingInterceptor.java) - Využit pro logging (SLF4J) request a response. Vložen do RestTemplate.
- [x] [Controller](https://gitlab.fel.cvut.cz/stepad10/nss-projekt/-/tree/master/BE/src/main/java/eu/profinit/stm/controller): REST
- [x] Architektura - Vícevrstevnatá
- [x] Init postup: níže
- [ ] Elasticsearch: X
- [x] Design patterns: X => domluvili jsme se na náhradě technologiemi.
- [Liquibase](https://gitlab.fel.cvut.cz/stepad10/nss-projekt/-/tree/master/BE/src/main/resources) - Pro verzování databáze, enum data.
- H2 - Embedded databáze pro testování, při inicializaci se nahrají changelogy a testovací data. Nastavení v testovacích .properties.
- [Docker](https://gitlab.fel.cvut.cz/stepad10/nss-projekt/-/blob/docker-compose/docker-compose.yml) - BE, FE, DB. Dockerfile v jednotlivých složkách.
- [MyBatis](https://gitlab.fel.cvut.cz/stepad10/nss-projekt/-/tree/master/BE/src/main/java/eu/profinit/stm/mapperMyBatis) - Interface pro komunikaci s DB, využívá cache.
- [ ] Cloud services: X

## Init postup

1. Stáhněte si Docker a spusťte.
2. Z branche "docker-compose" si stáhněte "docker-compose.yml" a vložte do source složky.
3. V source složce pusťte tento příkaz:

```
docker-compose up
```

4. V Dockeru by se měla spustit multi-container applikace.
5. Applikace je nyní dostupná na http://localhost:4200.
