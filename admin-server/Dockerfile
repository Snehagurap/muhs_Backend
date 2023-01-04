FROM openjdk:17-alpine
ADD ["target/admin-server-0.0.1.jar", "app.jar"]
EXPOSE 9000
ENV JAVA_OPTS="-Dspring.profiles.active=uat -Dspring.config.import=optional:configserver:http://config-server:8888"
RUN sh -c 'touch /app.jar'
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]