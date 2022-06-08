FROM adoptopenjdk/openjdk11:alpine-jre
RUN mkdir /app
COPY jiren-customers-api/target/*.jar /app/app.jar
WORKDIR /app
EXPOSE 7181
RUN addgroup -S spring && adduser -S spring -G spring
RUN chown -R spring:spring /app
USER spring:spring
ENTRYPOINT ["java","-jar","app.jar"]
