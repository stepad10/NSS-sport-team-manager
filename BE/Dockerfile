# FROM openjdk:11-jdk-slim as build
# WORKDIR /app

# COPY mvnw .
# COPY .mvn .mvn
# COPY pom.xml .
# RUN apt-get update && apt-get install dos2unix
# RUN dos2unix mvnw && ./mvnw dependency:go-offline -B
# COPY src src
# RUN ./mvnw package -DskipTests
# RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# FROM openjdk:11-jre-slim

# ARG DEPENDENCY=/app/target/dependency
# COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
# COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
# COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# ENTRYPOINT ["java","-cp","app:app/lib/*","eu.profinit.stm.SportTeamManagerApplication"]
# CMD ["./mvnw", "spring-boot:run"]

FROM maven:3-jdk-11
ADD . /app
WORKDIR /app
RUN ls -l
RUN mvn clean install

FROM openjdk:11-jdk
VOLUME /tmp
COPY --from=0 "/cxfbootsimple/target/cxf-boot-simple-*-SNAPSHOT.jar" app.jar
CMD ["sh", "-c", "java -Xmx300m -Xss512k -XX:CICompilerCount=2 -Dfile.encoding=UTF-8 -XX:+UseContainerSupport -jar /app.jar"]