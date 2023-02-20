FROM openjdk:17-oracle
COPY target/*.jar ms-accounts.jar
ENTRYPOINT ["java", "-jar", "/ms-accounts.jar"]
EXPOSE 8080