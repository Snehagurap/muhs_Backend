FROM openjdk:17-slim-bullseye
RUN apt-get update; apt-get install -y fontconfig libfreetype6
ADD ["target/api-gateway-0.0.1.jar", "app.jar"]
EXPOSE 8180
ENV JAVA_OPTS="-Dspring.profiles.active=uat -Dspring.config.import=optional:configserver:http://config-server:8888"
RUN sh -c 'touch /app.jar'
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]