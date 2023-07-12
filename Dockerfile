FROM openjdk:19
ADD target/dice-game-0.0.1.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]