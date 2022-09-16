FROM openjdk:17-alpine
ADD ["target/eureka-server-0.0.1.jar", "app.jar"]
EXPOSE 8002
ENV JAVA_OPTS="-Dspring.profiles.active=uat"
RUN sh -c 'touch /app.jar'
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]