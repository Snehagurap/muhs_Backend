FROM openjdk:17.0.2-jdk
RUN apk update
ADD ["target/config-server-0.0.1.jar", "app.jar"]
ENV JAVA_OPTS="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8787,suspend=n -Dspring.profiles.active=dev"
RUN sh -c 'touch /app.jar'
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]